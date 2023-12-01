package com.example.luvdub.UsetInforSet

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.luvdub.R
import com.example.luvdub.ui.theme.Pink14
import com.example.luvdub.ui.theme.White
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import java.util.Locale

class UserInforSetFragment {

    @Composable
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    fun UserInforSetScrren(viewModel: UserInforSetViewModel = viewModel(UserInforSetViewModel::class.java)) {
        // datamanager에 저장된 currentStep값을 ViewModel에서 추출
        val currentStep by viewModel.currentStep.collectAsState()
        // datamanager에 저장된 userNickname값을 ViewModel에서 추출
        val userNickname by viewModel.userNickname.collectAsState()
        // Step에 맞는 Title 설정
        val titleText by viewModel.titleText.collectAsState()
        val MAX_STEP = 5

        // Scaffold는 Compose에서 실험적으로 사용하고 있는 API지만 공부를 위해 한번 써봄
        Scaffold(
            // 커스텀 타이틀바 구성
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            titleText,
                            maxLines = 1,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        )
                    },
                    navigationIcon = {
                        // CurrentStep이 첫번째 스텝인 경우에는 BackButton없앰(뒤로갈 화면이 없기떄문임)
                        if (currentStep != 1) {
                            IconButton(onClick = {
                                // BackButton클릭시 BackStep으로 돌아감
                                viewModel.decreaseCurrentStep()
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.back),
                                    tint = Pink14,
                                    contentDescription = "Back Button"
                                )
                            }
                        }
                    }
                )
            },
        ) {
            innerPadding ->
            // 타이틀바 아래의 Body영역 구성
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {

                DrawLine(currentStep) // 타이틀바 밑에 라인 그리기

                when (currentStep) {
                    // 스텝별 화면 설정
                    1 -> Step1Content(userNickname,viewModel)
                    2 -> Step2Content()
                    3 -> Step3Content()
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(onClick = {

                    // 다음 Step으로 이동하기 전에 현재 입력한 값을 datastore에 저장
                    when(currentStep){
                        1 -> viewModel.saveNickName()
                    }

                    // 다음 스텝으로 이동
                    if (currentStep < MAX_STEP){
                        // 다음으로 클릭시 CurrentStep값을 +1 해줌
                        viewModel.incrementCurrentStep()
                    } else {
                        // 혹시나 Step이 설정 이외의 값으로 튀었을때 Step초기화
                        viewModel.clearCurrentStep()
                    }
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White,
                        contentColor = White
                    ),

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp, end = 40.dp, bottom = 20.dp)
                        .height(58.dp),
                    enabled = if (currentStep == 1) userNickname.isNotEmpty() else true
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.property_btn_on),
                            contentDescription = null,
                            modifier = Modifier.size(400.dp),
                            contentScale = ContentScale.Crop
                        )

                        Text("다음으로")
                    }
                }
            }
        }
    }

    // 타이틀바 아래에 Step에 따른 직선 설정
    @Composable
    fun DrawLine(currentStep : Int) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .background(Pink14),
            onDraw = {
                var maxStep = 4
                var lineWidth = (size.width/maxStep * currentStep) // 스텝에 따라 동적으로 선의 길이 계산
                drawLine(
                    color = Pink14,
                    start = Offset(0f, 0f),
                    end = Offset(lineWidth, 0f),
                    strokeWidth = 3.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        )
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun Step1Content(userNickname : String, viewModel: UserInforSetViewModel) {
        val focusManager = LocalFocusManager.current
        // Step 1의 UI 구성
        Text(
            text = "사용할 닉네임을 입력하세요",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp),
            style = TextStyle(
                fontSize = 24.sp,
                lineHeight = 28.sp,
                fontWeight = FontWeight(700),
                color = Color(0xFF000000),
                textAlign = TextAlign.Center,
            )
        )

        Text(
            text = "둘만이 사용하는 애칭을 6자 이하로 정해주세요",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF000000),
                textAlign = TextAlign.Center,
                letterSpacing = 0.02.sp,
            )
        )

        Spacer(modifier = Modifier.height(50.dp)) // 50dp의 마진

        // Edit : 닉네임 입력
        var isFocused by remember { mutableStateOf(false) }
        //var text by remember { mutableStateOf("") }
        val maxLength = 6 // 닉네임 입력 최대 글자수

        // BasicTextField에 Row를 입힌 이유는 EditText 가운데 정렬이 Row를 덮어 씌워야 먹힘
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(88.dp)
                .padding(start = 30.dp, end = 30.dp)
                .shadow(
                    elevation = 4.dp,
                    spotColor = Color(0x40000000),
                    ambientColor = Color(0x40000000)
                )
                .border(
                    width = 2.dp,
                    color = if (isFocused) Color(0x30FFFFFF) else Color(0x87FA114F),
                    shape = RoundedCornerShape(size = 10.dp)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            BasicTextField(
                value = userNickname,
                singleLine = true,
                onValueChange = {
                    // 최대 6글자만 입력 가능하게 구현
                    if (it.length > maxLength){  it.take(maxLength) }
                    else {viewModel.changeNickname(it)}
                                },
                textStyle = TextStyle.Default.copy(
                    fontSize = 36.sp,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFFA114F),
                    textAlign = TextAlign.Center,
                ),
                decorationBox = { innerTextField ->
                    // HintText 정의
                    if (userNickname.isEmpty() || userNickname.equals("")) {
                        Text(
                            "닉네임 입력",
                            style = TextStyle(
                                fontSize = 36.sp,
                                lineHeight = 28.sp,
                                fontWeight = FontWeight(100),
                                color = Color(0xFFBDBCBC),
                                textAlign = TextAlign.Center,
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    // 사용자가 EditText를 입력할 때 BasicTextField -> 속성을 EditText에 입힘
                    innerTextField()
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // 키패드 완료 버튼을 눌렀을 때 focus해제
                        focusManager.clearFocus()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        isFocused = !it.isFocused
                    }
            )
        }

    }
    @Composable
    fun Step2Content() {
        // Step 2의 UI 구성
        val currentDate = Calendar.getInstance()

        var day by remember {
            mutableStateOf(currentDate.get(Calendar.DAY_OF_MONTH))
        }
        var month by remember {
            mutableStateOf(currentDate.get(Calendar.MONTH) + 1 )
        }
        var year by remember {
            mutableStateOf(currentDate.get(Calendar.YEAR))
        }
        var lastDayInMonth by remember {
            mutableStateOf(30)
        }

        fun adjustDay() {
            val newLastDayInMonth = lastDayInMonth(month, year)
            if (lastDayInMonth != newLastDayInMonth) {
                lastDayInMonth = newLastDayInMonth
                if (day > newLastDayInMonth) {
                    day = lastDayInMonth
                }
            }
        }

        Text(
            text = "우리의 기념일을 입력하세요",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp),
            style = TextStyle(
                fontSize = 24.sp,
                lineHeight = 28.sp,
                fontWeight = FontWeight(700),
                color = Color(0xFF000000),
                textAlign = TextAlign.Center,
            )
        )

        Text(
            text = year.toString()  + "년" + month.toString()  + "월" + (day).toString()  + "일",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            style = TextStyle(
                fontSize = 32.sp,
                lineHeight = 28.sp,
                fontWeight = FontWeight(700),
                color = Color(0xFFFA114F),
                textAlign = TextAlign.Center,
                )
        )

        Spacer(modifier = Modifier.height(40.dp)) // 50dp의 마진
        
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(235.dp)
            .padding(start = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            WheelDatePicker(
                width = 120.dp,
                itemHeight = 43.dp,
                items = (1950 .. 2100).toList(),
                initialItem = currentDate.get(Calendar.YEAR) - 1,
                textStyle = TextStyle(fontSize = 24.sp),
                textColor = Color.LightGray,
                selectedTextColor = Color.Black,
                unit = "년",
                onItemSelected = { i, item ->
                    year = item
                    adjustDay()
                }
            )

            WheelDatePicker(
                width = 120.dp,
                itemHeight = 43.dp,
                items = (1..12).toMutableList(),
                initialItem = currentDate.get(Calendar.MONTH) + 1,
                textStyle = TextStyle(fontSize = 24.sp),
                textColor = Color.LightGray,
                selectedTextColor = Color.Black,
                unit = "월",
                onItemSelected = { i, item ->
                    month = item // i는 index값인데 index는 0부터 시작하므로 +1함
                    adjustDay()
                }
            )

            WheelDatePicker(
                width = 120.dp,
                itemHeight = 43.dp,
                items = (1..lastDayInMonth).toMutableList(),
                initialItem = currentDate.get(Calendar.DAY_OF_MONTH) + 1,
                textStyle = TextStyle(fontSize = 24.sp),
                textColor = Color.LightGray,
                selectedTextColor = Color.Black,
                unit = "일",
                onItemSelected = { i, item ->
                    day = item
                }
            )
        }
    }

    @Composable
    fun Step3Content() {
        // Step 3의 UI 구성
        Text("Step 3 Content")
    }

    private fun lastDayInMonth(month: Int, year: Int): Int {
        return if (month != 2) {
            31 - (month - 1) % 7 % 2
        } else {
            if (year and 3 == 0 && (year % 25 != 0 || year and 15 == 0)) {
                29
            } else {
                28
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun <T> WheelDatePicker(
        width: Dp,
        itemHeight: Dp,
        numberOfDisplayedItems: Int = 5,
        items: List<T>,
        initialItem: T,
        itemScaleFact: Float = 1.5f,
        textStyle: TextStyle,
        textColor: Color,
        selectedTextColor: Color,
        unit: String,
        onItemSelected: (index: Int, item: T) -> Unit = { _, _ -> }
    ) {
        val itemHalfHeight = LocalDensity.current.run { itemHeight.toPx() / 2f }
        val scrollState = rememberLazyListState(0)
        var lastSelectedIndex by remember {
            mutableStateOf(0)
        }
        var itemsState by remember {
            mutableStateOf(items)
        }
        LaunchedEffect(items) {
            var targetIndex = items.indexOf(initialItem) - 1
            targetIndex += ((Int.MAX_VALUE / 2) / items.size) * items.size
            itemsState = items
            lastSelectedIndex = targetIndex
            scrollState.scrollToItem(targetIndex)
        }
        LazyColumn(
            modifier = Modifier
                .width(width)
                .height(itemHeight * numberOfDisplayedItems),
            state = scrollState,
            flingBehavior = rememberSnapFlingBehavior(
                lazyListState = scrollState
            )
        ) {
            items(
                count = Int.MAX_VALUE,
                itemContent = { i ->
                    val item = itemsState[i % itemsState.size]
                    Box(
                        modifier = Modifier
                            .height(itemHeight)
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates ->
                                val y = coordinates.positionInParent().y - itemHalfHeight
                                val parentHalfHeight = (itemHalfHeight * numberOfDisplayedItems)
                                val isSelected =
                                    (y > parentHalfHeight - itemHalfHeight && y < parentHalfHeight + itemHalfHeight)
                                val index = i - 1
                                if (isSelected && lastSelectedIndex != index) {
                                    onItemSelected((index % (itemsState.size)), itemsState[index % itemsState.size])
                                    lastSelectedIndex = index
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = item.toString() + unit,
                            style = textStyle,
                            color = if (lastSelectedIndex == i) {
                                selectedTextColor
                            } else {
                                textColor
                            },
                            fontSize = if (lastSelectedIndex == i) {
                                textStyle.fontSize * itemScaleFact
                            } else {
                                textStyle.fontSize
                            }
                        )
                    }
                }
            )
        }
    }
}
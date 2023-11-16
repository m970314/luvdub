package com.example.luvdub.UsetInforSet

import android.annotation.SuppressLint
import android.graphics.Paint.Align
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.luvdub.R
import com.example.luvdub.ui.theme.Pink14
import com.example.luvdub.ui.theme.Pink80

class UserInforSetFragment {

    @Composable
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    fun UserInforSetScrren(viewModel: UserInforSetViewModel = viewModel(UserInforSetViewModel::class.java)) {

        // datamanager에 저장된 currentStep값을 ViewModel에서 추출
        val currentStep by viewModel.currentStep.collectAsState()
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
                        IconButton(onClick = { /* do something */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.back),
                                tint = Pink14,
                                contentDescription = "Localized description"

                            )
                        }
                    }
                )
            },
        ) {
            // 타이틀바 아래의 Body영역 구성
            Column(
                modifier = Modifier
                    //.fillMaxSize()
                    .padding(16.dp)
            ) {

                DrawLine() // 타이틀바 밑에 라인 그리기

                when (currentStep) {
                    // 스텝별 화면 설정
                    1 -> Step1Content()
                    2 -> Step2Content()
                    3 -> Step3Content()
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(onClick = {
                    // 다음 스텝으로 이동
                    if (currentStep < MAX_STEP){
                        // 다음으로 클릭시 CurrentStep값을 +1 해줌
                        viewModel.incrementCurrentStep()
                    } else {
                        viewModel.clearCurrentStep()
                    }
                }) {
                    Text("다음으로")
                }
            }
        }
    }

    @Composable
    fun DrawLine() {
        Canvas(
            modifier = Modifier
                .background(Pink14),
            onDraw = {
                drawLine(
                    color = Pink14,
                    start = Offset(0.dp.toPx(), 0.dp.toPx()),
                    end = Offset(100.dp.toPx(), 0.dp.toPx()),
                    strokeWidth = 3.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        )
    }

    @Composable
    fun Step1Content() {
        // Step 1의 UI 구성
        Text("Step 1 Content")
    }

    @Composable
    fun Step2Content() {
        // Step 2의 UI 구성
        Text("Step 2 Content")
    }

    @Composable
    fun Step3Content() {
        // Step 3의 UI 구성
        Text("Step 3 Content")
    }
}
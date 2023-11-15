package com.example.luvdub.UsetInforSet

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.luvdub.R
import com.example.luvdub.ui.theme.Pink14
import com.example.luvdub.ui.theme.Pink80

class UserInforSetFragment {
    @Composable
    fun UserInforSetScrren(viewModel: UserInforSetViewModel = viewModel(UserInforSetViewModel::class.java)){

        // datamanager에 저장된 currentStep값을 ViewModel에서 추출
        val currentStep by viewModel.currentStep.collectAsState()
        val titleText by viewModel.titleText.collectAsState()
        val MAX_STEP = 5

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            CustomTitleBar(titleText)

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
                }else{
                    viewModel.clearCurrentStep()
                }
            }) {
                Text("다음으로")
            }
        }
    }

    // 커스텀 타이틀바 UI구현(모든 스텝화면에서 공통으로 사용)
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun CustomTitleBar(titleText : String) {
        // Scaffold는 Compose에서 실험적으로 사용하고 있는 API지만 공부를 위해 한번 써봄
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(titleText,
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
            }
        ) {
        }

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
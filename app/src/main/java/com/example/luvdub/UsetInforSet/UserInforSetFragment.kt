package com.example.luvdub.UsetInforSet

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.luvdub.R
import com.example.luvdub.data_store.IntPreferencesKey
import com.example.luvdub.data_store.LuvdubDataStoreManager
import com.example.luvdub.ui.theme.Black

class UserInforSetFragment {
    @Composable
    fun UserInforSetScrren() {
        val context = LocalContext.current
        val currentStep by LuvdubDataStoreManager.getLuvdubIntPreferencesStore(
            context,
            IntPreferencesKey.LUV_CURRENT_TAB_INT
        ).collectAsState(initial = 0)

        val MAX_STEP = 5
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            CustomTitleBar()

            when (currentStep) {
                // 스텝별 화면 설정
                1 -> Step1Content()
                2 -> Step2Content()
                3 -> Step3Content()
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(onClick = {
                // 다음 스텝으로 이동

            }) {
                Text("다음으로")
            }
        }
    }

    // 커스텀 타이틀바 UI구현(모든 스텝화면에서 공통으로 사용)
    @Composable
    fun CustomTitleBar() {
        // 여기에서 공통 타이틀바 UI 구성
        Row() {
            Icon(
                painter = painterResource(id = R.drawable.kakao),
                contentDescription = "Kakao Sign Button",
                tint = Black,
                modifier = Modifier.size(24.dp))
            Text("Custom Title", modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold)
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
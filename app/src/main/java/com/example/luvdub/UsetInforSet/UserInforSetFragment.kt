package com.example.luvdub.UsetInforSet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.luvdub.R
import com.example.luvdub.ui.theme.Black

class UserInforSetFragment {
    @Composable
    fun UserInforSetScrren() {
        Column(modifier = Modifier.fillMaxSize()) {
            CustomTitleBar()
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
}
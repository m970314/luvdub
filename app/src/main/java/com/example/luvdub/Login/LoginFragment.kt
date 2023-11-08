package com.example.luvdub.Login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class LoginFragment {

    @Composable
    fun LoginScreen() {
        // Login 화면 그리기
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(30.dp))
            Text(text = "쉽고 빠르고 새로운\n우리만의 연락방법", fontSize = 36.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(14.dp))

            Text(
                // 하나의 text에서 다른 폰트스타일 적용
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 20.sp)) {
                        append("이제 더 이상 연락으로 다투지말고\n")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("럽둡(Luv-Dub)")
                        }
                        append("으로 시그널을 보내요")
                    }
                }
            )

            Button(onClick = { /*TODO*/ }) {
                Spacer(modifier = Modifier
                    .clip(RoundedCornerShape(60.dp))
                    .then(Modifier.height(56.dp))
                )
            }
        }
    }

}
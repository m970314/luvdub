package com.example.luvdub.Login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.luvdub.R
import com.example.luvdub.ui.theme.Black
import com.example.luvdub.ui.theme.Yellow

class LoginFragment {

    @Composable
    fun LoginScreen(navController : NavHostController) {
        // Login 화면 그리기
        ConstraintLayout(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            // 컨텐트들의 아이디 설정
            val titleText = createRef()
            val subText = createRef()
            val kakaoLogin = createRef()

            // 컨텐트 UI 그리기
            Text(text = "쉽고 빠르고 새로운\n우리만의 연락방법",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .constrainAs(titleText){
                        bottom.linkTo(subText.top, margin = 30.dp )
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )

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
                },
                modifier = Modifier
                    .constrainAs(subText){
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom, margin = 100.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                }

            )

            // 카카오 로그인 버튼 Ui 구성
            Button(onClick = {
            //Todo 카카오 로그인 기능 구현 현재는 그냥 화면만 이동
                navController.navigate("navigation_user_set"){
                    popUpTo("navigation_login"){
                        inclusive = true
                    }
                }
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 40.dp, end = 40.dp)
                    .height(56.dp)
                    .constrainAs(kakaoLogin) {
                        bottom.linkTo(parent.bottom, margin = 20.dp)
                    },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Yellow,
                    contentColor = Black
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.kakao),
                        contentDescription = "Kakao Sign Button",
                        tint = Black,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("카카오톡으로 시작하기")
                }
            }
        }
    }
}

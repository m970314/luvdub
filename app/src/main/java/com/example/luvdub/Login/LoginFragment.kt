package com.example.luvdub.Login

import android.content.Context
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.luvdub.BuildConfig
import com.example.luvdub.R
import com.example.luvdub.ui.theme.Black
import com.example.luvdub.ui.theme.Yellow
import com.google.android.gms.tasks.RuntimeExecutionException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class LoginFragment {
    private lateinit var auth: FirebaseAuth

    @Composable
    fun LoginScreen() {
        val context = LocalContext.current
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
            Button(onClick = { kakaoLogin(context) },
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

    private fun kakaoLogin(context : Context) {
        KakaoSdk.init(context, BuildConfig.KAKAO_APP_KEY)
        // 카카오톡 어플이 있으면 카톡으로 로그인, 없으면 카카오 계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    loginWithKaKaoAccount(context)
                } else if (token != null) {
                    getCustomToken(token.accessToken)
                }
            }
        } else {
            loginWithKaKaoAccount(context)
        }
    }

    // 카카오 계정으로 로그인
    private fun loginWithKaKaoAccount(context: Context) {
        UserApiClient.instance.loginWithKakaoAccount(context) { token: OAuthToken?, error: Throwable? ->
            if (token != null) {
                getCustomToken(token.accessToken)
            }
        }
    }

    // firebase functions에 배포한 kakaoCustomAuth 호출
    private fun getCustomToken(accessToken: String) {

        val functions: FirebaseFunctions = Firebase.functions("asia-northeast3")

        val data = hashMapOf(
            "token" to accessToken
        )

        functions
            .getHttpsCallable("kakaoCustomAuth")
            .call(data)
            .addOnCompleteListener { task ->
                try {
                    // 호출 성공
                    val result = task.result?.data as HashMap<*, *>
                    var mKey: String? = null
                    for (key in result.keys) {
                        mKey = key.toString()
                    }
                    val customToken = result[mKey!!].toString()

                    // 호출 성공해서 반환받은 커스텀 토큰으로 Firebase Authentication 인증받기
                    firebaseAuthWithKakao(customToken)
                } catch (e: RuntimeExecutionException) {
                    // 호출 실패
                }
            }
    }

    private fun firebaseAuthWithKakao(customToken: String) {
        auth.signInWithCustomToken(customToken).addOnCompleteListener { result ->
            if (result.isSuccessful) {
                // Firebase Authentication 인증 성공 후 로직
            } else {
                // 실패 후 로직
            }
        }
    }
}

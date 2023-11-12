package com.example.luvdub.Login

import android.content.Context
import com.example.luvdub.BuildConfig
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

class LoginRepository {
    private lateinit var auth: FirebaseAuth

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
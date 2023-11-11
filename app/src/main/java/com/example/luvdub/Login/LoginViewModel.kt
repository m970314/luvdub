package com.example.luvdub.Login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    fun loginWithKakao() {
        // 카카오톡 로그인 처리
        // 성공 또는 실패 여부를 _loginResult에 전달
        // ...
        _loginResult.value = true // 성공했다고 가정
    }
}
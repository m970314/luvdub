package com.example.luvdub.UsetInforSet

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.luvdub.data_store.IntPreferencesKey
import com.example.luvdub.data_store.LuvdubDataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UserInforSetViewModel(application: Application) : AndroidViewModel(application){
    // 현재 Step에대한 인덱스 값
    private val _currentStep = MutableStateFlow(0)
    val currentStep: StateFlow<Int> get() = _currentStep.asStateFlow()

    // 현재 Step에 대한 Title Text 값
    private val _titleText = MutableStateFlow("")
    val titleText: StateFlow<String> get() = _titleText.asStateFlow()

    init {
        viewModelScope.launch {
            /* DataStore에서 초기값을 비동기적으로 가져오기
             **** ViewModel에 저장하는 이유는 ViewModel에 비동기로 저장시켜놓고 View에서 필요할때마다 불러올 수 있기때문
            */
            val context = getApplication<Application>().applicationContext
            val initialValue = LuvdubDataStoreManager.getLuvdubIntPreferencesStore(
                context,
                IntPreferencesKey.LUV_CURRENT_TAB_INT
            ).first()

            // ViewModel의 StateFlow에 값을 설정
            _currentStep.value = initialValue

            updateTitleText()
        }
    }

    fun decreaseCurrentStep() {
        // 다음 스텝으로 이동
        var newStep = _currentStep.value - 1

        // Step의 최소값은 1
        if (newStep < 1) newStep = 1
        _currentStep.value = newStep

        // DataStore에 변경된 값을 저장
        viewModelScope.launch {
            val context = getApplication<Application>().applicationContext
            LuvdubDataStoreManager.saveLuvdubIntPreferencesStore(
                context,
                IntPreferencesKey.LUV_CURRENT_TAB_INT,
                newStep
            )
        }

        updateTitleText()
    }

    fun incrementCurrentStep() {
        // 다음 스텝으로 이동
        val newStep = _currentStep.value + 1
        _currentStep.value = newStep

        // DataStore에 변경된 값을 저장
        viewModelScope.launch {
            val context = getApplication<Application>().applicationContext
            LuvdubDataStoreManager.saveLuvdubIntPreferencesStore(
                context,
                IntPreferencesKey.LUV_CURRENT_TAB_INT,
                newStep
            )
        }

        updateTitleText()
    }

    fun clearCurrentStep() {
        // DataStore에 변경된 값을 저장
        viewModelScope.launch {
            val context = getApplication<Application>().applicationContext
            LuvdubDataStoreManager.saveLuvdubIntPreferencesStore(
                context,
                IntPreferencesKey.LUV_CURRENT_TAB_INT,
                1
            )
        }

        updateTitleText()
    }

    // Step이 변경될 때마다 titleText 업데이트
    private fun updateTitleText() {
        when (_currentStep.value) {
            1 -> _titleText.value = "닉네임 설정"
            2 -> _titleText.value = "기념일 설정"
            3 -> _titleText.value = "알람 설정"
        }
    }

}
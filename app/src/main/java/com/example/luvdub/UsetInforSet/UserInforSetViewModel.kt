package com.example.luvdub.UsetInforSet

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.luvdub.data_store.LuvdubPreferencesKey
import com.example.luvdub.data_store.LuvdubDataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.Calendar

class UserInforSetViewModel(application: Application) : AndroidViewModel(application){
    // 현재 Step에대한 인덱스 값
    private val _currentStep = MutableStateFlow(0)
    val currentStep: StateFlow<Int> get() = _currentStep.asStateFlow()

    // User가 설정한 Nickname 값
    private val _userNickname = MutableStateFlow("")
    val userNickname: StateFlow<String> get() = _userNickname.asStateFlow()

    // User가 설정한 기념일의 년도 값
    private val _userCalendar_year = MutableStateFlow(0)
    val userCalendar_year: StateFlow<Int> get() = _userCalendar_year.asStateFlow()

    // User가 설정한 기념일의 달 값
    private val _userCalendar_month= MutableStateFlow(0)
    val userCalendar_month: StateFlow<Int> get() = _userCalendar_month.asStateFlow()

    // User가 설정한 기념일의 일자 값
    private val _userCalendar_day = MutableStateFlow(0)
    val userCalendar_day: StateFlow<Int> get() = _userCalendar_day.asStateFlow()

    // 현재 Step에 대한 Title Text 값
    private val _titleText = MutableStateFlow("")
    val titleText: StateFlow<String> get() = _titleText.asStateFlow()

    init {
        viewModelScope.launch {
            /* DataStore에서 초기값을 비동기적으로 가져오기
             **** ViewModel에 저장하는 이유는 ViewModel에 비동기로 저장시켜놓고 View에서 필요할때마다 불러올 수 있기때문
            */
            val currentDate = Calendar.getInstance() //현재 날짜
            val context = getApplication<Application>().applicationContext
            val initStepValue = LuvdubDataStoreManager.getLuvdubIntPreferencesStore(
                context,
                LuvdubPreferencesKey.LUV_CURRENT_STEP_INT
            ).first()

            val initNicknameValue = LuvdubDataStoreManager.getLuvdubStringPreferencesStore(
                context,
                LuvdubPreferencesKey.LUV_USER_NICKNAME_STRING
            ).first()

            val initCalendarYearValue = LuvdubDataStoreManager.getLuvdubIntPreferencesStore(
                context,
                LuvdubPreferencesKey.LUV_USER_CALENDAR_YEAR_INT
            ).firstOrNull() ?: currentDate.get(Calendar.YEAR)

            val initCalendarMonthValue = LuvdubDataStoreManager.getLuvdubIntPreferencesStore(
                context,
                LuvdubPreferencesKey.LUV_USER_CALENDAR_MONTH_INT
            ).firstOrNull() ?: (currentDate.get(Calendar.MONTH) + 1)

            val initCalendarDayValue = LuvdubDataStoreManager.getLuvdubIntPreferencesStore(
                context,
                LuvdubPreferencesKey.LUV_USER_CALENDAR_DAY_INT
            ).firstOrNull() ?: currentDate.get(Calendar.DAY_OF_MONTH)

            // ViewModel의 StateFlow에 값을 설정
            _currentStep.value = initStepValue
            _userNickname.value = initNicknameValue
            _userCalendar_year.value = initCalendarYearValue
            _userCalendar_month.value = initCalendarMonthValue
            _userCalendar_day.value = initCalendarDayValue

            updateTitleText()
        }
    }

    // 사용자가 Back버튼을 클릭했을때 호출하는 함수
    fun decreaseCurrentStep() {
        // 이전 스텝으로 이동
        var newStep = _currentStep.value - 1

        // Step의 최소값은 1
        if (newStep < 1) newStep = 1
        _currentStep.value = newStep

        // DataStore에 변경된 값을 저장
        viewModelScope.launch {
            val context = getApplication<Application>().applicationContext
            LuvdubDataStoreManager.saveLuvdubIntPreferencesStore(
                context,
                LuvdubPreferencesKey.LUV_CURRENT_STEP_INT,
                newStep
            )
        }

        updateTitleText()
    }

    // 사용자가 다음으로 버튼 클릭시 호출하는 함수
    fun incrementCurrentStep() {
        // 다음 스텝으로 이동
        val newStep = _currentStep.value + 1
        _currentStep.value = newStep

        // DataStore에 변경된 값을 저장
        viewModelScope.launch {
            val context = getApplication<Application>().applicationContext
            LuvdubDataStoreManager.saveLuvdubIntPreferencesStore(
                context,
                LuvdubPreferencesKey.LUV_CURRENT_STEP_INT,
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
                LuvdubPreferencesKey.LUV_CURRENT_STEP_INT,
                1
            )
        }

        updateTitleText()
    }

    // 사용자가 editText를 통해 닉네임을 변경할때 호출되는 함수
    fun changeNickname(changeNickname : String) {
        _userNickname.value = changeNickname
    }

    // 사용자가 Step1에서 다음으로 버튼 클릭 시 닉네임을 dataStore에 저장
    fun saveNickName() {
        // DataStore에 변경된 값을 저장
        viewModelScope.launch {
            val context = getApplication<Application>().applicationContext
            LuvdubDataStoreManager.saveLuvdubStringPreferencesStore(
                context,
                LuvdubPreferencesKey.LUV_USER_NICKNAME_STRING,
                _userNickname.value
            )
        }

        updateTitleText()
    }

    // 사용자가 calendar를 통해 기념일을 변경할때 호출되는 함수
    fun changeCalendar( Date: Int, Type : String) {
        when(Type){
            "year" -> _userCalendar_year.value = Date
            "month" -> _userCalendar_month.value = Date
            "day" -> _userCalendar_day.value = Date
        }
    }

    // 사용자가 Step2에서 다음으로 버튼 클릭 시 기념일의 년, 월, 일을 dataStore에 저장
    fun saveCalendar() {
        // DataStore에 변경된 값을 저장
        viewModelScope.launch {
            val context = getApplication<Application>().applicationContext
            //년도
            LuvdubDataStoreManager.saveLuvdubIntPreferencesStore(
                context,
                LuvdubPreferencesKey.LUV_USER_CALENDAR_YEAR_INT,
                _userCalendar_year.value
            )

            //달
            LuvdubDataStoreManager.saveLuvdubIntPreferencesStore(
                context,
                LuvdubPreferencesKey.LUV_USER_CALENDAR_MONTH_INT,
                _userCalendar_month.value
            )

            //일자
            LuvdubDataStoreManager.saveLuvdubIntPreferencesStore(
                context,
                LuvdubPreferencesKey.LUV_USER_CALENDAR_DAY_INT,
                _userCalendar_day.value
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
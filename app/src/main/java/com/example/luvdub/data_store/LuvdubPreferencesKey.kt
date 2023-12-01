package com.example.luvdub.data_store

enum class LuvdubPreferencesKey(val key: String) {
    // 빈 값 방지를 위한 default
    LUV_DEFALT("default"),
    // 현재 Step 위치를 기억하기 위한 string 상수
    LUV_CURRENT_STEP_INT("luvdub_current_step"),
    // 사용자가 설정한 닉네임을 기억하기위한 string 상수
    LUV_USER_NICKNAME_STRING("luvdub_user_nickname"),
}
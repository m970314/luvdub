package com.example.luvdub.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * DataStore를 관리하기 위한 싱글톤 객체
 * 중복된 인스턴스를 생성하면 안되기 때문에 싱글톤으로 관리
 */

object LuvdubDataStoreManager {
    private const val LUVDUB_DATA_STORE_NAME = "luvdub_settings"

    // 기본 DataStore 생성
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = LUVDUB_DATA_STORE_NAME)
    private val LUV_DEFAULT_INT: Preferences.Key<Int> = intPreferencesKey("default_int") // INT 빈값 방지를 위한 DEFAULT
    private val LUV_DEFAULT_STRING: Preferences.Key<String> = stringPreferencesKey("default_string") // STRING 빈값 방지를 위한 DEFAULT
    private val LUV_CURRENT_STEP: Preferences.Key<Int> = intPreferencesKey("luvdub_current_step") // 현재 스텝 값
    private val LUV_USER_NICKNAME: Preferences.Key<String> = stringPreferencesKey("luvdub_user_nickname") // 사용자가 입력한 nickname 값
    /**
     * Int타입의 Preferences DataStore을 쓰기 위한 함수
     *
     * @param context applicationContext
     * @param luvdubPreferencesKey int 기본 설정에 대한 키 name
     * @param intPreferencesValue Key에 저장할 Value
     *
     * @see LuvDubDataStoreManager.getLuvDubIntPreferencesStore
     */
    suspend fun saveLuvdubIntPreferencesStore(
        context: Context,
        luvdubPreferencesKey: LuvdubPreferencesKey,
        intPreferencesValue: Int
    ) {
        val preferencesKey = getIntPreferencesKey(luvdubPreferencesKey)

        context.dataStore.edit { settings ->
            settings[preferencesKey] = intPreferencesValue
        }
    }

    /**
     * Int타입의 Preferences DataStore을 읽기 위한 함수
     *
     * @param context applicationContext
     * @param luvdubPreferencesKey int 기본 설정에 대한 키 name
     *
     * @return intPreferencesKey에 저장된 value를 Flow타입으로 return
     *
     * @see LuvdubDataStoreManager.getLubdubIntPreferencesStore
     */
    fun getLuvdubIntPreferencesStore(context: Context, luvdubPreferencesKey: LuvdubPreferencesKey): Flow<Int> {
        val preferencesKey = getIntPreferencesKey(luvdubPreferencesKey)

        return context.dataStore.data.map { preferences ->
            preferences[preferencesKey] ?: 1
        }
    }

    /**
     * IntPreferencesKey로 받은 상수 인자를 preferencesKey로 return 해주는 함수
     *
     * @param luvdubPreferencesKey int 기본 설정에 대한 키 name
     *
     * @return 인자로 받은 IntPreferencesKey 상수를 Preferences.Key<Int>로 return
     */
    private fun getIntPreferencesKey(luvdubPreferencesKey: LuvdubPreferencesKey): Preferences.Key<Int> {
        return when (luvdubPreferencesKey) {
            LuvdubPreferencesKey.LUV_CURRENT_STEP_INT -> LUV_CURRENT_STEP
            else -> LUV_DEFAULT_INT
        }
    }

    /**
     * Int타입의 Preferences DataStore을 쓰기 위한 함수
     *
     * @param context applicationContext
     * @param luvdubPreferencesKey int 기본 설정에 대한 키 name
     * @param StringPreferencesValue Key에 저장할 Value
     *
     * @see LuvDubDataStoreManager.getLuvDubStringPreferencesStore
     */
    suspend fun saveLuvdubStringPreferencesStore(
        context: Context,
        luvdubPreferencesKey: LuvdubPreferencesKey,
        intPreferencesValue: String
    ) {
        val preferencesKey = getStringPreferencesKey(luvdubPreferencesKey)

        context.dataStore.edit { settings ->
            settings[preferencesKey] = intPreferencesValue
        }
    }

    /**
     * String타입의 Preferences DataStore을 읽기 위한 함수
     *
     * @param context applicationContext
     * @param luvdubPreferencesKey String 기본 설정에 대한 키 name
     *
     * @return StringPreferencesKey에 저장된 value를 Flow타입으로 return
     *
     * @see LuvdubDataStoreManager.getLubdubIntPreferencesStore
     */
    fun getLuvdubStringPreferencesStore(context: Context, luvdubPreferencesKey: LuvdubPreferencesKey): Flow<String> {
        val preferencesKey = getStringPreferencesKey(luvdubPreferencesKey)

        return context.dataStore.data.map { preferences ->
            preferences[preferencesKey] ?: ""
        }
    }

    /**
     * StringPreferencesKey로 받은 상수 인자를 preferencesKey로 return 해주는 함수
     *
     * @param luvdubPreferencesKey String 기본 설정에 대한 키 name
     *
     * @return 인자로 받은 StringPreferencesKey 상수를 Preferences.Key<String>로 return
     */
    private fun getStringPreferencesKey(luvdubPreferencesKey: LuvdubPreferencesKey): Preferences.Key<String> {
        return when (luvdubPreferencesKey) {
            LuvdubPreferencesKey.LUV_USER_NICKNAME_STRING -> LUV_USER_NICKNAME
            else -> LUV_DEFAULT_STRING
        }
    }
}
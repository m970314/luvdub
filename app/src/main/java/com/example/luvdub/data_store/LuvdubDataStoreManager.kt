package com.example.luvdub.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
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
    private val LUV_CURRENT_STEP: Preferences.Key<Int> = intPreferencesKey("luvdub_current_step")

    /**
     * Int타입의 Preferences DataStore을 쓰기 위한 함수
     *
     * @param context applicationContext
     * @param intPreferencesKey int 기본 설정에 대한 키 name
     * @param intPreferencesValue Key에 저장할 Value
     *
     * @see LuvDubDataStoreManager.getLuvDubIntPreferencesStore
     */
    suspend fun saveLuvdubIntPreferencesStore(
        context: Context,
        intPreferencesKey: IntPreferencesKey,
        intPreferencesValue: Int
    ) {
        val preferencesKey = getIntPreferencesKey(intPreferencesKey)

        context.dataStore.edit { settings ->
            settings[preferencesKey] = intPreferencesValue
        }
    }

    /**
     * Int타입의 Preferences DataStore을 읽기 위한 함수
     *
     * @param context applicationContext
     * @param intPreferencesKey int 기본 설정에 대한 키 name
     *
     * @return intPreferencesKey에 저장된 value를 Flow타입으로 return
     *
     * @see LuvdubDataStoreManager.getLubdubIntPreferencesStore
     */
    fun getLuvdubIntPreferencesStore(context: Context, intPreferencesKey: IntPreferencesKey): Flow<Int> {
        val preferencesKey = getIntPreferencesKey(intPreferencesKey)

        return context.dataStore.data.map { preferences ->
            preferences[preferencesKey] ?: 1
        }
    }

    /**
     * IntPreferencesKey로 받은 상수 인자를 preferencesKey로 return 해주는 함수
     *
     * @param intPreferencesKey int 기본 설정에 대한 키 name
     *
     * @return 인자로 받은 IntPreferencesKey 상수를 Preferences.Key<Int>로 return
     */
    private fun getIntPreferencesKey(intPreferencesKey: IntPreferencesKey): Preferences.Key<Int> {
        return when (intPreferencesKey) {
            IntPreferencesKey.LUV_CURRENT_TAB_INT -> LUV_CURRENT_STEP
        }
    }
}
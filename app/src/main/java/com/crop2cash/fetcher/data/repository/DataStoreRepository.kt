package com.crop2cash.fetcher.data.repository

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.crop2cash.fetcher.data.pref.PreferencesKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "on_boarding_pref")

class DataStoreRepository(context: Context) {

    private val dataStore = context.dataStore

    suspend fun saveThemeState(isDarkTheme: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.themeStateKey] = isDarkTheme
        }
    }

    fun readThemeState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException)
                    emit(emptyPreferences())
                else
                    throw exception
            }
            .map { preferences ->
                val themeState = preferences[PreferencesKeys.themeStateKey] ?: false
                themeState
            }
    }
}
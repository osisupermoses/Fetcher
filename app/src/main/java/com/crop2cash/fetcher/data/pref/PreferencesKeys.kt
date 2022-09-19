package com.crop2cash.fetcher.data.pref

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val themeStateKey = booleanPreferencesKey(name = "theme")
    val favoriteKey = stringPreferencesKey(name = "favorite")
}

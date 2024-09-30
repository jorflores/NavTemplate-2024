package com.example.navtemplate.dataStore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {


    val LOGGED_ID = booleanPreferencesKey("logged_in")
    val TOKEN = stringPreferencesKey("token")
}
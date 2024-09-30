package com.example.navtemplate.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.navtemplate.dataStore.PreferenceKeys.LOGGED_ID
import com.example.navtemplate.dataStore.PreferenceKeys.TOKEN

const val DATASTORE = "my_datastore"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE)


suspend fun Context.setLoggedIn(isLoggedIn: Boolean)  {

    dataStore.edit { preferences ->
        preferences[LOGGED_ID] = isLoggedIn
    }
}


suspend fun Context.setToken(token: String)  {

    dataStore.edit { preferences ->
        preferences[TOKEN] = token
    }
}
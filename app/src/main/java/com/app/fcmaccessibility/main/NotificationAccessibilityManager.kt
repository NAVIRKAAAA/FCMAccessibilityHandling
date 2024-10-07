package com.app.fcmaccessibility.main

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class NotificationAccessibilityManager(
    context: Context
) {

    private val dataStore = context.dataStore

    fun get(): Flow<Boolean> {
        return dataStore.data.map { it[NOTIFICATION_ACCESSIBILITY_MANAGER_KEY] ?: false }
    }

    suspend fun set(isEnabled: Boolean) {
        dataStore.edit { it[NOTIFICATION_ACCESSIBILITY_MANAGER_KEY] = isEnabled }
    }

    companion object {
        private val NOTIFICATION_ACCESSIBILITY_MANAGER_KEY = booleanPreferencesKey("notification_accessibility_manager_key")
    }

}
package com.app.fcmaccessibility.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.fcmaccessibility.main.NotificationAccessibilityManager
import com.app.fcmaccessibility.util.setAccessibility
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FCMNotificationViewModel(
    private val notificationAccessibilityManager: NotificationAccessibilityManager
) : ViewModel() {

    private val _state = MutableStateFlow(FCMNotificationState())
    val state = _state.asStateFlow()
    private val firebaseMessaging = FirebaseMessaging.getInstance()

    init {
        setAccessibility()
    }

    private fun setAccessibility() {
        viewModelScope.launch {
            val accessibility = notificationAccessibilityManager.get().first()
            _state.update { it.copy(notificationIsEnabled = accessibility) }

            firebaseMessaging.setAccessibility(accessibility)
        }
    }

    fun onPermissionResult(value: Boolean) {
        viewModelScope.launch {
            _state.update { it.copy(hasPermission = value) }
        }
    }

    fun onNotificationAccessibilityChange(isEnabled: Boolean) {
        viewModelScope.launch {
            _state.update { it.copy(notificationIsEnabled = isEnabled) }

            firebaseMessaging.setAccessibility(isEnabled)
            notificationAccessibilityManager.set(isEnabled)
        }
    }
}
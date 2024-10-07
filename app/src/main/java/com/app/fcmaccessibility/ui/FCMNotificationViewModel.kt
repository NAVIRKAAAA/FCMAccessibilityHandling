package com.app.fcmaccessibility.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.fcmaccessibility.main.NotificationAccessibilityManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FCMNotificationViewModel(
    private val notificationAccessibilityManager: NotificationAccessibilityManager
) : ViewModel() {

    private val _state = MutableStateFlow(FCMNotificationState())
    val state = _state.asStateFlow()

    init {
        setAccessibility()
    }

    private fun setAccessibility() {
        viewModelScope.launch {
            notificationAccessibilityManager.get().collect { accessibility ->
                _state.update { it.copy(notificationIsEnabled = accessibility) }
            }
        }
    }

    fun onPermissionResult(value: Boolean) {
        viewModelScope.launch {
            _state.update { it.copy(hasPermission = value) }
        }
    }

    fun onNotificationAccessibilityChange(isEnabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            notificationAccessibilityManager.set(isEnabled)
        }
    }
}
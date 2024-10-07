package com.app.fcmaccesebility.ui

import com.google.accompanist.permissions.PermissionState

data class FCMNotificationState(
    val hasPermission: Boolean = false,
    val notificationIsEnabled: Boolean = false
)

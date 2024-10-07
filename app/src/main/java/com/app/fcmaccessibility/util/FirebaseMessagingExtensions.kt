package com.app.fcmaccessibility.util

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

suspend fun FirebaseMessaging.setAccessibility(isEnabled: Boolean) {
    try {
        if(isEnabled) {
            token.await()
        } else {
            deleteToken().await()
        }
    } catch (e: Exception) {
        return
    }

}
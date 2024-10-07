package com.app.fcmaccesebility.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.app.fcmaccesebility.ui.FCMNotificationScreen
import com.app.fcmaccesebility.ui.FCMNotificationViewModel
import com.app.fcmaccesebility.ui.theme.FCMAccesebilityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FCMAccesebilityTheme {

                val viewModel = FCMNotificationViewModel(
                    NotificationAccessibilityManager(this)
                )

                FCMNotificationScreen(
                    viewModel = viewModel
                )
            }
        }
    }
}
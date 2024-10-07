package com.app.fcmaccessibility.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.app.fcmaccessibility.ui.FCMNotificationScreen
import com.app.fcmaccessibility.ui.FCMNotificationViewModel
import com.app.fcmaccessibility.ui.theme.FCMAccesebilityTheme

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
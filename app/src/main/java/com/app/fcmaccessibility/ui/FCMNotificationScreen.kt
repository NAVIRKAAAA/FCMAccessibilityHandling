package com.app.fcmaccessibility.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Switch
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun FCMNotificationScreen(
    viewModel: FCMNotificationViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()
    val permissionState = rememberPermissionState(
        permission = Manifest.permission.POST_NOTIFICATIONS,
        onPermissionResult = viewModel::onPermissionResult
    )

    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(text = "FCMNotificationScreen", modifier = Modifier.padding(16.dp))

        Text("Has notification permission: ${state.hasPermission}")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "Notification accessibility",
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            Switch(
                checked = state.notificationIsEnabled,
                onCheckedChange = viewModel::onNotificationAccessibilityChange
            )
        }
    }

}
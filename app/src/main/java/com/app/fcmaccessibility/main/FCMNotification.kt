package com.app.fcmaccessibility.main

import com.google.gson.Gson

data class FCMNotification(
    val title: String,
    val message: String,
    val sound: String
)

fun Map<String, String>.toFCMNotification(): FCMNotification? {
    return try {
        Gson().toJson(this).jsonToFCMNotification()
    } catch (e: Exception) {
        null
    }

}

fun String.jsonToFCMNotification(): FCMNotification = Gson().fromJson(this, FCMNotification::class.java)

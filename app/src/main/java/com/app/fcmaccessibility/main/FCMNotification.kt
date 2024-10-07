package com.app.fcmaccessibility.main

import com.google.gson.Gson

data class FCMNotification(
    val message: String,
    val body: String,
    val sound: String
)

fun Map<String, String>.toFCMNotification(): FCMNotification? {
    return try {
        Gson().toJson(this).jsonToRebNotification()
    } catch (e: Exception) {
        null
    }

}

fun String.jsonToRebNotification(): FCMNotification = Gson().fromJson(this, FCMNotification::class.java)

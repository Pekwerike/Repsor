package com.example.covid_19nalert

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

private val NOTIFICATION_ID = 0

/**Extension function to build in-app and push notifications**/
fun NotificationManager.sendNotification(messageBody : String, applicationContex : Context){

    /**Pending Intent to send the user to the application UI when the notification is clicked**/
    val contentIntent = Intent(applicationContex, MainActivity::class.java)
    val pendingContentIntent = PendingIntent.getActivity(
        applicationContex,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )



    /**build notification**/
    val builder = NotificationCompat.Builder(
        applicationContex,
        applicationContex.getString(R.string.repsor_notification_channel_id)
    ).setSmallIcon(R.drawable.nigeria_flag_wave_medium)
        .setContentIntent(pendingContentIntent)
        .setContentTitle("Just In")
        .setContentText(messageBody)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    /**send notification**/
    notify(NOTIFICATION_ID, builder.build())

}

fun NotificationManager.cancelNotification() {
    cancelAll()
}
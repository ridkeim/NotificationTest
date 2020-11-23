package ru.ridkeim.notificationtest

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val REQUEST_CODE_1 = 1

fun NotificationManager.sendNotification(message : String, context : Context){

    val intent = Intent(context, ChildActivity::class.java)

    val pendingIntent = PendingIntent.getActivity(
        context,
        NOTIFICATION_ID,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val snoozeIntent = Intent(context, NotificationActionReceiver::class.java)
    val snoozePendingIntent = PendingIntent.getBroadcast(
        context,
        REQUEST_CODE,
        snoozeIntent,
        PendingIntent.FLAG_ONE_SHOT)

    val dismissPendingIntent = PendingIntent.getBroadcast(
        context,
        REQUEST_CODE_1,
        snoozeIntent,
        PendingIntent.FLAG_ONE_SHOT
    )
    val builder = NotificationCompat.Builder(
            context,
            context.getString(R.string.channel_id)
        )
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("NotificationTitle")
        .setContentText(message)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .addAction(
            R.drawable.ic_launcher_foreground,
            "Snooze",
            snoozePendingIntent
        )
        .addAction(
            R.drawable.ic_launcher_foreground,
            "Dismiss",
            dismissPendingIntent
        )
        .setPriority(NotificationCompat.PRIORITY_HIGH)

   notify(NOTIFICATION_ID, builder.build())
}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}
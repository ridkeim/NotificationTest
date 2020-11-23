package ru.ridkeim.notificationtest

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.text.format.DateUtils
import androidx.core.app.AlarmManagerCompat

fun AlarmManager.setupAlarm(context : Context, delay : Int, requestCode : Int){
    val notificationIntent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
    )
    AlarmManagerCompat.setExactAndAllowWhileIdle(
            this,
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime()+ delay*DateUtils.SECOND_IN_MILLIS,
            pendingIntent
    )
}
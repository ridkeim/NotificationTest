package ru.ridkeim.notificationtest

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.text.format.DateUtils
import android.widget.Toast
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.ContextCompat

class NotificationActionReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION_DISMISS = "ru.ridkeim.notification.DISMISS"
        const val ACTION_SNOOZE = "ru.ridkeim.notification.SNOOZE"
    }

    override fun onReceive(context: Context, intent: Intent) {
        when(intent.action){
            ACTION_DISMISS -> dismissNotification(context)
            ACTION_SNOOZE ->{
                snoozeNotification(context)
                dismissNotification(context)
            }
        }
    }

    private fun snoozeNotification(context: Context) {
        val alarmManager = ContextCompat.getSystemService(context, AlarmManager::class.java) as AlarmManager
        val notificationIntent = Intent(context, AlarmReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        AlarmManagerCompat.setExactAndAllowWhileIdle(
                alarmManager,
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime()+5* DateUtils.SECOND_IN_MILLIS,
                pendingIntent
        )
    }

    private fun dismissNotification(context: Context){
        val notificationManager = ContextCompat.getSystemService(context, NotificationManager::class.java) as NotificationManager
        notificationManager.cancelNotifications()
    }

}
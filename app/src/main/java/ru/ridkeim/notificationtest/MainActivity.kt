package ru.ridkeim.notificationtest

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.text.format.DateUtils
import android.widget.Button
import android.widget.TextView
import androidx.core.app.AlarmManagerCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel()

        sendButton.setOnClickListener {
            val notificationManager = ContextCompat.getSystemService(
                application,
                NotificationManager::class.java
            )
            notificationManager?.sendNotification("immediate",application)
        }
        sendDelayedButton.setOnClickListener {
            val alarmManager = ContextCompat.getSystemService(
                applicationContext,
                AlarmManager::class.java
            ) as AlarmManager

            val notificationIntent = Intent(application, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                application,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            AlarmManagerCompat.setExactAndAllowWhileIdle(
                alarmManager,
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime()+10*DateUtils.SECOND_IN_MILLIS,
                pendingIntent
            )
        }
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(
                getString(R.string.channel_id),
                getString(R.string.channel_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}
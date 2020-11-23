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
                this,
                NotificationManager::class.java
            ) as NotificationManager
            notificationManager.sendNotification("immediate",this)
        }
        sendDelayedButton.setOnClickListener {
            val alarmManager = ContextCompat.getSystemService(
                applicationContext,
                AlarmManager::class.java
            ) as AlarmManager
            alarmManager.setupAlarm(applicationContext, 10,0)
        }
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(
                getString(R.string.channel_id),
                getString(R.string.channel_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = ContextCompat.getSystemService(this,NotificationManager::class.java) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}
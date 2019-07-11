package com.udmurtenergo.gpstracker.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.udmurtenergo.gpstracker.App
import com.udmurtenergo.gpstracker.R

class ServiceNotification(private val service: ServiceContract.Service) {

    private val notificationManager: NotificationManager = App.instance.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun show() {
        val text = App.instance.getString(R.string.app_name)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, text, importance)
            channel.description = text
            notificationManager.createNotificationChannel(channel)
        }
        val builder = NotificationCompat.Builder(service as Service, NOTIFICATION_CHANNEL_ID)
        builder.setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(text)
            .setContentText(text)
            .setStyle(NotificationCompat.BigTextStyle().bigText(text)).priority = NotificationCompat.PRIORITY_HIGH
        val notification = builder.build()
        (service as Service).startForeground(NOTIFICATION_ID, notification) // Service in white list
    }

    fun hide() {
        notificationManager.cancel(NOTIFICATION_ID)
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "5198"
        private const val NOTIFICATION_ID = 5198
    }
}

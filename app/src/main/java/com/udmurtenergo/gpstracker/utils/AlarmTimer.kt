package com.udmurtenergo.gpstracker.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.SystemClock
import com.udmurtenergo.gpstracker.App

class AlarmTimer {
    interface Listener {
        fun onTimerClick()
    }
    private val alarmTimerAction = "com.udmurtenergo.gpstracker.alarmTimerAction"
    private val alarmManager = App.instance.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val alarmTimerReceiver = AlarmTimerReceiver()
    private var listener: Listener? = null // ServiceController

    fun turnOn(listener: Listener, networkUpdateInterval: Int) {
        this.listener = listener

        // Настройка планировщика
        val networkIntent = Intent(alarmTimerAction)
        val pendingIntent = PendingIntent.getBroadcast(App.instance, 0, networkIntent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime(), (networkUpdateInterval * 60 * 1000).toLong(), pendingIntent
        )
        // Настройка приемника
        val intentFilter = IntentFilter()
        intentFilter.addAction(alarmTimerAction)
        App.instance.registerReceiver(alarmTimerReceiver, intentFilter)
    }

    fun turnOff() {
        listener = null

        // Отключение планировщика
        val networkIntent = Intent(alarmTimerAction)
        val pendingIntent = PendingIntent.getBroadcast(App.instance, 0, networkIntent, 0)
        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()

        // Отключение приемника
        App.instance.unregisterReceiver(alarmTimerReceiver)
    }

    private inner class AlarmTimerReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (listener != null) {
                listener!!.onTimerClick()
            }
        }
    }
}

package com.aniketkadam.servicecontrol.demoservice

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.aniketkadam.servicecontrol.base.models.Running

class DemoForegroundService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    companion object {
        fun stopService(c: Context) {
            c.stopService(Intent(c, DemoForegroundService::class.java))
        }

        fun startService(c: Context) {
            c.startService(Intent(c, DemoForegroundService::class.java))
        }

        fun manageNotification(c: Context, running: Running) {
            val done = when (running) {
                Running.NotificationVisible -> showNotification(c, true)
                Running.NotificationHidden -> showNotification(c, false)
            }
        }

        private fun showNotification(c: Context, show: Boolean) {
            TODO("got to implement actually showing and hiding the notification")
        }
    }
}

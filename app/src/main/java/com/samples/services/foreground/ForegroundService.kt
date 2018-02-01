package com.samples.services.foreground

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.widget.Toast

import com.samples.R
import com.samples.services.MainActivity

/**
 * Created by Anukool Srivastav on 01/02/18.
 */

class ForegroundService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        when {
            Constants.ACTION.STARTFOREGROUND_ACTION == intent.action -> {
                showNotification()
                Toast.makeText(this, "Service Started!", Toast.LENGTH_SHORT).show()

            }
            Constants.ACTION.PREV_ACTION == intent.action -> Toast.makeText(this, "Clicked Previous!", Toast.LENGTH_SHORT)
                    .show()
            Constants.ACTION.PLAY_ACTION == intent.action -> Toast.makeText(this, "Clicked Play!", Toast.LENGTH_SHORT).show()
            Constants.ACTION.NEXT_ACTION == intent.action -> Toast.makeText(this, "Clicked Next!", Toast.LENGTH_SHORT).show()
            Constants.ACTION.STOPFOREGROUND_ACTION == intent.action -> {
                stopForeground(true)
                stopSelf()
            }
        }
        return Service.START_STICKY
    }

    private fun showNotification() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.action = Intent.ACTION_MAIN
        notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0)

        val previousIntent = Intent(this, ForegroundService::class.java)
        previousIntent.action = Constants.ACTION.PREV_ACTION
        val ppreviousIntent = PendingIntent.getService(this, 0,
                previousIntent, 0)

        val playIntent = Intent(this, ForegroundService::class.java)
        playIntent.action = Constants.ACTION.PLAY_ACTION
        val pplayIntent = PendingIntent.getService(this, 0,
                playIntent, 0)

        val nextIntent = Intent(this, ForegroundService::class.java)
        nextIntent.action = Constants.ACTION.NEXT_ACTION
        val pnextIntent = PendingIntent.getService(this, 0,
                nextIntent, 0)

        val icon = BitmapFactory.decodeResource(applicationContext.resources,
                R.drawable.ic_launcher)

        val notification = NotificationCompat.Builder(this)
                .setContentTitle("Sample Music Player")
                .setTicker("Music Player")
                .setContentText("My Playlist Song")
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
                .setContentIntent(pendingIntent)
                .addAction(android.R.drawable.ic_media_previous, "Previous",
                        ppreviousIntent)
                .addAction(android.R.drawable.ic_media_play, "Play",
                        pplayIntent)
                .addAction(android.R.drawable.ic_media_next, "Next",
                        pnextIntent).build()
        startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE,
                notification)
    }

    companion object {
        private val LOG_TAG = "ForegroundService"
        var IS_SERVICE_RUNNING = false
    }
}

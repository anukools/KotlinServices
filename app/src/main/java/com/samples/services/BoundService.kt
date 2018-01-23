package com.samples.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import android.widget.Chronometer

/**
 * Created by Anukool Srivastav on 12/01/18.
 */

class BoundService : Service() {

    // Binder given to clients
    private val mBinder = MyBinder()
    private var mChronometer: Chronometer? = null

    val timestamp: String
        get() {
            Log.v("BoundServices :  ", "getTimestamp")
            val elapsedMillis = SystemClock.elapsedRealtime() - mChronometer!!.base
            val hours = (elapsedMillis / 3600000).toInt()
            val minutes = (elapsedMillis - hours * 3600000).toInt() / 60000
            val seconds = (elapsedMillis - (hours * 3600000).toLong() - (minutes * 60000).toLong()).toInt() / 1000
            val millis = (elapsedMillis - (hours * 3600000).toLong() - (minutes * 60000).toLong() - (seconds * 1000).toLong()).toInt()
            return hours.toString() + ":" + minutes + ":" + seconds + ":" + millis
        }

    inner class MyBinder : Binder() {
        val service: BoundService
            get() = this@BoundService
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.v("BoundServices :  ", "onBind")
        return mBinder
    }

    override fun onCreate() {
        super.onCreate()

        mChronometer = Chronometer(this)
        mChronometer!!.base = SystemClock.elapsedRealtime()
        mChronometer!!.start()

        Log.v("BoundServices :  ", "onCreate")
    }

    override fun onRebind(intent: Intent) {
        Log.v("BoundServices :  ", "onRebind")
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent): Boolean {
        Log.v("BoundServices :  ", "onUnbind")
        return super.onUnbind(intent)
    }


    override fun onDestroy() {
        Log.v("BoundServices :  ", "onDestroy")
        super.onDestroy()
    }

}

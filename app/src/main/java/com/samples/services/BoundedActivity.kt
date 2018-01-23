package com.samples.services

import android.content.ServiceConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.content.ComponentName
import android.content.Context
import kotlinx.android.synthetic.main.activity_bound.*
import android.content.Intent
import com.samples.R


class BoundedActivity : AppCompatActivity() {

    lateinit var mBoundService: BoundService
    var mServiceBound = false


    private val mServiceConnection = object : ServiceConnection {

        override fun onServiceDisconnected(name: ComponentName) {
            mServiceBound = false
        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val myBinder = service as BoundService.MyBinder
            mBoundService = myBinder.service
            mServiceBound = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bound)


        button1.setOnClickListener {
            val intent = Intent(this, BoundService::class.java)
            bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
        }

        button2.setOnClickListener {

            if (mServiceBound) {
                unbindService(mServiceConnection)
                mServiceBound = false
            }
            val intent = Intent(this, BoundService::class.java)
            stopService(intent)
        }


        button3.setOnClickListener{
            timer.setText(mBoundService.timestamp)
        }
    }

    override fun onStop() {
        super.onStop()
        if (mServiceBound) {
            unbindService(mServiceConnection)
            mServiceBound = false
        }
    }
}

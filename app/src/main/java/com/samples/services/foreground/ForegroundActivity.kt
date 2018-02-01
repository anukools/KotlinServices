package com.samples.services.foreground

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import com.samples.R

class ForegroundActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foreground)

    }

    fun onButtonClick(v: View) {
        val service = Intent(this@ForegroundActivity, ForegroundService::class.java)
        when (v.id) {
            R.id.button1 -> {
                service.action = Constants.ACTION.STARTFOREGROUND_ACTION
                ForegroundService.IS_SERVICE_RUNNING = true
            }

            R.id.button2 -> {
                service.action = Constants.ACTION.STOPFOREGROUND_ACTION
                ForegroundService.IS_SERVICE_RUNNING = false
            }
        }

        startService(service)
    }
}

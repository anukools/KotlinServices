package com.samples.services

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.samples.R
import kotlinx.android.synthetic.main.activity_unbound.*

class UnBoundedActivity : AppCompatActivity() {
    private val clickListener = View.OnClickListener { view ->
        val intent = Intent(this, UnBoundService::class.java)
        when (view.id) {

            R.id.button1 -> {
                startService(intent)
            }
            R.id.button2 -> {
                stopService(intent)
            }
            R.id.button3 -> {
                val nextActivity = Intent(this, BoundedActivity::class.java)
                startActivity(nextActivity)
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unbound)


        button1.setOnClickListener(clickListener)
        button2.setOnClickListener(clickListener)
        button3.setOnClickListener(clickListener)

    }
}
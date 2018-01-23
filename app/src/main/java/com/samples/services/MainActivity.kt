package com.samples.services

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.samples.R

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val clickListener = View.OnClickListener { view ->
        when (view.id) {

            R.id.button1 -> {
                val nextActivity = Intent(this, UnBoundedActivity::class.java)
                startActivity(nextActivity)
            }
            R.id.button2 -> {
                val nextActivity = Intent(this, BoundedActivity::class.java)
                startActivity(nextActivity)
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1.setOnClickListener(clickListener)
        button2.setOnClickListener(clickListener)

    }
}

package com.samples.services.intentService

import android.content.Intent
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast

import com.samples.R

class IntentServiceActivity : AppCompatActivity(), DownloadResultReceiver.Receiver {

    private var listView: ListView? = null
    private var progressBar: ProgressBar? = null

    private var arrayAdapter: ArrayAdapter<String>? = null

    private var mReceiver: DownloadResultReceiver? = null

    internal val url = "http://javatechig.com/api/get_category_posts/?dev=1&slug=android"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* Allow activity to show indeterminate progressbar */
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS)

        /* Set activity layout */
        setContentView(R.layout.activity_intent_service)

        /* Initialize listView */
        listView = findViewById<View>(R.id.listView) as ListView
        progressBar = findViewById<View>(R.id.progress_spinner) as ProgressBar

        /* Starting Download Service */
        mReceiver = DownloadResultReceiver(Handler())
        mReceiver!!.setReceiver(this)
        val intent = Intent(Intent.ACTION_SYNC, null, this, DownloadService::class.java)

        /* Send optional extras to Download IntentService */
        intent.putExtra("url", url)
        intent.putExtra("receiver", mReceiver)
        intent.putExtra("requestId", 101)

        startService(intent)
    }

    override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
        when (resultCode) {
            DownloadService.STATUS_RUNNING ->

                progressBar!!.visibility = View.VISIBLE
            DownloadService.STATUS_FINISHED -> {
                /* Hide progress & extract result from bundle */
                progressBar!!.visibility = View.GONE

                val results = resultData.getStringArray("result")

                /* Update ListView with result */
                arrayAdapter = ArrayAdapter(this@IntentServiceActivity, android.R.layout.simple_list_item_1, results!!)
                listView!!.adapter = arrayAdapter
            }
            DownloadService.STATUS_ERROR -> {
                /* Hide progress */
                progressBar!!.visibility = View.GONE
                /* Handle the error */
                val error = resultData.getString(Intent.EXTRA_TEXT)
                Toast.makeText(this, error, Toast.LENGTH_LONG).show()
            }
        }

    }
}

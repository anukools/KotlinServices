package com.samples.services.intentService

import android.content.Intent
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast

import com.samples.R
import kotlinx.android.synthetic.main.activity_intent_service.*

class IntentServiceActivity : AppCompatActivity(), DownloadResultReceiver.Receiver {

    private var arrayAdapter: ArrayAdapter<String>? = null

    private var mReceiver: DownloadResultReceiver? = null

    private val url = "http://javatechig.com/api/get_category_posts/?dev=1&slug=android"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* Set activity layout */
        setContentView(R.layout.activity_intent_service)

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

                progress_spinner!!.visibility = View.VISIBLE

            DownloadService.STATUS_FINISHED -> {
                /* Hide progress & extract result from bundle */
                progress_spinner!!.visibility = View.GONE

                val results = resultData.getStringArray("result")

                /* Update ListView with result */
                arrayAdapter = ArrayAdapter(this@IntentServiceActivity, android.R.layout.simple_list_item_1, results!!)
                listView!!.adapter = arrayAdapter
            }
            DownloadService.STATUS_ERROR -> {
                /* Hide progress */
                progress_spinner!!.visibility = View.GONE
                /* Handle the error */
                val error = resultData.getString(Intent.EXTRA_TEXT)
                Toast.makeText(this, error, Toast.LENGTH_LONG).show()
            }
        }

    }
}

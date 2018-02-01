package com.samples.services.intentService


import android.app.IntentService
import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import android.text.TextUtils
import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by Anukool Srivastav on 29/01/18.
 */

class DownloadService : IntentService(DownloadService::class.java.name) {

    override fun onHandleIntent(intent: Intent?) {

        Log.d(TAG, "Service Started!")

        val receiver = intent!!.getParcelableExtra<ResultReceiver>("receiver")
        val url = intent.getStringExtra("url")

        val bundle = Bundle()

        if (!TextUtils.isEmpty(url)) {
            /* Update UI: Download Service is Running */
            receiver.send(STATUS_RUNNING, Bundle.EMPTY)

            try {
                val results = downloadData(url)

                /* Sending result back to activity */
                if (results.isNotEmpty()) {
                    bundle.putStringArray("result", results)
                    receiver.send(STATUS_FINISHED, bundle)
                }
            } catch (e: Exception) {

                /* Sending error message back to activity */
                bundle.putString(Intent.EXTRA_TEXT, e.toString())
                receiver.send(STATUS_ERROR, bundle)
            }

        }
        Log.d(TAG, "Service Stopping!")
        this.stopSelf()
    }

    @Throws(IOException::class, DownloadException::class)
    private fun downloadData(requestUrl: String): Array<String?> {
        var inputStream: InputStream? = null

        var urlConnection: HttpURLConnection? = null

        /* forming th java.net.URL object */
        val url = URL(requestUrl)

        urlConnection = url.openConnection() as HttpURLConnection

        /* optional request header */
        urlConnection.setRequestProperty("Content-Type", "application/json")

        /* optional request header */
        urlConnection.setRequestProperty("Accept", "application/json")

        /* for Get request */
        urlConnection.requestMethod = "GET"

        val statusCode = urlConnection.responseCode

        /* 200 represents HTTP OK */
        if (statusCode == 200) {
            inputStream = BufferedInputStream(urlConnection.inputStream)

            val response = convertInputStreamToString(inputStream)

            return parseResult(response)
        } else {
            throw DownloadException("Failed to fetch data!!")
        }
    }

    @Throws(IOException::class)
    private fun convertInputStreamToString(inputStream: InputStream?): String {

        val bufferedReader = BufferedReader(InputStreamReader(inputStream!!))

        val result = inputStream.bufferedReader().use(BufferedReader::readText)

        /* Close Stream */
        inputStream.close()

        return result
    }

    private fun parseResult(result: String): Array<String?> {

        try {
            val response = JSONObject(result)

            val posts = response.optJSONArray("posts")

            var blogTitles: Array<String?> =  arrayOfNulls<String>(posts.length())

            for (i in 0 until posts.length()) {
                val post = posts.optJSONObject(i)
                val title = post.optString("title")

                blogTitles[i] = title
            }

            return blogTitles
        } catch (e: JSONException) {
            e.printStackTrace()
            val temp: Array<String?> =  arrayOfNulls<String>(0)
            return temp
        }
    }

    inner class DownloadException : Exception {

        constructor(message: String) : super(message) {}

        constructor(message: String, cause: Throwable) : super(message, cause) {}
    }

    companion object {

        val STATUS_RUNNING = 0
        val STATUS_FINISHED = 1
        val STATUS_ERROR = 2

        private val TAG = "DownloadService"
    }
}

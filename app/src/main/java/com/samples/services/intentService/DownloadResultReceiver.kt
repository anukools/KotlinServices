package com.samples.services.intentService

import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver

/**
 * Created by Anukool Srivastav on 29/01/18.
 */

class DownloadResultReceiver(handler: Handler) : ResultReceiver(handler) {
    private var mReceiver: Receiver? = null

    fun setReceiver(receiver: Receiver) {
        mReceiver = receiver
    }

    interface Receiver {
        fun onReceiveResult(resultCode: Int, resultData: Bundle)
    }

    override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
        if (mReceiver != null) {
            mReceiver!!.onReceiveResult(resultCode, resultData)
        }
    }
}

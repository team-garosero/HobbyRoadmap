package com.garosero.android.hobbyroadmap.network

import com.garosero.android.hobbyroadmap.network.request.*

class NetworkFactory {

    companion object {
        fun request(baseRequest: BaseRequest, listener: RequestListener? = null) {

            if (listener != null) {
                baseRequest.setListener(listener)
            } // end if

            baseRequest.request()
        }
    }
}
package com.garosero.android.hobbyroadmap.network

import com.garosero.android.hobbyroadmap.network.request.*

class NetworkFactory {

    companion object {
        fun request(baseRequest: BaseRequest, listener: RequestListener) {
            baseRequest.setListener(listener)
            baseRequest.request()
        }
    }
}
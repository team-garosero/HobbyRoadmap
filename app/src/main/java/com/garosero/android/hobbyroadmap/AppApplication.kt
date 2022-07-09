package com.garosero.android.hobbyroadmap

import android.app.Application
import com.garosero.android.hobbyroadmap.network.response.UserResponse

class AppApplication : Application() {
    private val TAG = "AppApplication"

    companion object {
        var userData = UserResponse()
    }
}
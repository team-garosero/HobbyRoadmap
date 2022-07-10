package com.garosero.android.hobbyroadmap.network.response

open class MyClassResponse(
    var classPath : String = "",
    var lastAccess : String = "",
    var modules : Map<String, String> = mapOf()
)

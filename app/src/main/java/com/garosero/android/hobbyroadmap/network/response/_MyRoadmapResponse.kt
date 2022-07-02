package com.garosero.android.hobbyroadmap.network.response

data class _MyRoadmapResponse(
    var last_access : String = "",
    var courses : Map<String, String> = mapOf()
)

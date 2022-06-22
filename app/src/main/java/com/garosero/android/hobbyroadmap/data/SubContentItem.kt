package com.garosero.android.hobbyroadmap.data


data class SubContentItem(
    var order : Int = 0,
    var title : String = "",
    var courseMap : MutableMap<String, CourseItem> = mutableMapOf(),
    var subContentID : String = ""
)

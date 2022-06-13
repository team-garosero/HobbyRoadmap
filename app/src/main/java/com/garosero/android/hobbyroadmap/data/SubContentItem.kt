package com.garosero.android.hobbyroadmap.data

/*
 * firebase와 바로 연동하려면, 인자가 없는 생성자가 정의 되어야 한다.
 * SubContentItem()로 호출이 가능해야 한다.
 */
data class SubContentItem(
    var order : Int = 0,
    var title : String = "",
    var courseMap : MutableMap<String, CourseItem> = mutableMapOf(),
    var subContentID : String = ""
)

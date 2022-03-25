package com.garosero.android.hobbyroadmap.models

data class CategoryItem(
    var title:String,
    val roadmap:MutableList<MyRoadmapItem> = mutableListOf()
)

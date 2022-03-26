package com.garosero.android.hobbyroadmap.data

data class CategoryItem(
    var title:String,
    val roadmapList:MutableList<RoadmapItem> = mutableListOf()
)

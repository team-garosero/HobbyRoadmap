package com.garosero.android.hobbyroadmap.data

import java.time.LocalDate

data class TilItem(
    val courseID : String = "",
    val uid : String = "",
    val date: LocalDate,
    val contentList: MutableList<ContentItem> = mutableListOf()
)
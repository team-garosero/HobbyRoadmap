package com.garosero.android.hobbyroadmap.data

import java.time.LocalDate

data class TilItem(
    val uid : String = "",
    val courseId : String = "",
    val date: LocalDate,
    val content: String = ""
)
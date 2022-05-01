package com.garosero.android.hobbyroadmap.data

import java.time.LocalDate

data class TilBoxItem(
    var date: LocalDate,
    var tilList: MutableList<TilItem> = mutableListOf()
)
package com.garosero.android.hobbyroadmap.myutil

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateHelper {
    @RequiresApi(Build.VERSION_CODES.O)
    fun dateStringYMD(date: LocalDate): String{
        return date.format(DateTimeFormatter.ofPattern("YYYY/MM/dd"))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun dateStringMD(date: LocalDate): String{
        return date.format(DateTimeFormatter.ofPattern("MM/dd"))
    }
}
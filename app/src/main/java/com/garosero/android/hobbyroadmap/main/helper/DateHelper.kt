package com.garosero.android.hobbyroadmap.main.helper

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class DateHelper {
    companion object {
        fun changeDateToYYMMDD(date : LocalDate) : String{
            return date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        }

        fun getTodayString() : String {
            return changeDateToYYMMDD(LocalDate.now())
        }
    }
}
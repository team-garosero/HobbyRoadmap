package com.garosero.android.hobbyroadmap.main.helper

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class DateHelper {
    companion object {
        fun changeDateToYYMMDD(date : LocalDate = LocalDate.now()) : String{
            return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        }

        fun getTodayString() : String {
            return changeDateToYYMMDD(LocalDate.now())
        }

        /**
         * ex. 3월 8일
         */
        fun changeDateToMMDD(date: LocalDate = LocalDate.now()) : String {
            return date.format(DateTimeFormatter.ofPattern("M월 d일"))
        }

    }
}
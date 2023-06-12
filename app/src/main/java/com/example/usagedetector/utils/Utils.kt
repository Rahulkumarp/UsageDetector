package com.example.usagedetector.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class Utils {

    companion object {
        @SuppressLint("SimpleDateFormat")
        fun dateToTime(value: String): String {

            var spf = SimpleDateFormat("dd-mm-yyyy hh:mm:ss")
            val newDate: Date = spf.parse(value)
            spf = SimpleDateFormat("hh:mm:ss")
            return spf.format(newDate)
        }
    }
}
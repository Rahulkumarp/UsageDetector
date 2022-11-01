package com.example.usagedetector.usage

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.util.Log
import com.example.usagedetector.`interface`.AppMetric
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter

class BetterUsageExporter(private val context: Context) : AppMetric {

    private companion object{

        const val BATTERY_USAGE_FILENAME = "battery_usage.txt"
        const val INVALIDATE_VALUE = -1
    }
    private val batteryPw = PrintWriter(FileOutputStream(File(context.filesDir, BATTERY_USAGE_FILENAME),
        true),true)

    private val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)



    override fun export() {
        val batteryStatus : Intent = intentFilter.let {
            ifilter -> context.registerReceiver(null, ifilter)!!
        }

        val status : Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, INVALIDATE_VALUE) ?: INVALIDATE_VALUE

        if(status != INVALIDATE_VALUE){
            batteryPw.println("$status")
            Log.d("BATTERY", status.toString())
        }
    }

    override fun close() {
       batteryPw.close()
    }
}
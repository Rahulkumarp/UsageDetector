package com.example.usage.usage

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.util.Log
import com.example.usage.`interface`.ApiInterface
import com.example.usage.`interface`.AppMetric
import com.example.usage.utils.Utils
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import javax.inject.Inject

class BetterUsageExporter @Inject constructor(private val context: Context, val apiservice :  ApiInterface) : AppMetric {

    private companion object{

        const val BATTERY_USAGE_FILENAME = "battery_usage.txt"
        const val INVALIDATE_VALUE = -1
    }
    private val batteryPw = PrintWriter(FileOutputStream(File(context.filesDir, BATTERY_USAGE_FILENAME),
        true),true)

    private val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)


    override fun export(
        screenName: String?,
        buttonName: String?,
        fileName: String,
        lineName: String,
        methodName: String
    ) {
        val batteryStatus : Intent = intentFilter.let {
                ifilter -> context.registerReceiver(null, ifilter)!!
        }

        val status : Int = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, INVALIDATE_VALUE)

        if(status != INVALIDATE_VALUE) {
            batteryPw.println("$status")
            Timber.tag("BATTERY").d(status.toString())
        }
    }


    override fun close() {
       batteryPw.close()
    }

    override fun setPath(folder : String) {
        //Utils.setDataInContentProvider(context, BATTERY_USAGE_FILENAME,"txt")
    }
}
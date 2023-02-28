package com.example.usage.usage

import android.content.Context
import android.net.TrafficStats
import android.util.Log
import com.example.usage.`interface`.AppMetric
import com.example.usage.utils.Utils
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.lang.RuntimeException
import java.text.CharacterIterator
import java.text.StringCharacterIterator

class NetworkUsageExporter(var context : Context) : AppMetric {


    private companion object{
        const val NETWORK_USAGE_FILENAME = "network_usage.txt"
    }

    private val networkPw = PrintWriter(FileOutputStream(File(context.filesDir,
        NETWORK_USAGE_FILENAME
    ),true),true)


    private var transmittedBytes = 0L
    private var receiveBytes = 0L




    override fun export(
        screenName: String?,
        buttonName: String?,
        fileName: String,
        lineName: String,
        methodName: String
    ) {
       val tBytes = TrafficStats.getTotalTxBytes()
       val rBytes = TrafficStats.getTotalRxBytes()

        if(tBytes.toInt() == TrafficStats.UNSUPPORTED || rBytes.toInt() == TrafficStats.UNSUPPORTED){
            throw RuntimeException("Device not support network monitoring")
        }else if(transmittedBytes > 0 && receiveBytes >0){

            Log.d("NETWORK", convertINMBWithString(tBytes)!! )
            Log.d("NETWORK", convertINMBWithString(rBytes)!!)
            Log.d("NETWORK", convertINMBWithString(tBytes - transmittedBytes).toString())
            Log.d("NETWORK", convertINMBWithString(rBytes - receiveBytes).toString())
            networkPw.println("${tBytes - transmittedBytes} ${rBytes - receiveBytes}")
        }

        transmittedBytes = tBytes
        receiveBytes = rBytes
    }

    override fun close() {
       networkPw.close()
    }

    override fun setPath(folder : String) {
      //  Utils.setDataInContentProvider(context, NETWORK_USAGE_FILENAME,"txt")

    }


    fun convertINMBWithString(bytes: Long): String? {
        var bytes = bytes
        if (-1000 < bytes && bytes < 1000) {
            return "$bytes B"
        }
        val ci: CharacterIterator = StringCharacterIterator("kMGTPE")
        while (bytes <= -999950 || bytes >= 999950) {
            bytes /= 1000
            ci.next()
        }
        return java.lang.String.format("%.1f %cB", bytes / 1000.0, ci.current())
    }
}
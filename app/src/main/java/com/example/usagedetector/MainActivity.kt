package com.example.usagedetector

import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.usage.AppMetricExporter
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.text.CharacterIterator
import java.text.StringCharacterIterator


class MainActivity : AppCompatActivity() {

    private lateinit var button : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById<Button>(R.id.hello_world)

        Log.d("CPU", getCPUDetails().toString())
        getRAMInfo()

        button.setOnClickListener{

            AppMetricExporter(this).startCollect()
            getRAMInfo()

            getMemoryInfo()

            getApkSize(this)
        }
    }

    fun getRAMInfo()
    {
        val nativeHeapSize = Debug.getNativeHeapSize()
        val nativeHeapFreeSize = Debug.getNativeHeapFreeSize()
        val usedMemInBytes = nativeHeapSize - nativeHeapFreeSize

        Log.d("RAMINFO", humanReadableByteCountSI(nativeHeapSize).toString())
        Log.d("RAMINFO", humanReadableByteCountSI(nativeHeapFreeSize).toString())
        Log.d("RAMINFO", humanReadableByteCountSI(usedMemInBytes).toString())

       val memory  =  Debug.getNativeHeapAllocatedSize();
        Log.d("RAMINFO", humanReadableByteCountSI(memory).toString())

    }


    fun humanReadableByteCountSI(bytes: Long): String? {
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


    fun getCPUDetails(): String? {
        val processBuilder: ProcessBuilder
        var cpuDetails = ""
        val DATA = arrayOf("/system/bin/cat", "/proc/cpuinfo")
        val `is`: InputStream
        val process: Process
        val bArray: ByteArray
        bArray = ByteArray(1024)
        try {
            processBuilder = ProcessBuilder(*DATA)
            process = processBuilder.start()
            `is` = process.inputStream
            while (`is`.read(bArray) != -1) {
                cpuDetails = cpuDetails + String(bArray) //Stroing all the details in cpuDetails
            }
            `is`.close()
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return cpuDetails
    }


    private fun getMemoryInfo(): String? {
        val memoryInfo = ActivityManager.MemoryInfo()
        val activityManager =
            getSystemService(ACTIVITY_SERVICE) as ActivityManager
        activityManager.getMemoryInfo(memoryInfo)
        val runtime = Runtime.getRuntime()

        Log.d("MEMORY", """
            Available Memory = ${humanReadableByteCountSI(memoryInfo.availMem)}
            Total Memory = ${humanReadableByteCountSI(memoryInfo.totalMem)}
            Runtime Max Memory = ${humanReadableByteCountSI(runtime.maxMemory())}
            Runtime Total Memory = ${humanReadableByteCountSI(runtime.totalMemory())}
            Runtime Free Memory = ${humanReadableByteCountSI(runtime.freeMemory())}
            
            """.trimIndent())

        return """
            Available Memory = ${memoryInfo.availMem.toString()}
            Total Memory = ${memoryInfo.totalMem.toString()}
            Runtime Max Memory = ${runtime.maxMemory()}
            Runtime Total Memory = ${runtime.totalMemory()}
            Runtime Free Memory = ${runtime.freeMemory()}
            
            """.trimIndent()
    }


    fun getApkSize(context: Context) {
        val pm = context.packageManager
        val applicationInfo = pm.getApplicationInfo(context.packageName, 0)
        val file = File(applicationInfo.publicSourceDir)
        val size = file.length()
        Log.d("size_of_apk", humanReadableByteCountSI(size).toString())
    }
}
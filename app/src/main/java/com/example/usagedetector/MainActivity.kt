package com.example.usagedetector

import android.Manifest
import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Debug
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.usage.AppMetricExporter
import com.example.usagedetector.model.CPUModel
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.io.InputStream
import java.text.CharacterIterator
import java.text.StringCharacterIterator


class MainActivity : AppCompatActivity() {

    private lateinit var button : Button
    private lateinit var button2 : Button
    private lateinit var memoryData : Button
    lateinit var appMetricExporter :  AppMetricExporter
    lateinit var  cpuList : ArrayList<CPUModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById<Button>(R.id.hello_world)
        button2 = findViewById<Button>(R.id.hello_world2)
        memoryData = findViewById<Button>(R.id.memory_data)
        cpuList = ArrayList<CPUModel>()
        Log.d("CPU", getCPUDetails().toString())
        getRAMInfo()




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ), 2909
                )
            } else {
                // continue with your code
            }
        } else {
            // continue with your code
        }

        appMetricExporter = AppMetricExporter(this)
        appMetricExporter.createPathForFile("usage9","Usage Deectot")


        button.setOnClickListener{

         appMetricExporter.startCollect("MAINACTIVITY", "check")

        }


        button2.setOnClickListener{
            getCSVData()
        }

        memoryData.setOnClickListener {
            getMemoryData()
        }
    }


    fun checkFunTest() {
        appMetricExporter.startCollect("MAINACTIVITY", "EXIST")

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

    fun getCSVData() {


        var csvfile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS + "/usage8") ,"cpu_usage.csv")

        try {
            val reader = com.opencsv.CSVReader(FileReader(csvfile))
            var nextLine: Array<String>?

                while (reader.readNext().also { nextLine = it } != null) {
                    // nextLine[] is an array of values from the line

                    if(nextLine?.size!! >= 19) {
                        var cpuModel = CPUModel(
                            nextLine?.get(0) ?: "",
                            nextLine?.get(1).toString() ?: "",
                            nextLine?.get(2) ?: "",
                            nextLine?.get(3) ?: "",
                            nextLine?.get(4) ?: "",
                            nextLine?.get(5) ?: "",
                            nextLine?.get(6) ?: "",
                            nextLine?.get(7) ?: "",
                            nextLine?.get(8) ?: "",
                            nextLine?.get(9) ?: "",
                            nextLine?.get(10) ?: "",
                            nextLine?.get(11) ?: "",
                            nextLine?.get(12) ?: "",
                            nextLine?.get(13) ?: "",
                            nextLine?.get(14) ?: "",
                            nextLine?.get(15) ?: "",
                            nextLine?.get(16) ?: "",
                            nextLine?.get(17) ?: "",
                            nextLine?.get(18) ?: "",
                            nextLine?.get(19) ?: ""
                        )

                        if (!nextLine?.get(0).toString().equals("App")) {
                            cpuList.add(cpuModel)
                        }
                    }
                    Log.d("CDSDSDSD", nextLine?.get(0).toString() + (nextLine?.get(1) ?: 0) + "etc...")
                }

        } catch (e: IOException) {
            Toast.makeText(this, "The specified file was not found", Toast.LENGTH_SHORT).show();
        }
    }


    fun getMemoryData() {
        for(i in cpuList.indices)
        {
            Log.d("usedMemory----", cpuList.get(i).RES)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        appMetricExporter.stopCollect()
        appMetricExporter.uploadFileOnServer("","","","")
    }
}
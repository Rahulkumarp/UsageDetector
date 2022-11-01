package com.example.usagedetector.usage

import android.content.Context
import android.os.Debug
import android.util.Log
import com.example.usagedetector.`interface`.AppMetric
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.text.CharacterIterator
import java.text.StringCharacterIterator

class MemoryUsageExporter(context: Context) : AppMetric {


    private companion object{

        const val MEM_USAGE_FILENAME = "mem_usage.txt"

        const val CRITICAL_MEMORY_LOADING = 0.9
    }

    private val absolutePath = context.filesDir.absolutePath

    private val memPw = PrintWriter(
        FileOutputStream(File(context.filesDir, MEM_USAGE_FILENAME), true),
        true)

    override fun export() {
       val runtime = Runtime.getRuntime()
        val maxHeapSizeInMB = convertInMBOnlyNumber(runtime.maxMemory())
        val availHeapSizeInMB = convertInMBOnlyNumber(runtime.freeMemory())
        val usedHeapSizeInMB = convertInMBOnlyNumber(runtime.totalMemory() - runtime.freeMemory())

        val totalNativeMemorySize = convertInMBOnlyNumber(Debug.getNativeHeapSize())
        val availNativeMemoryFreeSize = convertInMBOnlyNumber(Debug.getNativeHeapFreeSize())
        val usedNativeMemoryInMB = totalNativeMemorySize!! - availNativeMemoryFreeSize!!

        if (usedHeapSizeInMB != null) {
            if (maxHeapSizeInMB != null) {
                if(usedHeapSizeInMB > (maxHeapSizeInMB * CRITICAL_MEMORY_LOADING)) {

                    Debug.dumpHprofData("$absolutePath/dump_heap_memory_${System.currentTimeMillis()}.hprof")
                }
            }
        }

        val str = "Used Heap size - $usedHeapSizeInMB Avail Heap size - $availHeapSizeInMB Max Heap Size - $maxHeapSizeInMB " +
                "Used Native Memory -  $usedNativeMemoryInMB Avail Native Memory  Free - $availNativeMemoryFreeSize Total Native Memory - $totalNativeMemorySize "

        memPw.println(str)
        Log.d("Memory -maxMemory ",convertINMBWithString(runtime.maxMemory())!!)
        Log.d("Memory -freeMemory ",convertINMBWithString(runtime.freeMemory())!!)
        Log.d("Memory -UsedMemory",convertINMBWithString(runtime.totalMemory()- runtime.freeMemory())!!)
        Log.d("Memory -NativeHeapSize ",convertINMBWithString(Debug.getNativeHeapSize())!!)
        Log.d("Mem-NativeHeapFreeSize ",convertINMBWithString(Debug.getNativeHeapFreeSize())!!)
        Log.d("Mem-NativeHeapUsedSize ",convertINMBWithString(Debug.getNativeHeapSize() - Debug.getNativeHeapFreeSize() )!!)

    }

    override fun close() {
        memPw.close()
    }

    fun convertInMBOnlyNumber(bytes: Long): Long? {
        var bytes = bytes
        if (-1000 < bytes && bytes < 1000) {
           // return "$bytes B"
            return bytes
        }
        val ci: CharacterIterator = StringCharacterIterator("kMGTPE")
        while (bytes <= -999950 || bytes >= 999950) {
            bytes /= 1000
            ci.next()
        }
        return (bytes / 1000.0).toLong()
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
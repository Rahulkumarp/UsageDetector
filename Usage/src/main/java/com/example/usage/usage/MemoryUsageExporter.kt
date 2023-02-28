package com.example.usage.usage

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.ActivityManager.MemoryInfo
import android.content.Context
import android.os.Debug
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import com.example.usage.`interface`.AppMetric
import com.example.usage.utils.Constants
import com.example.usage.utils.Utils
import java.io.OutputStream
import java.io.PrintWriter
import java.text.CharacterIterator
import java.text.StringCharacterIterator

open class MemoryUsageExporter(var context: Context) : AppMetric {


    var buttonName = ""
    private companion object{

        const val MEM_USAGE_FILENAME = "mem_usage.csv"

        const val CRITICAL_MEMORY_LOADING = 0.9
    }

     // private val absolutePath = context.filesDir.absolutePath


    var outputStrem : OutputStream? = null
  //  private val memPw = PrintWriter(FileOutputStream(mydir, true), true)
  //  private val memPw = PrintWriter(FileOutputStream(File(context.filesDir, MEM_USAGE_FILENAME), true), true)
  lateinit var memPw : PrintWriter
  ///    FileOutputStream(File(context.filesDir, MEM_USAGE_FILENAME), true), true)

    override fun export(
        screenName: String?,
        buttonName: String?,
        fileName: String,
        lineName: String,
        methodName: String
    ) {

        Log.d("xxc", Thread.currentThread().stackTrace[2].methodName)
        var isIdle = Constants.idleMemoryUsage

//        if(Constants.idleMemoryUsage){
//            this.buttonName = "NA"
//        }else{
//            if (buttonName != null) {
//                this.buttonName = buttonName
//            }
//        }

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

                    Log.d("Dump heap memory","dump heap memory")
                    //   Debug.dumpHprofData("$absolutePath/dump_heap_memory_${System.currentTimeMillis()}.hprof")
                }
            }
        }

        var str = "${Utils.getDate()},$screenName,$isIdle ,$buttonName,$fileName, $lineName, $methodName,$usedHeapSizeInMB,$availHeapSizeInMB,$maxHeapSizeInMB,$usedNativeMemoryInMB,$availNativeMemoryFreeSize,$totalNativeMemorySize"
        memPw.println(str)


        Log.d("Memory -freeMemory ",convertINMBWithString(runtime.freeMemory())!!)
        Log.d("Memory -UsedMemory",convertINMBWithString(runtime.totalMemory()- runtime.freeMemory())!!)
        Log.d("Memory -NativeHeapSize ",convertINMBWithString(Debug.getNativeHeapSize())!!)
        Log.d("Mem-NativeHeapFreeSize ",convertINMBWithString(Debug.getNativeHeapFreeSize())!!)
        Log.d("Mem-NativeHeapUsedSize ",convertINMBWithString(Debug.getNativeHeapSize() - Debug.getNativeHeapFreeSize() )!!)


        Constants.idleMemoryUsage = true
    }

    override fun close() {
        memPw.close()
    }

    @SuppressLint("SuspiciousIndentation")
    override fun setPath(folder : String) {
        outputStrem = Utils.setDataInContentProvider(context, MEM_USAGE_FILENAME,"csv",folder)
          if(outputStrem!=null) {
              memPw = PrintWriter(outputStrem, true)
              var header = "Time, Screen Name, Idle, Action Performed, File Name, Line No., Method, Used Heap Size,Avail Heap Size, Max heap size, Used Native Memory, Avail Native MemoryFree,Total Native Memory"
              memPw.println(header)
          }


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
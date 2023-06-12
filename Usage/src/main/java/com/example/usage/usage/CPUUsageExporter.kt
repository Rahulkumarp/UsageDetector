package com.example.usage.usage

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import com.example.usage.`interface`.AppMetric
import com.example.usage.utils.Constants
import com.example.usage.utils.Utils
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.io.PrintWriter
import kotlin.jvm.Throws

class CPUUsageExporter(var context: Context) : AppMetric {

    var buttonName = ""
    private companion object {
        const val CPU_USAGE_FILENAME = "cpu_usage.csv"
        const val PACKAGE_NAME = "com.example.usagedetector"
    }

    var outputStream  : OutputStream? = null

    // val cpuPw = PrintWriter(FileOutputStream(mydir, true), true)
    private var cpuPw :PrintWriter? = null

//    private val cpuPw = PrintWriter(
//        FileOutputStream(
//            File(context.filesDir, CPU_USAGE_FILENAME), true
//        ), true
//    )



    override fun export(
        screenName: String?,
        buttonName: String?,
        fileName: String,
        lineName: String,
        methodName: String
    ) {

//        if(Constants.idleCPUUsage){
//            this.buttonName = "NA"
//        }else{
//            if (buttonName != null) {
//                this.buttonName = buttonName
//            }
//        }

        try {
            recordCpu(screenName, buttonName, fileName, lineName, methodName)
        } catch (th: Throwable) {

        }
    }


    override fun close() {

        cpuPw?.close()
    }

    override fun setPath(folder : String) {
        outputStream = Utils.setDataInContentProvider(context,
            CPU_USAGE_FILENAME,"txt",folder)
        if(outputStream!=null) {
            cpuPw = PrintWriter(outputStream, true)
            cpuPw?.println("App, Time, Screen Name, Idle, Action Performed, File Name, Line No., Method,PID,USER,PR,NI,VIRT,RES,SHR,Status(S/R),[%CPU],%MEM,TIME+,ARGS")

        }


    }


    @SuppressLint("SuspiciousIndentation")
    fun recordCpu(screenName: String?, buttonName: String?, fileName : String, lineName : String, methodName: String,) {
        var isIdle = Constants.idleCPUUsage

        val processLine = readSystemFile("top", "-n", "1")



        if(!processLine[5].contains("top") && processLine[5].length > 10 ) {
            var processLine5 = processLine[5].replace("      ", ",")
                .replace("    ", ",").replace("   ", ",").replace("  ", ",")
                .replace(" ", ",").replace(",,", ",")
                var data5 =
                    " ${Constants.appName},${Utils.getDate()},$screenName, $isIdle,$buttonName,$fileName, $lineName, $methodName $processLine5"
                cpuPw?.println(data5)

        }

        if(!processLine[6].contains("top") && processLine[6].length > 10 ) {
            Log.d("CPUL", processLine[6].length.toString())
            var processLine6 =   processLine[6].replace("      ",",")
                .replace("    ",",").
                replace("   ",",").replace("  ",",")
                .replace(" ",",").replace(",,",",")


                var data6 = " ${Constants.appName}, ${Utils.getDate()},$screenName, $isIdle,$buttonName,$fileName, $lineName, $methodName $processLine6"
                cpuPw?.println(data6)
            }

        if(!processLine[7].contains("top") && processLine[7].length > 10 ) {
        var processLine7 =   processLine[7].replace("      ",",")
            .replace("    ",",").
            replace("   ",",").replace("  ",",")
            .replace(" ",",").replace(",,",",")

            var data7 = " ${Constants.appName}, ${Utils.getDate()},$screenName, $isIdle,$buttonName,$fileName, $lineName, $methodName $processLine7"
            cpuPw?.println(data7)
        }

         Log.d("CPU0", processLine[0].toString())
         Log.d("CPU1", processLine[1].toString())
         Log.d("CPU2", processLine[2].toString())
         Log.d("CPU3", processLine[3].toString())
         Log.d("CPU4", processLine[4].toString())
         Log.d("CPU5", processLine[5].toString())
         Log.d("CPU6", processLine[6].toString())
         Log.d("CPU7", processLine[7].toString())


        Constants.idleCPUUsage = true
//        val file = File(context.filesDir, CPU_USAGE_FILENAME)
//        if(file.exists()) {
//            Log.d("CPU", "YES")
//        }
    }


    @Throws(java.lang.Exception::class)
    private fun readSystemFile(vararg pSystemFile: String): List<String> {
        return Runtime.getRuntime()
            .exec(pSystemFile).inputStream.bufferedReader().useLines { it.toList() }
    }

}
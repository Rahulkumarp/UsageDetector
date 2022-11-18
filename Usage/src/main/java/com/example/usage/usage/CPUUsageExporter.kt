package com.example.usage.usage

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



    override fun export(screenName: String?, buttonName: String?) {

        try {
            recordCpu(screenName,buttonName)
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
            cpuPw?.println("Time, Screen Name, Idle, Action Performed,PID,USER,PR,NI,VIRT,RES,SHR,S[%CPU],,%MEM,TIME+,ARGS")

        }


    }


    fun recordCpu(screenName: String?, buttonName: String?) {
        var isIdle = Constants.idleCPUUsage
//        val processLine = readSystemFile("top","-n","1")
//            .flatMap { it.split(" ") }
//            .map ( String::trim )
//            .filter (String :: isNotEmpty)
//
//        if(processLine.isNotEmpty()){
//            val index =  processLine.indexOfFirst { it == "S" || it == "R" || it == "D" }
//            check(index > -1){
//                "Not Found process state of $PACKAGE_NAME"
//            }
//
//            cpuPw.println(processLine[index + 1].toFloat().toInt().toString())
//        }


        val processLine = readSystemFile("top", "-n", "1")
//            .flatMap { it.split(" ") }
//            .map ( String::trim )
//            .filter (String :: isNotEmpty)
//
//        if(processLine.isNotEmpty()){
//            val index =  processLine.indexOfFirst { it == "S" || it == "R" || it == "D" }
//            check(index > -1){
//                "Not Found process state of $PACKAGE_NAME"
//            }

        for(element in processLine) {
            if(element != processLine[0]) {
                var string  = element
               /* string = string.replace("   ",",")
                string = string.replace("  ",",")
                string = string.replace(" ",",")
          //    */
                //cpuPw?.println(string)
            }
        }


//        var processLine4 =   processLine[4].replace("      ",",")
//            .replace("    ",",").
//            replace("   ",",").replace("  ",",")
//            .replace(" ",",").replace(",,",",")
//       var data =" ${Utils.getDate()},$screenName, Idle,$buttonName +$processLine4"
//        cpuPw?.println(data)
      var processLine5 =   processLine[5].replace("      ",",")
            .replace("    ",",").
            replace("   ",",").replace("  ",",")
            .replace(" ",",").replace(",,",",")
        var data5 =" ${Utils.getDate()},$screenName, $isIdle,$buttonName +$processLine5"
        cpuPw?.println(data5)

        if(processLine[5].contains("top")){
        var processLine7 =   processLine[7].replace("      ",",")
            .replace("    ",",").
            replace("   ",",").replace("  ",",")
            .replace(" ",",").replace(",,",",")

        var data7 =" ${Utils.getDate()},$screenName, $isIdle,$buttonName +$processLine7"
        cpuPw?.println(data7)
        }else{
            var processLine6 =   processLine[6].replace("      ",",")
                .replace("    ",",").
                replace("   ",",").replace("  ",",")
                .replace(" ",",").replace(",,",",")

            var data6 =" ${Utils.getDate()},$screenName, $isIdle,$buttonName +$processLine6"
            cpuPw?.println(data6)
        }

         Log.d("CPU", processLine[0].toString())
         Log.d("CPU", processLine[1].toString())
         Log.d("CPU", processLine[2].toString())
         Log.d("CPU", processLine[3].toString())
         Log.d("CPU", processLine[4].toString())
         Log.d("CPU", processLine[5].toString())
         Log.d("CPU", processLine[6].toString())
         Log.d("CPU", processLine[7].toString())


        Constants.idleCPUUsage = true
        val file = File(context.filesDir, CPU_USAGE_FILENAME)
        if(file.exists()) {
            Log.d("CPU", "YES")
        }
    }


    @Throws(java.lang.Exception::class)
    private fun readSystemFile(vararg pSystemFile: String): List<String> {
        return Runtime.getRuntime()
            .exec(pSystemFile).inputStream.bufferedReader().useLines { it.toList() }
    }

}
package com.example.usage.usage

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import com.example.usage.`interface`.AppMetric
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



    override fun export(screenName: String, buttonName: String) {

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
        }


    }


    fun recordCpu(screenName: String, buttonName: String) {
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

        cpuPw?.println("Screen Name $screenName \n" +
                " Button Clicked $buttonName ${Utils.getDate()}")

        for(element in processLine) {
            if(element != processLine[0]) {
                cpuPw?.println(element)
            }
        }

         Log.d("CPU", processLine[0].toString())
         Log.d("CPU", processLine[1].toString())
         Log.d("CPU", processLine[2].toString())
         Log.d("CPU", processLine[3].toString())
         Log.d("CPU", processLine[4].toString())
         Log.d("CPU", processLine[5].toString())
         Log.d("CPU", processLine[6].toString())
         Log.d("CPU", processLine[7].toString())


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
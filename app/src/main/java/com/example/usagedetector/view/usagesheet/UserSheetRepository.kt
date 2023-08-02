package com.example.usagedetector.view.usagesheet

import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.example.usage.utils.Constants
import com.example.usagedetector.model.CPUModel
import java.io.File
import java.io.FileReader
import java.io.IOException
import javax.inject.Inject

class UserSheetRepository @Inject constructor(){


    fun getCSVData() : ArrayList<CPUModel> {


        var cpuList  =  arrayListOf <CPUModel>()
        var csvfile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS + "/"+ Constants.folderName) ,"cpu_usage.csv")

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
         Log.d("EXCEPTION", e.message.toString())
        }
        return cpuList
    }
}
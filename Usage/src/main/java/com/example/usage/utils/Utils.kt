package com.example.usage.utils

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun getDate(): String {
        val currentTime = Calendar.getInstance().time
        val df = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        val formattedDate = df.format(currentTime.time)
        return formattedDate
    }

    fun setDataInContentProvider(context: Context,fileName: String, mime_type: String, folder : String) : OutputStream? {
        var imageOutStream: OutputStream? = null
        if (!File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS+"/$folder") ,
                fileName
            ).exists()
        ) {

            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.Q){
                val values = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                    put(MediaStore.Images.Media.MIME_TYPE, "csv")
                    put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS+"/$folder")
                }


                context.contentResolver.run {
                    val uri = context.contentResolver.insert(
                        MediaStore.Files.getContentUri("external"), values
                    )
                    imageOutStream = openOutputStream(uri!!)!!

                }

                return imageOutStream!!
            }else{

                val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath
                val newPath = "$path/$folder"
                val file = File(newPath)
                file.mkdir()
                val fileCreate = File(file,fileName)
                fileCreate.createNewFile()
                imageOutStream =   FileOutputStream(fileCreate,true)
                return imageOutStream

            }

        }else {

            return null
        }
    }


}
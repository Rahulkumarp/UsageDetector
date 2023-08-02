package com.example.usage.interfaceapp

import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Query
import java.lang.Exception
import java.util.concurrent.TimeUnit


interface ApiInterface {


    @POST("ApiConstant.LOGIN")
    suspend fun uploadCSVFile(
        @Query("device") device: String,
        @Query("fileName") fileName: String,
        @Query("pathName") pathName: String,
        @Query("minInfo") minInfo: String/*,
        @Body body: RequestBody*/
    )


    companion object {

        @Throws(Exception::class)
        fun create() : ApiInterface {


            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logger)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

//            val client = OkHttpClient.Builder()
//                .addInterceptor(logger)
//                .build()

            return Retrofit.Builder()
                .baseUrl("https://eo")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)

        }
    }
}
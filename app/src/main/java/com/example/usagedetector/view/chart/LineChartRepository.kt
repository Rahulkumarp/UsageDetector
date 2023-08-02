package com.example.usagedetector.view.chart

import com.example.usage.interfaceapp.ApiInterface
import javax.inject.Inject


class LineChartRepository @Inject constructor( private val apiService : ApiInterface){

    suspend fun uploadFile() {
        apiService.uploadCSVFile("","","","")
    }
}
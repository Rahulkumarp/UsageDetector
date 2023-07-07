package com.example.usagedetector.view.chart

import com.example.usage.`interface`.ApiInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject


class LineChartRepository @Inject constructor( private val apiService : ApiInterface){

    suspend fun uploadFile() {
        apiService.uploadCSVFile("","","","")
    }
}
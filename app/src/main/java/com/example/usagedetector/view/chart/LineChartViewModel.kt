package com.example.usagedetector.view.chart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LineChartViewModel @Inject constructor(private val appListRepository: LineChartRepository) : ViewModel() {


    fun uploadFile() {
        viewModelScope.launch {
            appListRepository.uploadFile()
        }
    }
}
package com.example.usagedetector.view.chart

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LineChartViewModel @Inject constructor(private val appListRepository: LineChartRepository) : ViewModel()  {


}
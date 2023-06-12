package com.example.usagedetector.view.usagesheet

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.load.engine.Resource
import com.example.usagedetector.MainActivity
import com.example.usagedetector.model.CPUModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import java.io.FileReader
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class UserSheetViewModel @Inject constructor(private val appListRepository: UserSheetRepository) : ViewModel()  {


    private val _cpuUSageList = MutableLiveData<List<CPUModel>>()
    val cpuUSageList: LiveData<List<CPUModel>>
        get() = _cpuUSageList

    fun getCSVData() {
       val data =  appListRepository.getCSVData()
        _cpuUSageList.value = data
    }
}
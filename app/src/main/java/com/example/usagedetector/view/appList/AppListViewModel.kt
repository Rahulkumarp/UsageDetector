package com.example.usagedetector.view.appList

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.example.usagedetector.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppListViewModel @Inject constructor(private val appListRepository: AppListRepository) : ViewModel()  {


}
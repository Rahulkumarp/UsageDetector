package com.example.usagedetector.view.welcomescreen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.example.usagedetector.MainActivity
import com.example.usagedetector.view.appList.AppListActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(private val welcomeRepository: WelcomeRepository) : ViewModel()  {

    fun startedWelcomeScreen(context: Context) {
        Handler().postDelayed({
            context.startActivity(Intent(context, AppListActivity::class.java))
            (context as Activity).finish()
        }, 3000)
    }
}
package com.example.usagedetector.view.userdevicelist

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import androidx.lifecycle.ViewModel
import com.example.usagedetector.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDeviceListViewModel @Inject constructor(private val appListRepository: UserDeviceListRepository) : ViewModel()  {

    fun startedWelcomeScreen(context: Context) {
        Handler().postDelayed({
            context.startActivity(Intent(context, MainActivity::class.java))
            (context as Activity).finish()
        }, 3000)
    }
}
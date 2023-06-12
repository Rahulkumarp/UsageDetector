package com.example.usagedetector.view.welcomescreen

import android.database.DatabaseUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.usagedetector.R
import com.example.usagedetector.databinding.ActivityWelcomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeActivity : AppCompatActivity() {

    lateinit var activityWelcomeBinding: ActivityWelcomeBinding
    val viewModel: WelcomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_welcome)
        activityWelcomeBinding = ActivityWelcomeBinding.inflate(layoutInflater)
//        setContentView(R.layout.activity_loader_login)
        setContentView(activityWelcomeBinding.root)
        activityWelcomeBinding.viewmodel = viewModel

        viewModel.startedWelcomeScreen(this)
    }
}
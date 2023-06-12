package com.example.usagedetector.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CPUModel(var app : String,
                    var timeDate: String,
                    var screenName : String,
                    var idle : String,
                    var actionPerformed : String,
                    var fileName: String,
                    var lineNo: String,
                    var method : String,
                     var pid: String,
                    var user: String,
                    var PR: String,
                    var NI: String,
                    var VIRT: String,
                    var RES: String,
                    var SHR: String,
                    var status: String,
                    var CPU: String,
                    var MEMPercentage: String,
                    var TIME: String,
                    var ARGS: String) : Parcelable

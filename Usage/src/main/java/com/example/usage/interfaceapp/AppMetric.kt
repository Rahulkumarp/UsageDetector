package com.example.usage.interfaceapp

interface AppMetric {
    fun export(screenName : String? , buttonName : String?, fileName : String, lineName : String , methodName : String )
    fun close()
    fun setPath(folder : String)
}
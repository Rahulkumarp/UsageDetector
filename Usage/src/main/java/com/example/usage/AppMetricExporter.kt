package com.example.usage

import android.content.Context
import android.util.Log
import com.example.usage.usage.BetterUsageExporter
import com.example.usage.usage.CPUUsageExporter
import com.example.usage.usage.MemoryUsageExporter
import com.example.usage.usage.NetworkUsageExporter
import com.example.usage.utils.Constants
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.concurrent.TimeUnit

open class AppMetricExporter(context: Context) {

    private companion object {
        const val INTERVAL_TIME_IN_SEC = 1L
        const val INITIAL_DELAY = 0L
    }

    private val exporters = listOf(CPUUsageExporter(context),
        MemoryUsageExporter(context)
    )

  //  NetworkUsageExporter(context), BetterUsageExporter(context)

    private var disposable: Disposable? = null

    fun startCollect(screenName : String?, buttonName : String?) {
        var thread = Thread.currentThread()
        var fileName = thread.stackTrace[3].fileName
        var lineNumber = thread.stackTrace[3].lineNumber
        var methodName = thread.stackTrace[3].methodName
        Constants.idleMemoryUsage = false
        Constants.idleCPUUsage = false
        disposable = Observable.interval(INITIAL_DELAY, INTERVAL_TIME_IN_SEC, TimeUnit.SECONDS)
            .subscribe({ exporters.forEach{
                it.export(screenName,buttonName,fileName, lineNumber.toString(), methodName)}},{th ->
                Timber.e(th)})
    }

    fun stopCollect(){
        exporters.forEach { it.close() }
        disposable?.dispose()
        disposable = null
    }

    fun createPathForFile(folder: String)
    {
        exporters.forEach { it.setPath(folder) }
    }


}

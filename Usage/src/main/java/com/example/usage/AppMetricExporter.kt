package com.example.usage

import android.content.Context
import com.example.usage.usage.BetterUsageExporter
import com.example.usage.usage.CPUUsageExporter
import com.example.usage.usage.MemoryUsageExporter
import com.example.usage.usage.NetworkUsageExporter
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.concurrent.TimeUnit

open class AppMetricExporter(context: Context) {

    private companion object {
        const val INTERVAL_TIME_IN_SEC = 10L
        const val INITIAL_DELAY = 0L
    }

    private val exporters = listOf(
        MemoryUsageExporter(context), CPUUsageExporter(context),
        NetworkUsageExporter(context), BetterUsageExporter(context)
    )

    private var disposable: Disposable? = null

    fun startCollect() {
        disposable = Observable.interval(INITIAL_DELAY, INTERVAL_TIME_IN_SEC, TimeUnit.SECONDS)
            .subscribe({ exporters.forEach{ it.export()}},{th ->
                Timber.e(th)})
            }

    fun stopCollect(){
        exporters.forEach { it.close() }
        disposable?.dispose()
        disposable = null
    }


}

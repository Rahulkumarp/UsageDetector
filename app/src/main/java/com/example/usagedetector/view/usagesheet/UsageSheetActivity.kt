package com.example.usagedetector.view.usagesheet

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.usagedetector.databinding.ActivityUsageSheetBinding
import com.example.usagedetector.model.CPUModel
import com.example.usagedetector.view.adapter.USageSheetListAdapter
import com.example.usagedetector.view.chart.LineChartActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsageSheetActivity : AppCompatActivity() {

    lateinit var activityUsageSheetBinding: ActivityUsageSheetBinding
    val viewModel: UserSheetViewModel by viewModels()
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var alAdapter: USageSheetListAdapter
    lateinit var arrayList: ArrayList<CPUModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityUsageSheetBinding = ActivityUsageSheetBinding.inflate(layoutInflater)
        setContentView(activityUsageSheetBinding.root)
        activityUsageSheetBinding.viewmodel = viewModel


        arrayList = arrayListOf()
        viewModel.getCSVData()
        init()

        activityUsageSheetBinding.sno.setOnClickListener {
            if(arrayList.size > 0) {
                val intent = Intent(this, LineChartActivity::class.java)
                intent.putExtra("List", arrayList)
                intent.putExtra("type", "all")
                startActivity(intent)
            }
        }

        activityUsageSheetBinding.linearRes.setOnClickListener {
            if(arrayList.size > 0) {
                val intent = Intent(this, LineChartActivity::class.java)
                intent.putExtra("List", arrayList)
                intent.putExtra("type", "res")
                startActivity(intent)
            }
        }

        activityUsageSheetBinding.linearShr.setOnClickListener {
            if(arrayList.size > 0) {
                val intent = Intent(this, LineChartActivity::class.java)
                intent.putExtra("List", arrayList)
                intent.putExtra("type", "shr")
                startActivity(intent)
            }
        }

        activityUsageSheetBinding.linearVirt.setOnClickListener {
            if(arrayList.size > 0) {
                val intent = Intent(this, LineChartActivity::class.java)
                intent.putExtra("List", arrayList)
                intent.putExtra("type", "virt")
                startActivity(intent)
            }
        }

        activityUsageSheetBinding.linearCpu.setOnClickListener {
            if(arrayList.size > 0) {
                val intent = Intent(this, LineChartActivity::class.java)
                intent.putExtra("List", arrayList)
                intent.putExtra("type", "cpu")
                startActivity(intent)
            }
        }

        activityUsageSheetBinding.linearMem.setOnClickListener {
            if(arrayList.size > 0) {
                val intent = Intent(this, LineChartActivity::class.java)
                intent.putExtra("List", arrayList)
                intent.putExtra("type", "mem")
                startActivity(intent)
            }
        }
    }


    private fun init() {
        viewModel.cpuUSageList.observe(this, Observer {
            if(it !=null && it.isNotEmpty()) {
                arrayList.clear()
                arrayList = it as ArrayList<CPUModel>
                alAdapter = USageSheetListAdapter(arrayList,this)
                linearLayoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
                activityUsageSheetBinding.recyclerviewSheet.layoutManager = linearLayoutManager
                activityUsageSheetBinding.recyclerviewSheet.adapter  =alAdapter
                alAdapter?.notifyDataSetChanged()
            }
        })
    }
}
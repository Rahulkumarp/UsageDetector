package com.example.usagedetector.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.usagedetector.R
import com.example.usagedetector.databinding.ItemAppListBinding
import com.example.usagedetector.databinding.ItemUsageSheetBinding
import com.example.usagedetector.databinding.ItemUserDeviceBinding
import com.example.usagedetector.model.AppListModel
import com.example.usagedetector.model.CPUModel
import com.example.usagedetector.model.UserDeviceListModel
import com.example.usagedetector.view.usagesheet.UsageSheetActivity
import com.example.usagedetector.view.userdevicelist.UserDeviceListActivity

class USageSheetListAdapter(var appList :  List<CPUModel>, var context: Context) : RecyclerView.Adapter<USageSheetListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): USageSheetListAdapter.ViewHolder {
       val binding  =  ItemUsageSheetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

       holder.bind(appList[position], context, position)
    }

    override fun getItemCount(): Int {
        return appList.size
    }

    class ViewHolder(var binding : ItemUsageSheetBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cpuModel : CPUModel, context : Context, pos : Int) {
           // Glide.with(context).load(cpuModel.).into(binding.appImage);
            binding.sno.text = pos.toString()
            binding.app.text = cpuModel.app
            binding.timeDate.text = cpuModel.timeDate
            binding.scrennName.text = cpuModel.screenName
            binding.idle.text = cpuModel.idle
            binding.actionPerfomed.text = cpuModel.actionPerformed
            binding.fileName.text = cpuModel.fileName
            binding.lineNo.text = cpuModel.lineNo
            binding.method.text = cpuModel.method
            binding.pid.text = cpuModel.pid
            binding.user.text = cpuModel.user
            binding.pr.text = cpuModel.PR
            binding.ni.text = cpuModel.NI
            binding.virt.text = cpuModel.VIRT
            binding.res.text = cpuModel.RES
            binding.shr.text = cpuModel.SHR
            binding.status.text = cpuModel.status
            binding.cpuP.text = cpuModel.CPU
            binding.memoryP.text = cpuModel.MEMPercentage
            binding.time2.text = cpuModel.TIME
            binding.args.text = cpuModel.ARGS



        }

    }
}
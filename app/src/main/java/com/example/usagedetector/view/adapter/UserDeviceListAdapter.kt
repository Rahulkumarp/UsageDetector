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
import com.example.usagedetector.MainActivity
import com.example.usagedetector.R
import com.example.usagedetector.databinding.ItemAppListBinding
import com.example.usagedetector.databinding.ItemUserDeviceBinding
import com.example.usagedetector.model.AppListModel
import com.example.usagedetector.model.UserDeviceListModel
import com.example.usagedetector.view.usagesheet.UsageSheetActivity
import com.example.usagedetector.view.userdevicelist.UserDeviceListActivity

class UserDeviceListAdapter(var appList :  List<UserDeviceListModel>, var context: Context) : RecyclerView.Adapter<UserDeviceListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDeviceListAdapter.ViewHolder {
       val binding  =  ItemUserDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

       holder.bind(appList[position], context)
    }

    override fun getItemCount(): Int {
        return appList.size
    }

    class ViewHolder(var binding : ItemUserDeviceBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(appListModel: UserDeviceListModel, context : Context)
        {
          //  Glide.with(context).load(appListModel.image).into(binding.appImage);
            binding.deviceName.text = appListModel.deviceName
            binding.userId.text = "User - " + appListModel.userdID

            binding.buttonCpuUsage.setOnClickListener {
                context.startActivity(Intent(context, UsageSheetActivity::class.java))
            }
            binding.cardview.setOnClickListener {
                context.startActivity(Intent(context, MainActivity::class.java))
            }
        }

    }
}
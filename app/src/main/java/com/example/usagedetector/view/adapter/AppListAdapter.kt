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
import com.example.usagedetector.model.AppListModel
import com.example.usagedetector.view.userdevicelist.UserDeviceListActivity

class AppListAdapter(var appList :  List<AppListModel>, var context: Context) : RecyclerView.Adapter<AppListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppListAdapter.ViewHolder {
       val binding  =  ItemAppListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

       holder.bind(appList[position], context)
    }

    override fun getItemCount(): Int {
        return appList.size
    }

    class ViewHolder(var binding : ItemAppListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(appListModel: AppListModel, context : Context)
        {
           Glide.with(context).load(appListModel.image).placeholder(R.drawable.ic_mobile).into(binding.appImage);
            binding.appName.text = appListModel.appName
            binding.carview.setOnClickListener {
                context.startActivity(Intent(context,UserDeviceListActivity::class.java))
            }
        }

    }
}
package com.example.usagedetector.view.userdevicelist

import android.R
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.usagedetector.databinding.ActivityAppListBinding
import com.example.usagedetector.databinding.ActivityUserDeviceListBinding
import com.example.usagedetector.model.AppListModel
import com.example.usagedetector.model.UserDeviceListModel
import com.example.usagedetector.view.adapter.AppListAdapter
import com.example.usagedetector.view.adapter.UserDeviceListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDeviceListActivity : AppCompatActivity() {

    lateinit var bindding: ActivityUserDeviceListBinding
    val viewModel : UserDeviceListViewModel by viewModels()
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var alAdapter: UserDeviceListAdapter
    lateinit var arrayList: ArrayList<UserDeviceListModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindding = ActivityUserDeviceListBinding.inflate(layoutInflater)
        setContentView(bindding.root)
        bindding.viewmodel = viewModel


        bindding.toolbar.title = "Device List"
        setSupportActionBar(bindding.toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        arrayList = arrayListOf()


        var model =  UserDeviceListModel("One Plus 9R ","ADSSD7")
        var model2 =  UserDeviceListModel("Oppo F3 ","SDJKS9")
        var model3 =  UserDeviceListModel("SAMSUNG NOTE ","DFGHJK8")
        var model4 =  UserDeviceListModel("MOTOROLA G9","CVBN6789")
        var model5 =  UserDeviceListModel("NOKIA L1 ","RTYUG889")
        var model6 =  UserDeviceListModel("ONE PLUS Node3","CVBNRTYUH78")
        var model7 =  UserDeviceListModel("MICROMAX A1","VBNMGHJU89")
        var model8 =  UserDeviceListModel("TECHNO 9 ","678YU67")

        arrayList.add(model)
        arrayList.add(model2)
        arrayList.add(model3)
        arrayList.add(model4)
        arrayList.add(model5)
        arrayList.add(model6)
        arrayList.add(model7)
        arrayList.add(model8)

        alAdapter = UserDeviceListAdapter(arrayList,this)
        linearLayoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        bindding.recycleViewUserDeviceList.layoutManager = linearLayoutManager
        bindding.recycleViewUserDeviceList.adapter  =alAdapter
        alAdapter?.notifyDataSetChanged()

    }


    override fun onOptionsItemSelected(@NonNull item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
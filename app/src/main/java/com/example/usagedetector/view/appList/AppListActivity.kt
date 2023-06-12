package com.example.usagedetector.view.appList

import android.R
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Orientation
import com.example.usagedetector.databinding.ActivityAppListBinding
import com.example.usagedetector.model.AppListModel
import com.example.usagedetector.view.adapter.AppListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppListActivity : AppCompatActivity() {

    lateinit var activityAppListBinding: ActivityAppListBinding
    val viewModel : AppListViewModel by viewModels()
    lateinit var linearLayoutManager: GridLayoutManager
    lateinit var alAdapter: AppListAdapter
    lateinit var arrayList: ArrayList<AppListModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityAppListBinding = ActivityAppListBinding.inflate(layoutInflater)
        setContentView(activityAppListBinding.root)
        activityAppListBinding.viewmodel = viewModel


        activityAppListBinding.toolbar.title = "App List"
        setSupportActionBar(activityAppListBinding.toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)


        arrayList = arrayListOf()


        var model =  AppListModel("Twitter One","https://cdn3.iconfinder.com/data/icons/2018-social-media-logotypes/1000/2018_social_media_popular_app_logo_twitter-512.png")
        var model2 =  AppListModel("ManiPA ","https://logos-world.net/wp-content/uploads/2021/02/App-Store-Logo.png")
        var model3 =  AppListModel("INDA ","https://cdn.logojoy.com/wp-content/uploads/20220329171710/telegram-app-logo.png")
        var model4 =  AppListModel("MANUOP ","https://cdn3.iconfinder.com/data/icons/2018-social-media-logotypes/1000/2018_social_media_popular_app_logo_youtube-512.png")
        var model5 =  AppListModel("DHAIL ","https://static.vecteezy.com/system/resources/previews/000/578/606/original/vector-dental-care-logo-and-symbols-template-icons-app.jpg")
        var model6 =  AppListModel("DIL KILL ","https://cdn.logojoy.com/wp-content/uploads/20220329171710/telegram-app-logo.png")
        var model7 =  AppListModel("KILLER ","https://upload.wikimedia.org/wikipedia/commons/thumb/3/3a/Xbox_app_logo.svg/2048px-Xbox_app_logo.svg.png")
        var model8 =  AppListModel("POPPP ","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR9lA4PoyGoC0q1E8QQm7i9YGZM-LOw5On0TA&usqp=CAU")

        arrayList.add(model)
        arrayList.add(model2)
        arrayList.add(model3)
        arrayList.add(model4)
        arrayList.add(model5)
        arrayList.add(model6)
        arrayList.add(model7)
        arrayList.add(model8)

        alAdapter = AppListAdapter(arrayList,this)
       // linearLayoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        linearLayoutManager = GridLayoutManager(this,2)
        activityAppListBinding.recycleViewAppList.layoutManager = linearLayoutManager
        activityAppListBinding.recycleViewAppList.adapter  =alAdapter
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
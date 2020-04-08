package com.example.covid_19nalert

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import androidx.work.*
import com.example.covid_19nalert.databinding.ActivityMainBinding
import com.example.covid_19nalert.databinding.FragmentContentViewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import java.util.concurrent.TimeUnit

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val toolBar = binding.mainToolbar
        toolBar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_black1_24dp)
        val navController = this.findNavController(R.id.my_nav_host)
        /*NavigationUI.setupWithNavController(toolBar, navController)*/
       //setSupportActionBar(toolBar)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
      // val appBarConfigur = AppBarConfiguration(navController.graph).topLevelDestinations


        /*toolBar.setupWithNavController(navController, appBarConfigur)*/
        NavigationUI.setupWithNavController(toolBar, navController)


    }

  override fun onSupportNavigateUp(): Boolean {
      val navController = this.findNavController(R.id.my_nav_host)
      /*val appBarConfigur = AppBarConfiguration(navController.graph)*/
        return navController.navigateUp()
    }
}

/*class ApplicationClass : Application(){
    private val applicationCoroutine = CoroutineScope(Dispatchers.Default)
    override fun onCreate() {
        super.onCreate()
        workUnit()
    }



       private fun setUpWork() {
       *//**Setting up constraint for the work request**//*
        val workConstraints = Constraints.Builder()
           .setRequiredNetworkType(NetworkType.CONNECTED)
           .build()

        *//**Setting up the work request**//*
        val workRequest = PeriodicWorkRequestBuilder<WorkerClass>(15, TimeUnit.MINUTES)
            .setConstraints(workConstraints)
            .build()

        *//**Setting up work manager**//*
        WorkManager.getInstance().enqueueUniquePeriodicWork(WorkerClass.WORKER_NAME, ExistingPeriodicWorkPolicy.KEEP, workRequest)
    }

    fun workUnit() {
        applicationCoroutine.launch {
            setUpWork()
        }
    }
}*/

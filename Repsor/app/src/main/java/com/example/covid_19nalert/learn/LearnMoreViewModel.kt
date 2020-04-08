package com.example.covid_19nalert.learn

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.covid_19nalert.cases_summary.NetWorkMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job


/**Learn fragment viewModel class**/
class LearnMoreViewModel(
    application: Application) : AndroidViewModel(application){
    val viewJob = Job()
    val scope2 = CoroutineScope(viewJob + Dispatchers.Main)
    val allLearningResource : LiveData<List<LearnEntity>>

    private val _moveResourceToWebView = MutableLiveData<LearnEntity>()
    val moveResourceToWebView : LiveData<LearnEntity>
    get() = _moveResourceToWebView
    init {
        val database = LearnDatabase.getInstance(application, scope2).learnDao
         allLearningResource = database.retrieveAllLearningResource()
    }

    fun receivedLearningResource(resour : LearnEntity){
        _moveResourceToWebView.value = resour
    }

    fun doneSendingLearningResource(){
        _moveResourceToWebView.value = null
    }
}
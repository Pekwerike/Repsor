package com.example.covid_19nalert.corona_statistics

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.covid_19nalert.cases_summary.NetWorkMode
import com.example.covid_19nalert.domain.CountryDomainModel
import com.example.covid_19nalert.domain.RoughCheck
import com.example.covid_19nalert.network_service.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CoronaStatisticViewModel(val app: Application): AndroidViewModel(app){

    /**Coroutine job for asynchronous processing**/
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    //List of countries statistic on COVID-19
    private val _allStatisticsList = MutableLiveData<List<CountryDomainModel>>()
    val allStatisticList : LiveData<List<CountryDomainModel>>
    get() = _allStatisticsList


    private val _internetStatus = MutableLiveData<NetWorkMode>()
    val internetStatus : LiveData<NetWorkMode>
        get() = _internetStatus

    private val _displayStuff = MutableLiveData<RoughCheck>()
    val displayStuff : LiveData<RoughCheck>
    get() = _displayStuff

    init {
        getNewSummary()
    }


    /**Fetching data from the network**/
    fun getNewSummary() {
        coroutineScope.launch {
            try {
               _internetStatus.value = NetWorkMode.LOADING
               val worldometerStats = WorldometerApiInterface.CreateApiService().getCountries().await()
                val nigeriaStat = WorldometerNigeriaApiInterface.CreateApiService().getCountries().await()
                val globalStat = WorldometerOverallApiInterface.CreateApiService().getCountries().await()

               _displayStuff.value  = RoughCheck(globalStat.toDomainModel(),nigeriaStat.toDomainModel().first(),worldometerStats.toDomainModel())

                Log.i("worldometer", "${worldometerStats.toString()}")
                _allStatisticsList.value = worldometerStats.toDomainModel()
                _internetStatus.value = NetWorkMode.DONE

            }catch (e : Exception){
                _internetStatus.value = NetWorkMode.ERROR
            }
        }
    }


    /**Cancelling all coroutine jobs**/
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
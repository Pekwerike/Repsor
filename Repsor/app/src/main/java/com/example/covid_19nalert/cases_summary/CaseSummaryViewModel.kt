package com.example.covid_19nalert.cases_summary

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.covid_19nalert.domain.NewsDomainModel
import com.example.covid_19nalert.network_service.NewsApiInterface
import com.example.covid_19nalert.network_service.toDomainModel
import kotlinx.coroutines.*
import java.util.*

/**Enum class to determine the network status**/
enum class NetWorkMode{
    LOADING, DONE, ERROR
}

/**ViewModel class for the news articles**/
class CaseSummaryViewModel(val app: Application): AndroidViewModel(app){

    /**Coroutine scope for asynchronous work**/
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    /**LiveDate to hold failure message if the network request fails**/
    private val _failureResponse = MutableLiveData<String>()
    val failureResponse : LiveData<String>
    get() = _failureResponse

    /**Returned liveData that will be sent to the news adapter api for display**/
    private val _rough2 = MutableLiveData<List<NewsDomainModel>>()
    val rough2 : LiveData<List<NewsDomainModel>>
    get() = _rough2


    /**Variable to hold the news article selected by the users, in order to be displayed in the webview**/
    private val _newsArticleContent = MutableLiveData<NewsDomainModel>()
    val newsArticle : LiveData<NewsDomainModel>
    get() = _newsArticleContent

    /**LiveData to inform the progressBar of the users network status**/
    private val _networkModeStaus = MutableLiveData<NetWorkMode>()
    val netWorkMode : LiveData<NetWorkMode>
    get() = _networkModeStaus


    init {
        getHttpsResponse()
    }


    /**Responsible for all network requests**/
     fun getHttpsResponse() {
        coroutineScope.launch {
            try {
               _networkModeStaus.value = NetWorkMode.LOADING
               val returnedNewsArticles = NewsApiInterface.CreateApiService().getNews().await()
                _rough2.value = returnedNewsArticles.articles.toDomainModel()
               _networkModeStaus.value = NetWorkMode.DONE

            }catch (e : Exception){
                _failureResponse.value = "Failure : ${e.message}"
                _networkModeStaus.value = NetWorkMode.ERROR
            }
        }
    }


    /**Function to determine the news article the user selected**/
    fun displayNewsArticleContent(newsArticle : NewsDomainModel) {
        _newsArticleContent.value = newsArticle
    }

    /**This function will be called to set the newsArticleContent live
     * Data to null after the user has navigated to the webView**/
    fun doneDisplayingNewsArticle() {
        _newsArticleContent.value = null
    }


    /**cancelling all active coroutine jobs**/
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
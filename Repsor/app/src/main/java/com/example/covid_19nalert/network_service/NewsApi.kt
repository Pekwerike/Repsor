package com.example.covid_19nalert.network_service

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

/**Base Url for news http api**/
private val BASE_URL = "https://newsapi.org/"

/**creating moshi library to parse Json response into kotlin object**/
private val moshi = Moshi
    .Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**Creating Retrofit Object for the covid19api.com url**/
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

/**Retrofit Http Api**/
interface NewsApiInterface {
    @GET("v2/top-headlines?q=coronavirus&country=ng&from=2020-03-20&to=2020-03-24&sortBy=popularity&apiKey=8baa90006b5f4721879c1b4fe9e80f8d")
    fun getNews() : Deferred<JsonNewsObject>


    companion object{
        fun CreateApiService() : NewsApiInterface{
            return retrofit.create(NewsApiInterface::class.java)
        }
    }
}

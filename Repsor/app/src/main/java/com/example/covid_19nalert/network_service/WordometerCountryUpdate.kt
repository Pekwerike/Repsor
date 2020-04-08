package com.example.covid_19nalert.network_service

import com.example.covid_19nalert.domain.CountryDomainModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.text.NumberFormat
import java.util.*

/**Class to parse the returned json response into kotlin objects**/
data class WordometerCountryUpdate(
    val countryCode : String?,
    val country : String,
    val lat : Float?,
    val lng : Float?,
    val totalConfirmed : Int?,
    val totalDeaths : Int?,
    val totalRecovered : Int?,
    val dailyConfirmed : Int?,
    val dailyDeaths : Int?,
    val activeCases : Int?,
    val totalCritical : Int?,
    val totalConfirmedPerMillionPopulation: Int?,
    val FR :String?,
    val PR : String?,
    val lastUpdated : String?
)

/**Extension function to transform the list of countries corona virus stats returned by the network**/
fun List<WordometerCountryUpdate>.toDomainModel() : List<CountryDomainModel> {
    return map{
        CountryDomainModel(
          country = it.country,
            countryCode = it.countryCode,
            totalConfirmed = "Total Cases: " + NumberFormat.getNumberInstance().format(it.totalConfirmed),
            totalCritical = it.totalCritical,
            totalDeaths =  "Deaths: " + NumberFormat.getNumberInstance().format(it.totalDeaths),
            totalRecovered = "Recovered: " + NumberFormat.getNumberInstance().format(it.totalRecovered),
            dailyConfirmed = "New Cases: " + NumberFormat.getNumberInstance().format(it.dailyConfirmed),
            dailyDeaths = it.dailyDeaths,
            activeCases = it.activeCases,
            totalConfirmedInt = it.totalConfirmed
        )
    }
}

/**Base URL for retrofit**/
private val BASE_URL = "https://api.coronatracker.com/v3/stats/worldometer/"

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

/**Retrofit Http Api interface**/
interface WorldometerApiInterface {
    @GET("country")
    fun getCountries() : Deferred<List<WordometerCountryUpdate>>


    companion object{
        fun CreateApiService() : WorldometerApiInterface{
            return retrofit.create(WorldometerApiInterface::class.java)
        }
    }
}

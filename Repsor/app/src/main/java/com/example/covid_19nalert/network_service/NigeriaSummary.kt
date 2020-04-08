package com.example.covid_19nalert.network_service

import androidx.lifecycle.Transformations.map
import com.example.covid_19nalert.domain.NigeriaDomainSummary
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.text.NumberFormat

/**Class to parse the returned json response into kotlin objects**/
data class NigeriaSummary(
    val countryCode : String?,
    val country : String?,
    val lat : Float?,
    val lng : Float?,
    val totalConfirmed : Int?,
    val totalDeaths : Int?,
    val totalRecovered : Int?,
    val dailyConfirmed : Int?,
    val dailyDeaths : Int?,
    val activeCases : Int?,
    val totalCritical : Int?,
    val totalConfirmedPerMillionPopulation : Int?,
    val FR :String?,
    val PR : String?,
    val lastUpdated : String?
)

/**Transforming the returned Nigeria coronavirus statistics into a domain object**/
fun List<NigeriaSummary>.toDomainModel() : List<NigeriaDomainSummary> {
    return map {
        NigeriaDomainSummary(
            country = it.country,
            totalRecovered = NumberFormat.getInstance().format(it.totalRecovered),
            totalConfirmed = NumberFormat.getInstance().format(it.totalConfirmed),
            totalDeaths = NumberFormat.getInstance().format(it.totalDeaths),
            dailyConfirmed = NumberFormat.getInstance().format(it.dailyConfirmed)

        )
    }
}

/**Base Url**/
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

/**Retrofit Http Api**/
interface WorldometerNigeriaApiInterface {
    @GET("country?countryCode=NG")
    fun getCountries() : Deferred<List<NigeriaSummary>>


    companion object{
        fun CreateApiService() : WorldometerNigeriaApiInterface{
            return retrofit.create(WorldometerNigeriaApiInterface::class.java)
        }
    }
}
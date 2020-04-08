package com.example.covid_19nalert.network_service

import com.example.covid_19nalert.domain.GlobalDomainSummary
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.text.NumberFormat

/**Data class for the global coronavirus summary coming directly from the Rest Api**/
data class OverallStatApen(
    val totalConfirmed : Int?,
    val totalDeaths : Int?,
    val totalRecovered : Int?,
    val totalNewCases : Int?,
    val totalNewDeaths : Int?,
    val totalActiveCases : Int?,
    val totalCasesPerMillionPop : Int?,
    val created : String?
)

/**Transforming the returned global summary into a domain model, that the global summary layout and viewModel will interface**/
fun OverallStatApen.toDomainModel() : GlobalDomainSummary {
    return GlobalDomainSummary(
          NumberFormat.getInstance().format(totalConfirmed),
          NumberFormat.getInstance().format(totalDeaths),
        NumberFormat.getInstance().format(totalRecovered)
    )
}

//Base Url
private val BASE_URL = "https://api.coronatracker.com/v3/stats/worldometer/"

//creating moshi library to parse Json response into kotlin object
private val moshi = Moshi
    .Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//Creating Retrofit Object for the covid19api.com url
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

//Retrofit Http Api
interface WorldometerOverallApiInterface {
    @GET("global")
    fun getCountries() : Deferred<OverallStatApen>


    companion object{
        fun CreateApiService() : WorldometerOverallApiInterface{
            return retrofit.create(WorldometerOverallApiInterface::class.java)
        }
    }
}

package com.example.covid_19nalert.domain

import android.os.Parcelable
import com.example.covid_19nalert.network_service.OverallStatApen
import kotlinx.android.parcel.Parcelize

/**Domain Model for countries corona stats data**/
data class CountryDomainModel(
    val countryCode : String?,
    val country : String?,
    val totalConfirmed : String?,
    val totalConfirmedInt : Int?,
    val totalDeaths : String?,
    val totalRecovered : String?,
    val dailyConfirmed : String?,
    val dailyDeaths : Int?,
    val activeCases : Int?,
    val totalCritical : Int?
)


/**Domain Model for the news articles**/
@Parcelize
data class NewsDomainModel(
    val source : String?,
    val author : String?,
    val title : String?,
    val description : String?,
    val url : String,
    val urlToImage : String?,
    val publishedAt : String?,
    val content : String?
) : Parcelable

/**Central domain model for the Returned Nigeria corona stats json response**/
data class NigeriaDomainSummary(
    val country : String?,
    val totalConfirmed : String?,
    val totalDeaths : String?,
    val totalRecovered : String?,
    val dailyConfirmed : String?
)

/**Central domain model for the returned global corona stats json response**/
data class GlobalDomainSummary(
    val totalConfirmed : String?,
    val totalDeaths : String?,
    val totalRecovered : String?
)

/**Data class to hold all threee different domain model classes, so as to send them to the countriesStatAdapter**/
data class RoughCheck(
    val overAllStat : GlobalDomainSummary,
    val nigeriaStat : NigeriaDomainSummary,
    val countrySummary : List<CountryDomainModel>
)
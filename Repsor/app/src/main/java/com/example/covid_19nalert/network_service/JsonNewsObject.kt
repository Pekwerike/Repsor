package com.example.covid_19nalert.network_service

import android.os.Build
import android.text.format.DateFormat.format
import androidx.annotation.RequiresApi
import androidx.lifecycle.Transformations.map
import com.example.covid_19nalert.domain.NewsDomainModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*


data class JsonNewsObject(
    val status : String,
    val totalResults : Int,
    val articles : List<NewsArticle>
)

data class NewsArticle(
    val source : IdAndName?,
    val author : String?,
    val title : String?,
    val description : String?,
    val url : String,
    val urlToImage : String?,
    val publishedAt : String?,
    val content : String?
)

data class IdAndName(
    val id : String?,
    val name : String?
)




fun List<NewsArticle>.toDomainModel() : List<NewsDomainModel>{
    return map {
         /*val date =  Calendar.getInstance().set(
            it.publishedAt?.substring(0, 4)!!.toInt(),
            it.publishedAt?.substring(6, 7)!!.toInt(),
            it.publishedAt.substring(9, 10).toInt(),
            it.publishedAt.substring(12, 13).toInt(),
            it.publishedAt.substring(15, 16).toInt(),
             it.publishedAt.substring(18, 18).toInt()
        ).toString()*/

            NewsDomainModel(
                source = it.source?.name,
                author = it.author,
                title = it.title,
                description = it.description,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt?.take(10),
                content = it.content
            )

    }
}


package com.example.covid_19nalert.adapters

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.covid_19nalert.cases_summary.NetWorkMode


/**ImageView adapter for the glide library to display the image**/
@BindingAdapter("imageUrl")
fun ImageView.setImg (imgUrl : String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
            .load(imgUri)
            .into(this)
    }

}


/**Function to determine the visibilty of the loading progressBar**/
@BindingAdapter("showProgressBar")
fun ProgressBar.determineVisibilty (networkState : NetWorkMode?){
    visibility = when(networkState){
        NetWorkMode.LOADING -> View.VISIBLE
        NetWorkMode.ERROR -> View.GONE
        NetWorkMode.DONE -> View.GONE
        else -> View.VISIBLE
    }
}

/**Determines when to show the network error image view based on the network mode enum class**/
@BindingAdapter("showNetworkError")
fun ImageView.determineVisibility(networkState : NetWorkMode?){
    visibility = when(networkState){
        NetWorkMode.ERROR -> View.VISIBLE
        NetWorkMode.LOADING -> View.GONE
        NetWorkMode.DONE -> View.GONE
        else -> View.GONE
    }
}

/**Determines when to show to refresh page info based on the network mode enum class**/
@BindingAdapter("showNetworkError")
fun TextView.determineVisibility(networkState : NetWorkMode?){
    visibility = when(networkState){
        NetWorkMode.ERROR -> View.VISIBLE
        NetWorkMode.LOADING -> View.GONE
        NetWorkMode.DONE -> View.GONE
        else -> View.GONE
    }
}
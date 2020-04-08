package com.example.covid_19nalert.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.covid_19nalert.databinding.FragmentCaseSummaryBinding
import com.example.covid_19nalert.databinding.NewsViewBinding
import com.example.covid_19nalert.domain.NewsDomainModel
import com.example.covid_19nalert.network_service.JsonNewsObject

/**RecyclerView Adapter for the news data**/
class ListAdapter1(val newsClickListener : OnClickListener) : ListAdapter<NewsDomainModel, ListAdapter1.NewsViewHolder>(DiffCallback()){


    /**ViewHolder**/
    class NewsViewHolder(val newsBinding: NewsViewBinding) : RecyclerView.ViewHolder(newsBinding.root) {

        fun bind(newsItem : NewsDomainModel){
            newsBinding.news = newsItem
            newsBinding.executePendingBindings()

        }

        companion object {
        fun newsViewHolder(parent: ViewGroup) : NewsViewHolder {
            val binding = NewsViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return NewsViewHolder(binding)
        }
    }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  NewsViewHolder{
        return NewsViewHolder.newsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = getItem(position)
        holder.itemView.setOnClickListener {
            newsClickListener.onClick(news)
        }
        holder.bind(news)

    }


}

/**Onclick Listener for each newsItem in the recyclerView**/
class OnClickListener (val clickListener : (news : NewsDomainModel) -> Unit) {
    fun onClick (news : NewsDomainModel) {
        return clickListener(news)
    }
}

/**Calculating the difference between the new and old lists**/
class DiffCallback : DiffUtil.ItemCallback<NewsDomainModel>(){
    override fun areItemsTheSame(oldItem: NewsDomainModel, newItem: NewsDomainModel): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(
        oldItem: NewsDomainModel,
        newItem: NewsDomainModel
    ): Boolean {
        return oldItem == newItem
    }

}
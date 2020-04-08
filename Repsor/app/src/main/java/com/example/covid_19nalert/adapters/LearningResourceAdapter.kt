package com.example.covid_19nalert.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.covid_19nalert.R
import com.example.covid_19nalert.databinding.SingleLearnResourceBinding
import com.example.covid_19nalert.learn.LearnEntity

/**RecyclerView Adapter to display the learn resources**/
class LearningResourceAdapter(val clickListener : LearningResourceOnClick) : ListAdapter<LearnEntity, LearningResourceAdapter.LearnResourceViewModel>(DiffCallOh()) {

    /**ViewHolder for the items in the dataset**/
    class LearnResourceViewModel(private val binding : SingleLearnResourceBinding) : RecyclerView.ViewHolder(binding.root){
         fun bind(resourceCollected : LearnEntity){
             binding.singleLearnResource = resourceCollected
             binding.learnCardView.setCardBackgroundColor(when(resourceCollected.id){
                 in 1..3 -> Color.rgb(242,161,4)
                 in 3..7 -> Color.rgb(255,193,7)
                 in 8..11 -> Color.rgb(211,47,47)
                 in 12..17-> Color.rgb(0,115,79)
                 in 18..21 -> Color.rgb(76,175,80)
                 in 22..25 -> Color.rgb(0,115,79)
                 else -> Color.WHITE
             })
         }



        companion object {
            fun creatingViewHolder(parent: ViewGroup) : LearnResourceViewModel {
                val binding = DataBindingUtil.inflate<SingleLearnResourceBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.single_learn_resource,
                    parent,
                    false
                )

                return LearnResourceViewModel(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearnResourceViewModel {
      return  LearnResourceViewModel.creatingViewHolder(parent)
    }

    override fun onBindViewHolder(holder: LearnResourceViewModel, position: Int) {
        val resourceRetrieved = getItem(position)
        holder.itemView.setOnClickListener {
            clickListener.onClickListener(resourceRetrieved)
        }
        holder.bind(resourceRetrieved)
    }
}

/**DiffUtil callback to calculate difference in the list in order to update the recyclerView**/
class DiffCallOh : DiffUtil.ItemCallback<LearnEntity>(){
    override fun areItemsTheSame(oldItem: LearnEntity, newItem: LearnEntity): Boolean {
        return oldItem.urlToLearningResource == newItem.urlToLearningResource
    }

    override fun areContentsTheSame(oldItem: LearnEntity, newItem: LearnEntity): Boolean {
        return oldItem == oldItem
    }

}

/**an OnClick Listener for each item in the recyclerView**/
class LearningResourceOnClick(val clickListener : (learnResource : LearnEntity) -> Unit ) {
    fun onClickListener(resource : LearnEntity) {
        return clickListener(resource)
    }
}
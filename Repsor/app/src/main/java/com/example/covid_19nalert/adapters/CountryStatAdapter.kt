package com.example.covid_19nalert.adapters

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.covid_19nalert.R
import com.example.covid_19nalert.databinding.GlobalStatHeaderBinding
import com.example.covid_19nalert.databinding.NigeriaStatHeaderBinding
import com.example.covid_19nalert.databinding.SingleCountryStatBinding
import com.example.covid_19nalert.domain.CountryDomainModel
import com.example.covid_19nalert.domain.GlobalDomainSummary
import com.example.covid_19nalert.domain.NigeriaDomainSummary
import com.example.covid_19nalert.domain.RoughCheck
import com.example.covid_19nalert.network_service.OverallStatApen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**Constants that represent the different view type in the data item class**/
private val ITEM_VIEW_TYPE_GLOBAL = 0
private val ITEM_VIEW_TYPE_COUNTRIES = 1
private val ITEM_VIEW_TYPE_NIGERIA = 2




/**RecyclerView adapter to display corona virus statistic for all infected countries**/
class CountryStatAdapter : ListAdapter<DataItem1, RecyclerView.ViewHolder>(DiffCall) {

    /**Get the data item type in order to know which viewHolder type the recyclerView should create **/
    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is DataItem1.AllCountriesItem -> ITEM_VIEW_TYPE_COUNTRIES
            is DataItem1.HeaderItem -> ITEM_VIEW_TYPE_GLOBAL
            is DataItem1.NigeriaList -> ITEM_VIEW_TYPE_NIGERIA
        }
    }
    /**coroutine scope for submiting the restructed list**/
    private val scope = CoroutineScope(Dispatchers.Default)

    /**Function to reconstruct the data sent from the three api endpoints and submit it to the listViewAdapter Api**/
    fun addAndSubmitNewList(inputData : RoughCheck?) {
        scope.launch {
            val items = listOf(DataItem1.HeaderItem(inputData?.overAllStat)) + listOf(DataItem1.NigeriaList(inputData?.nigeriaStat)) + inputData?.countrySummary!!.map { DataItem1.AllCountriesItem(it) }
            //else -> listOf(DataItem.HeaderItem) + list.map { DataItem.UsersItem(it) }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }

    }

    /**ViewHolder for the overall coronavirus world summary**/
    class GlobalStatViewHolder(val binding: GlobalStatHeaderBinding) : RecyclerView.ViewHolder(binding.root){

        /**function to bind the domain data to the data binding variable**/
        fun bind(globalStat : GlobalDomainSummary?){
            binding.global = globalStat
        }

        /**Using dataBinding to inflate the global stat header layout and create a new view holder with the binding object**/
        companion object {
            fun creatingViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder{
                val binding = DataBindingUtil.inflate<GlobalStatHeaderBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.global_stat_header,
                    parent,
                    false
                )

                return GlobalStatViewHolder(binding)
            }
        }
    }

    /**ViewHolder for Nigeria's corona virus statistics**/
    class NigeriaStatViewHolder(val binding : NigeriaStatHeaderBinding) : RecyclerView.ViewHolder(binding.root){
        /**function to bind the domain data to the data binding variable**/
        fun bind(stat : NigeriaDomainSummary?){
            binding.nigeria = stat
        }

       /**Using dataBinding to inflate the nigeria stat header layout and create a new view holder with the binding object**/
        companion object{
            fun creatingViewHolder(parent : ViewGroup) : RecyclerView.ViewHolder{
                val binding = DataBindingUtil.inflate<NigeriaStatHeaderBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.nigeria_stat_header, parent,
                false)

                return NigeriaStatViewHolder(binding)
            }
        }
    }
    /**ViewHolder for the 180 countries coronavirus statistics**/
    class SingleCountryViewHolder(val bindingObj : SingleCountryStatBinding) : RecyclerView.ViewHolder(bindingObj.root){
        /**function to bind the domain data to the data binding variable**/
        fun bind(country : CountryDomainModel?) {
            bindingObj.countryStat = country

        }

   /**Using dataBinding to inflate the global stat header layout and create a new view holder with the binding object**/
        companion object {
            fun CreatingViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
                val binding = DataBindingUtil.inflate<SingleCountryStatBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.single_country_stat,
                    parent,
                    false
                )

                return SingleCountryViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
              ITEM_VIEW_TYPE_COUNTRIES -> SingleCountryViewHolder.CreatingViewHolder(parent)
              ITEM_VIEW_TYPE_GLOBAL -> GlobalStatViewHolder.creatingViewHolder(parent)
              ITEM_VIEW_TYPE_NIGERIA -> NigeriaStatViewHolder.creatingViewHolder(parent)
              else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is GlobalStatViewHolder -> {
                val itemGotten = getItem(position) as DataItem1.HeaderItem
                holder.bind(itemGotten.allStats)
            }
            is NigeriaStatViewHolder ->{
                val itemGotten = getItem(position) as DataItem1.NigeriaList
                holder.bind(itemGotten.niger)
            }
            is SingleCountryViewHolder -> {
                val itemGotten = getItem(position) as DataItem1.AllCountriesItem
                holder.bind(itemGotten.statList)
            }
        }
    }

    /**diffUtil call back to calculate change in the list and update the recyclerView**/
    companion object DiffCall : DiffUtil.ItemCallback<DataItem1>() {
        override fun areItemsTheSame(
            oldItem: DataItem1,
            newItem: DataItem1
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: DataItem1,
            newItem: DataItem1
        ): Boolean {
            return oldItem.id == newItem.id
        }


    }

}

/**class to hold the three distinct data items for display**/
sealed class DataItem1 {
    abstract val id : String?
    data class AllCountriesItem (val statList : CountryDomainModel?) : DataItem1(){
        override val id: String?
            get() = statList?.country
    }

    data class HeaderItem(val allStats : GlobalDomainSummary?) : DataItem1(){
        override val id: String
            get() = ""
    }

    data class NigeriaList(val niger : NigeriaDomainSummary?) : DataItem1(){
        override val id: String
        get() = ""
    }
}


package com.example.covid_19nalert.corona_statistics


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager

import com.example.covid_19nalert.R
import com.example.covid_19nalert.adapters.CountryStatAdapter
import com.example.covid_19nalert.databinding.FragmentCoronaStatisticBinding

/**
 * Fragment for the coronavirus countries statistics
 */
class CoronaStatistic : Fragment() {
    /**Lazily intializing the viewModel**/
    private val viewModel: CoronaStatisticViewModel by lazy {
        ViewModelProviders.of(this).get(CoronaStatisticViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       /**Using dataBinding to inflate the layout of the view**/
       val binding = DataBindingUtil.inflate<FragmentCoronaStatisticBinding>(
            inflater,
            R.layout.fragment_corona_statistic,
            container,
            false
        )

        binding.viewModel = viewModel
        /**Adapter object**/
        val adapter = CountryStatAdapter()

        /**Returned response for the recyclerView adpater list**/
        viewModel.displayStuff.observe(this, Observer {
            it?.let{
                adapter.addAndSubmitNewList(it)
            }
        })
        val manager = GridLayoutManager(context, 1)

        binding.countryStatisticRecyclerView.adapter = adapter
        binding.countryStatisticRecyclerView.layoutManager = manager
        binding.countriesSwipeToRefreshLayout.setOnRefreshListener {
            viewModel.getNewSummary()
            binding.countriesSwipeToRefreshLayout.setColorSchemeResources(R.color.colorPrimary)
            binding.countriesSwipeToRefreshLayout.isRefreshing = false

        }

       binding.setLifecycleOwner(this)
       return binding.root
    }


}

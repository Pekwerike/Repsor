package com.example.covid_19nalert.cases_summary


import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.covid_19nalert.ContentViewActivity

import com.example.covid_19nalert.R
import com.example.covid_19nalert.adapters.ListAdapter1
import com.example.covid_19nalert.adapters.OnClickListener
import com.example.covid_19nalert.databinding.FragmentCaseSummaryBinding


/**News Fragment**/
class CaseSummary : Fragment() {
    /**lazily initializing the viewModel class**/
    private val viewModel: CaseSummaryViewModel by lazy {
        ViewModelProviders.of(this).get(CaseSummaryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /**Inflating the fragment's layout using data binding**/
        val binding = DataBindingUtil.inflate<FragmentCaseSummaryBinding>(
            inflater,
            R.layout.fragment_case_summary,
            container,
            false
        )
        binding.viewModel = viewModel

        /**ListAdapter Object**/
        val adapter = ListAdapter1(OnClickListener { viewModel.displayNewsArticleContent(it) })

        /**Observing the returned news list data live data in order to update the adapter and UI**/
        viewModel.rough2.observe(this, Observer {
            it?.let{
                adapter.submitList(it)
            }
        })

        /**A refresh Listener to refresh the entire layout**/
        binding.refreshLayout.setOnRefreshListener {
            viewModel.getHttpsResponse()
            binding.refreshLayout.setColorSchemeResources(R.color.colorPrimary)
            binding.refreshLayout.isRefreshing = false
            //viewModel.getLatestUpdate()

        }

        /**Sending the clicked news item to the news webView**/
        viewModel.newsArticle.observe(this, Observer {
            it?.let {
                val newIntent = Intent(context, ContentViewActivity::class.java)
                newIntent.putExtra("newsArticle", it)
                startActivity(newIntent)
                viewModel.doneDisplayingNewsArticle()
            }
        })
        binding.recyclerView.adapter = adapter
        binding.setLifecycleOwner(this)
        return binding.root
    }


}

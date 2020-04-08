package com.example.covid_19nalert.learn


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.covid_19nalert.ContentViewActivity
import com.example.covid_19nalert.LearnWebViewActivity

import com.example.covid_19nalert.R
import com.example.covid_19nalert.adapters.LearningResourceAdapter
import com.example.covid_19nalert.adapters.LearningResourceOnClick
import com.example.covid_19nalert.databinding.FragmentLearnMoreBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/**
 * Fragment for the learning resources
 */
class LearnMore : Fragment() {

    private val viewModel: LearnMoreViewModel by lazy {
        ViewModelProviders.of(this).get(LearnMoreViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentLearnMoreBinding>(
            inflater,
            R.layout.fragment_learn_more,
            container,
            false
        )
        val coroutineScope = CoroutineScope(Dispatchers.Main)
        val application = requireNotNull(this.activity).application
        val dataSource = LearnDatabase.getInstance(application, coroutineScope).learnDao




        val learnAdapter = LearningResourceAdapter(LearningResourceOnClick { viewModel.receivedLearningResource(it)})
        viewModel.allLearningResource.observe(this, Observer {
            it?.let {
                learnAdapter.submitList(it)
            }
        })

        viewModel.moveResourceToWebView.observe(this, Observer {
            it?.let {
                val newIntent = Intent(context, LearnWebViewActivity::class.java)
                newIntent.putExtra("learnResource", it)
                startActivity(newIntent)
                viewModel.doneSendingLearningResource()
            }
        })

        binding.learnResourceRecyclerView.adapter = learnAdapter
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
        return binding.root
    }


}

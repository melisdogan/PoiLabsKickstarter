package com.example.poilabskickstarter.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.poilabskickstarter.R
import com.example.poilabskickstarter.databinding.ActivityListingBinding

class ListingActivity : AppCompatActivity() {
    private lateinit var viewModel: ListingViewModel
    private lateinit var binding: ActivityListingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_listing)
        viewModel = ViewModelProvider(this).get(ListingViewModel::class.java)
        binding.listingRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.listingRecyclerview.setHasFixedSize(true)
        val adapter = ListingAdapter()
        /*viewModel.campaignList.observe(this, Observer {

            binding.listingRecyclerview.adapter = ListingAdapter(it)
        })*/
        viewModel.getCampaigns {
            it.observe(this, Observer { list ->
                adapter.submitList(list)
            })
        }
        binding.listingRecyclerview.adapter = adapter
    }
}
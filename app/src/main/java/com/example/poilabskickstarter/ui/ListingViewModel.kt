package com.example.poilabskickstarter.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.poilabskickstarter.App
import com.example.poilabskickstarter.data.ListingDataSourceFactory
import com.example.poilabskickstarter.data.model.Campaign
import java.util.*


class ListingViewModel : ViewModel() {

    fun getCampaigns(callback: (LiveData<PagedList<Campaign>>) -> Unit) {
        App.campaignRepository.getCampaigns {
            // empty list if error
            it.forEach { campaign ->
                campaign.currency = Currency.getInstance(campaign.currency).symbol
            }
            val sourceFactory = ListingDataSourceFactory(it)
            val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(20)
                .setInitialLoadSizeHint(20)
                .setPrefetchDistance(5)
                .build()
            val listingLiveData = LivePagedListBuilder(sourceFactory, config)
                .build()
            callback(listingLiveData)
        }

    }
}
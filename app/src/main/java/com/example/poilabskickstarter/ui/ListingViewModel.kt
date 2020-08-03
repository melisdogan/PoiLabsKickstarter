package com.example.poilabskickstarter.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.poilabskickstarter.App
import com.example.poilabskickstarter.data.model.Campaign
import java.util.*


class ListingViewModel : ViewModel() {
    lateinit var listingLiveData: LiveData<PagedList<Campaign>>
    fun getCampaigns(callback: (LiveData<PagedList<Campaign>>) -> Unit) {
        App.campaignRepository.getCampaigns {
            // empty list if error
            it.forEach { campaign ->
                campaign.currency = Currency.getInstance(campaign.currency).symbol
            }
            App.campaignRepository.getPagedDataSource(it) {
                listingLiveData = it
            }
            callback(listingLiveData)
        }

    }

    fun searchThroughCampaigns(
        keyword: String = "",
        sort: String = "id",
        start: Int = 0,
        end: Int = -1
    ) {
        App.campaignRepository.searchDataSource(keyword, sort, start, end)
        listingLiveData.value?.dataSource?.invalidate()
    }
}
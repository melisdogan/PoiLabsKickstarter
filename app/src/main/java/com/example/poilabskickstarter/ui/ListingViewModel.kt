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
                campaign.endTime = campaign.endTime.replace("T", " ")
            }
            App.campaignRepository.getPagedDataSource(it) {
                listingLiveData = it
            }
            callback(listingLiveData)
        }

    }

    fun searchThroughCampaigns(
        keyword: String? = null, sort: String? = null, start: Int? = null, end: Int? = null
    ) {
        App.campaignRepository.searchDataSource(keyword, sort, start, end)
        listingLiveData.value?.dataSource?.invalidate()
    }

    fun removeFilter(filter: String) {
        App.campaignRepository.removeFilter(filter)
        listingLiveData.value?.dataSource?.invalidate()
    }
}
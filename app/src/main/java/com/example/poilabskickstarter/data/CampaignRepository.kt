package com.example.poilabskickstarter.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.poilabskickstarter.data.model.Campaign

class CampaignRepository(val dataSource: CampaignDataSource) {
    private lateinit var sourceFactory: ListingDataSourceFactory
    fun getCampaigns(callback: (ArrayList<Campaign>) -> Unit) {
        dataSource.getCampaigns {
            callback(it)
        }
    }

    fun getPagedDataSource(
        list: ArrayList<Campaign>,
        callback: (LiveData<PagedList<Campaign>>) -> Unit
    ) {
        sourceFactory = ListingDataSourceFactory(list)
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

    fun searchDataSource(
        keyword: String? = null,
        sort: String? = null,
        start: Int? = null,
        end: Int? = null
    ) {
        sourceFactory.search(keyword, sort, start, end)
    }

    fun removeFilter(filter: String) {
        sourceFactory.removeFilter(filter)
    }
}
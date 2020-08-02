package com.example.poilabskickstarter.data

import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import com.example.poilabskickstarter.data.model.Campaign

class ListingDataSource(private val listings: List<Campaign>) : PositionalDataSource<Campaign>() {
    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Campaign>) {
        callback.onResult(listings.subList(0, params.requestedLoadSize), 0, listings.size)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Campaign>) {
        val start = params.startPosition
        val end = if ((params.startPosition + params.loadSize) > listings.size) {
            params.startPosition + (listings.size - params.startPosition)
        } else {
            params.startPosition + params.loadSize
        }
        callback.onResult(listings.subList(start, end))
    }
}

class ListingDataSourceFactory(private val listings: List<Campaign>) :
    DataSource.Factory<Int, Campaign>() {
    override fun create(): DataSource<Int, Campaign> {
        return ListingDataSource(listings)
    }
}
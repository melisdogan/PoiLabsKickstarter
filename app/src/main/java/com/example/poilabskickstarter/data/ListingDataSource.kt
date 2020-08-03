package com.example.poilabskickstarter.data

import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import com.example.poilabskickstarter.data.model.Campaign

class ListingDataSource(private val listings: List<Campaign>) : PositionalDataSource<Campaign>() {

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Campaign>) {
        if (listings.size > params.requestedLoadSize) {
            callback.onResult(listings.subList(0, params.requestedLoadSize), 0, listings.size)
        } else {
            callback.onResult(listings.subList(0, listings.size), 0, listings.size)
        }
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
    private var keyword: String? = null
    private var filterStart: Int? = null
    private var filterEnd: Int? = null
    private var sort: String? = null
    override fun create(): DataSource<Int, Campaign> {
        val result = listings.toMutableList()
        if (keyword != null) {
            result.removeIf {
                !it.title.toLowerCase().contains(keyword!!)
            }
        }

        if (filterStart != null) {
            result.removeIf {
                !it.numberOfBackers.matches("\\d+(\\.\\d+)?".toRegex()) || it.numberOfBackers.toInt() < filterStart!!
            }
        }
        if (filterEnd != null) {
            result.removeIf {
                !it.numberOfBackers.matches("\\d+(\\.\\d+)?".toRegex()) || it.numberOfBackers.toInt() > filterEnd!!
            }
        }

        when (sort) {
            "id" -> result.sortWith(Comparator { o1, o2 -> o1!!.id.compareTo(o2!!.id) })
            "alphabetical" -> result.sortWith(Comparator { o1, o2 -> o1!!.title.compareTo(o2!!.title) })
            "percentage" -> result.sortWith(Comparator { o1, o2 ->
                o1!!.percentageFunded.compareTo(
                    o2!!.percentageFunded
                )
            })
            null -> result.sortWith(Comparator { o1, o2 -> o1!!.id.compareTo(o2!!.id) })
        }
        return ListingDataSource(result)
    }

    fun search(
        keyword: String? = null,
        sort: String? = null,
        start: Int? = null,
        end: Int? = null
    ) {
        if (keyword != null || this.keyword == null) {
            this.keyword = keyword
        }
        if (sort != null || this.sort == null) {
            this.sort = sort
        }
        if (start != null || filterStart == null) {
            filterStart = start
        }
        if (end != null || filterEnd == null) {
            filterEnd = end
        }
    }

    fun removeFilter(filter: String) {
        when (filter) {
            "filter" -> {
                filterEnd = null
                filterStart = null
            }
            "search" -> {
                keyword = null
            }
        }
    }
}
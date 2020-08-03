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
    private var keyword = ""
    private var filterStart = 0
    private var filterEnd = -1
    private var sort = "id"
    override fun create(): DataSource<Int, Campaign> {
        var result = listings.toMutableList()
        /*var result = if (keyword != "") {
            listings.filter {
                it.title.toLowerCase().contains(keyword)
            }
        } else {
            listings
        }
        try {
            var result = if (filterEnd != -1) {
                result.filter {
                    it.numberOfBackers.toInt() in (filterStart) until filterEnd
                }
            } else {
                result
            }
        } catch (e: NumberFormatException) {
            Log.d("ListingDataSource", "Not a numeric value")
        }
        when (sort) {
            "id" -> Collections.sort(result) { o1, o2 -> o1!!.id.compareTo(o2!!.id) }
            "alphabetical" -> Collections.sort(result) { o1, o2 -> o1!!.title.compareTo(o2!!.title) }
            "percentage" -> Collections.sort(result) { o1, o2 -> o1!!.percentageFunded.compareTo(o2!!.percentageFunded) }
        }*/
        if (keyword != "") {
            result.removeIf {
                !it.title.toLowerCase().contains(keyword)
            }
        }

        if (filterEnd != -1) {
            result.removeIf {
                !it.numberOfBackers.matches("\\d+(\\.\\d+)?".toRegex()) || it.numberOfBackers.toInt() > filterEnd || it.numberOfBackers.toInt() < filterStart
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
        }
        return ListingDataSource(result)
    }

    fun search(keyword: String = "", sort: String = "id", start: Int = 0, end: Int = -1) {
        this.keyword = keyword
        this.sort = sort
        filterStart = start
        filterEnd = end
    }
}
package com.example.poilabskickstarter.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.poilabskickstarter.App
import com.example.poilabskickstarter.data.model.Campaign

class ListingViewModel : ViewModel() {

    private val _campaignList = MutableLiveData<ArrayList<Campaign>>()
    val campaignList: LiveData<ArrayList<Campaign>> = _campaignList

    fun getCampaigns() {
        App.campaignRepository.getCampaigns {
            _campaignList.value = it
        }
    }
}
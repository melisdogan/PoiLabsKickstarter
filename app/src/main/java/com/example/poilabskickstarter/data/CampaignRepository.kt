package com.example.poilabskickstarter.data

import com.example.poilabskickstarter.data.model.Campaign

class CampaignRepository(val dataSource: CampaignDataSource) {
    fun getCampaigns(callback: (ArrayList<Campaign>) -> Unit) {
        dataSource.getCampaigns {
            callback(it)
        }
    }
}
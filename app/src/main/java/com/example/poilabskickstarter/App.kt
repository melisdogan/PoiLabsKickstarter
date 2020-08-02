package com.example.poilabskickstarter

import android.app.Application
import android.content.Context
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.gsonparserfactory.GsonParserFactory
import com.example.poilabskickstarter.data.CampaignDataSource
import com.example.poilabskickstarter.data.CampaignRepository

class App : Application() {
    init {
        instance = this
    }
    companion object {
        private var instance: App? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }

        val campaignRepository = CampaignRepository(CampaignDataSource())
        /*val shopRepository = ShopRepository(ShopDataSource())
val warehouseRepository = WarehouseRepository(WarehouseDataSource())*/
    }

    override fun onCreate() {
        super.onCreate()
        AndroidNetworking.initialize(applicationContext)
        AndroidNetworking.setParserFactory(GsonParserFactory())
    }
}
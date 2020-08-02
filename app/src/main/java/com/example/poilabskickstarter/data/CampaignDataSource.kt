package com.example.poilabskickstarter.data

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.example.poilabskickstarter.data.model.Campaign
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray

class CampaignDataSource {
    fun getCampaigns(callback: (ArrayList<Campaign>) -> Unit) {
        AndroidNetworking.get("http://starlord.hackerearth.com/kickstarter")
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray?) {
                    val type =
                        object : TypeToken<ArrayList<Campaign?>?>() {}.type
                    callback(Gson().fromJson(response.toString(), type))
                }

                override fun onError(anError: ANError?) {
                    callback(arrayListOf())
                }
            })
    }
}
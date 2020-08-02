package com.example.poilabskickstarter.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Campaign(
    @SerializedName(value = "s.no")
    val id: Int,
    @SerializedName(value = "amt.pledged")
    val amountPledged: Long,
    val blurb: String,
    @SerializedName(value = "by")
    val author: String,
    val country: String,
    var currency: String,
    @SerializedName(value = "end.time")
    val endTime: String,
    val location: String,
    @SerializedName(value = "percentage.funded")
    val percentageFunded: Int,
    @SerializedName(value = "num.backers")
    val numberOfBackers: String,
    val state: String,
    val title: String,
    val type: String,
    val url: String
) : Parcelable
package com.example.poilabskickstarter.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.poilabskickstarter.data.model.Campaign
import com.example.poilabskickstarter.databinding.ListingItemBinding

class ListingAdapter(private val campaignList: ArrayList<Campaign>) :
    RecyclerView.Adapter<ListingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingViewHolder {
        val binding = ListingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return campaignList.size
    }

    override fun onBindViewHolder(holder: ListingViewHolder, position: Int) {
        holder.bind(campaignList[position])
    }
}

class ListingViewHolder(val binding: ListingItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(campaign: Campaign) {
        binding.campaign = campaign
    }
}

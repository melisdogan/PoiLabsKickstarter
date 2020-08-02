package com.example.poilabskickstarter.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.poilabskickstarter.data.model.Campaign
import com.example.poilabskickstarter.databinding.ListingItemBinding

class ListingAdapter(val clickListener: ListingListener) :
    PagedListAdapter<Campaign, ListingViewHolder>(ListingDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingViewHolder {
        val binding = ListingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListingViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, clickListener) }
    }
}

class ListingListener(val clickListener: (Campaign) -> Unit) {
    fun onClick(campaign: Campaign) = clickListener(campaign)
}

class ListingViewHolder(val binding: ListingItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(campaign: Campaign, clickListener: ListingListener) {
        binding.campaign = campaign
        binding.clickListener = clickListener
    }
}

class ListingDiffCallback : DiffUtil.ItemCallback<Campaign>() {
    override fun areItemsTheSame(oldItem: Campaign, newItem: Campaign): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Campaign, newItem: Campaign): Boolean {
        return oldItem == newItem
    }
}
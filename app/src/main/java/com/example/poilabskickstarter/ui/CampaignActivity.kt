package com.example.poilabskickstarter.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.poilabskickstarter.R
import com.example.poilabskickstarter.databinding.ActivityCampaignBinding

class CampaignActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityCampaignBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_campaign)
        binding.campaign = intent.getParcelableExtra("Campaign")
    }
}
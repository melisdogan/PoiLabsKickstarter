package com.example.poilabskickstarter.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.poilabskickstarter.R
import com.example.poilabskickstarter.databinding.ActivityCampaignBinding

class CampaignActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCampaignBinding
    private lateinit var viewModel: CampaignViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_campaign)
        viewModel = ViewModelProvider(this).get(CampaignViewModel::class.java)
        viewModel.campaign = intent.getParcelableExtra("Campaign")
        binding.campaign = viewModel.campaign
        setSupportActionBar(binding.campaignToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.campaignBackButton.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.kickstarter.com" + viewModel.campaign.url)
            )
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
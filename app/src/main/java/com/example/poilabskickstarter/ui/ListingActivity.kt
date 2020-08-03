package com.example.poilabskickstarter.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.poilabskickstarter.R
import com.example.poilabskickstarter.databinding.ActivityListingBinding
import com.example.poilabskickstarter.databinding.FilterDialogBinding
import com.example.poilabskickstarter.databinding.SortDialogBinding

class ListingActivity : AppCompatActivity() {
    private lateinit var viewModel: ListingViewModel
    private lateinit var binding: ActivityListingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_listing)
        viewModel = ViewModelProvider(this).get(ListingViewModel::class.java)
        binding.listingRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.listingRecyclerview.setHasFixedSize(true)
        binding.listingSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchThroughCampaigns(keyword = query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        binding.listingSearch.setOnCloseListener {
            viewModel.removeFilter("search")
            true
        }
        binding.filtersText.setOnClickListener {
            binding.filtersText.visibility = View.GONE
            viewModel.removeFilter("filter")
        }
        binding.sortButton.setOnClickListener {
            dialogBuild(it)
        }
        binding.filterButton.setOnClickListener {
            dialogBuild(it)
        }
        val adapter = ListingAdapter(ListingListener {
            val intent = Intent(this, CampaignActivity::class.java)
            intent.putExtra("Campaign", it)
            startActivity(intent)
        })
        viewModel.getCampaigns {
            it.observe(this, Observer { list ->
                adapter.submitList(list)
            })
        }
        binding.listingRecyclerview.adapter = adapter
    }

    private fun dialogBuild(view: View) {
        val dialog = AlertDialog.Builder(this).create()
        when (view.id) {
            R.id.filter_button -> {
                val dialogBinding: FilterDialogBinding =
                    DataBindingUtil.inflate(layoutInflater, R.layout.filter_dialog, null, false)
                dialog.setView(dialogBinding.root)
                dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Filter") { dialog, _ ->
                    viewModel.searchThroughCampaigns(
                        start = dialogBinding.filterStart.text.toString().toInt(),
                        end = dialogBinding.filterEnd.text.toString().toInt()
                    )
                    filterEnabled(
                        dialogBinding.filterStart.text.toString().toInt(),
                        dialogBinding.filterEnd.text.toString().toInt()
                    )
                    dialog.dismiss()
                }
                dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
            }
            R.id.sort_button -> {
                val dialogBinding: SortDialogBinding =
                    DataBindingUtil.inflate(layoutInflater, R.layout.sort_dialog, null, false)
                dialog.setView(dialogBinding.root)
                dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Sort") { dialog, _ ->
                    viewModel.searchThroughCampaigns(
                        sort = when (dialogBinding.sortRadiogroup.checkedRadioButtonId) {
                            R.id.sort_alphabetically -> "alphabetical"
                            R.id.sort_percentage -> "percentage"
                            R.id.sort_id -> "id"
                            else -> "id"
                        }
                    )
                    dialog.dismiss()
                }
                dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
            }
        }
        dialog.show()
    }

    private fun filterEnabled(filterStart: Int, filterEnd: Int) {
        binding.filtersText.visibility = View.VISIBLE
        binding.filterStart = filterStart
        binding.filterEnd = filterEnd
    }
}
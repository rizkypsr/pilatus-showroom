package com.showroom.pilatus.ui.search

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.showroom.pilatus.R
import com.showroom.pilatus.adapter.ProductListAdapter
import com.showroom.pilatus.databinding.ActivitySearchBinding
import com.showroom.pilatus.model.response.home.Data
import com.showroom.pilatus.ui.detail.DetailActivity

class SearchActivity : AppCompatActivity(), SearchContract.View {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var presenter: SearchPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.shimmer.hideShimmerAdapter()

        presenter = SearchPresenter(this)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                binding.searchView.clearFocus()
                presenter.getSearchByName(p0.toString())
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })

        binding.searchView.requestFocus()

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onSearchByNameSuccess(product: List<Data>) {
        binding.recyclerViewSearch.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        val newProductsListAdapter = ProductListAdapter(product)
        binding.recyclerViewSearch.adapter = newProductsListAdapter

        newProductsListAdapter.setOnItemClickCallback(object :
            ProductListAdapter.OnItemClickCallback {
            override fun onItemClicked(product: Data) {
                val toDetail = Intent(this@SearchActivity, DetailActivity::class.java)
                startActivity(toDetail)
            }
        })
    }

    override fun onSearchByNameFailed(message: String) {
        Toast.makeText(this, "Ada Kesalahan", Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        binding.shimmer.showShimmerAdapter()
    }

    override fun dismissLoading() {
        binding.shimmer.hideShimmerAdapter()
    }
}
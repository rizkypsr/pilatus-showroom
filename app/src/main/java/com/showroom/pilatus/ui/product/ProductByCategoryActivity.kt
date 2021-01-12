package com.showroom.pilatus.ui.product

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.showroom.pilatus.R
import com.showroom.pilatus.adapter.ProductListAdapter
import com.showroom.pilatus.databinding.ActivityCategoryResultBinding
import com.showroom.pilatus.model.response.home.CategoryResponse
import com.showroom.pilatus.model.response.home.Data
import com.showroom.pilatus.ui.detail.DetailActivity
import com.showroom.pilatus.ui.search.SearchContract
import com.showroom.pilatus.ui.search.SearchPresenter

class ProductByCategoryActivity : AppCompatActivity(), ProductByCategoryContract.View {

    private lateinit var binding: ActivityCategoryResultBinding

    private lateinit var category: CategoryResponse
    private lateinit var presenter: ProductByCategoryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = ProductByCategoryPresenter(this)

        category = intent.getParcelableExtra<CategoryResponse>("category") as CategoryResponse

        binding.topAppBar.title = category.name

        presenter.getProductByCategory(category.id)

        binding.recyclerViewCategoryResult.setHasFixedSize(true)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    override fun onProductByCategorySuccess(it1: List<Data>) {
        binding.recyclerViewCategoryResult.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        val newProductsListAdapter = ProductListAdapter(it1)
        binding.recyclerViewCategoryResult.adapter = newProductsListAdapter

        newProductsListAdapter.setOnItemClickCallback(object :
            ProductListAdapter.OnItemClickCallback {
            override fun onItemClicked(product: Data) {
                val toDetail = Intent(this@ProductByCategoryActivity, DetailActivity::class.java)
                startActivity(toDetail)
            }
        })
    }

    override fun onProductByCategoryFailed(message: String) {
        //
    }

    override fun showLoading() {
        binding.shimmer.showShimmerAdapter()
    }

    override fun dismissLoading() {
        binding.shimmer.hideShimmerAdapter()
    }
}
package com.showroom.pilatus.ui.search

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.showroom.pilatus.R
import com.showroom.pilatus.adapter.NewProductsListAdapter
import com.showroom.pilatus.databinding.ActivityCategoryResultBinding
import com.showroom.pilatus.model.response.home.CategoryResponse
import com.showroom.pilatus.model.response.home.Data
import com.showroom.pilatus.network.APIConfig
import com.showroom.pilatus.network.ApiService
import com.showroom.pilatus.ui.category.CategoryPresenter
import com.showroom.pilatus.ui.detail.DetailActivity
import com.showroom.pilatus.ui.home.HomePresenter

class ProductByCategoryActivity : AppCompatActivity(), SearchContract.View {

    private lateinit var binding: ActivityCategoryResultBinding

    private lateinit var category: CategoryResponse

    private lateinit var presenter: SearchPresenter

    var progressDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        presenter = SearchPresenter(this)

        category = intent.getParcelableExtra<CategoryResponse>("category") as CategoryResponse

        binding.topAppBar.title = category.name

        presenter.getProductByCategory(category.id)

        binding.recyclerViewCategoryResult.setHasFixedSize(true)

    }

    private fun initView() {
        progressDialog = Dialog(this)
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onProductByCategorySuccess(it1: List<Data>) {
        binding.recyclerViewCategoryResult.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        val newProductsListAdapter = NewProductsListAdapter(it1)
        binding.recyclerViewCategoryResult.adapter = newProductsListAdapter

        newProductsListAdapter.setOnItemClickCallback(object :
            NewProductsListAdapter.OnItemClickCallback {
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
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}
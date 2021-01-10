package com.showroom.pilatus.ui.category

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.showroom.pilatus.adapter.NewProductsListAdapter
import com.showroom.pilatus.databinding.ActivityCategoryResultBinding
import com.showroom.pilatus.model.response.home.CategoryResponse
import com.showroom.pilatus.model.response.home.Data
import com.showroom.pilatus.network.APIConfig
import com.showroom.pilatus.network.ApiService
import com.showroom.pilatus.ui.detail.DetailActivity

class CategoryResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryResultBinding
    private lateinit var apiService: ApiService

    private lateinit var category: CategoryResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        category = intent.getParcelableExtra<CategoryResponse>("category") as CategoryResponse

        binding.topAppBar.title = category.name

        apiService = APIConfig.getRetrofitClient(this).create(ApiService::class.java)

        binding.recyclerViewCategoryResult.setHasFixedSize(true)

        //getListProducts()
    }

//    private fun getListProducts() {
//
//        var listProduct = ArrayList<ProductResponse>()
//
//        apiService.getProductsByCategory(category.id).enqueue(object : Callback<Data> {
//            override fun onResponse(
//                call: Call<Data>,
//                response: Response<Data>
//            ) {
//                val product = response.body()
//
//                if (product != null) {
//                    listProduct =
//                        product.data as ArrayList<ProductResponse>
//                }
//
//                showListProduct(listProduct)
//            }
//
//            override fun onFailure(call: Call<Data>, t: Throwable) {
//                Log.d("JUNET", "onFailure: ${t.message}")
//            }
//        })
//    }

    private fun showListProduct(listNewProducts: ArrayList<Data>) {
        binding.recyclerViewCategoryResult.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        val newProductsListAdapter = NewProductsListAdapter(listNewProducts)
        binding.recyclerViewCategoryResult.adapter = newProductsListAdapter

        newProductsListAdapter.setOnItemClickCallback(object :
            NewProductsListAdapter.OnItemClickCallback {
            override fun onItemClicked(product: Data) {
                val toDetail = Intent(this@CategoryResultActivity, DetailActivity::class.java)
                startActivity(toDetail)
            }

        })
    }
}
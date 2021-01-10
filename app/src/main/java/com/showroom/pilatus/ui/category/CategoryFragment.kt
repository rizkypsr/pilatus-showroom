package com.showroom.pilatus.ui.category

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.showroom.pilatus.adapter.AllCategoryListAdapter
import com.showroom.pilatus.databinding.FragmentCategoryBinding
import com.showroom.pilatus.model.response.home.CategoryResponse
import com.showroom.pilatus.network.APIConfig
import com.showroom.pilatus.network.ApiService

class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var apiService: ApiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apiService = APIConfig.getRetrofitClient(requireActivity()).create(ApiService::class.java)

        //getListCategory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private fun getListCategory() {
//        var listCategory = ArrayList<DataCategory>()
//
//        apiService.getCategories().enqueue(object : Callback<CategoryResponse> {
//            override fun onResponse(
//                call: Call<CategoryResponse>,
//                response: Response<CategoryResponse>
//            ) {
//                val category = response.body()
//
//                if (category != null) {
//                    listCategory = category.data as ArrayList<DataCategory>
//                }
//
//                showListCategory(listCategory)
//
//                binding.progressBar.visibility = View.INVISIBLE
//            }
//
//            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
//                binding.progressBar.visibility = View.INVISIBLE
//            }
//
//        })
//    }

    private fun showListCategory(listCategory: ArrayList<CategoryResponse>) {
        binding.recyclerViewCategory.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val categoryListAdapter = AllCategoryListAdapter(listCategory)
        binding.recyclerViewCategory.adapter = categoryListAdapter

        categoryListAdapter.setOnItemClickCallback(object :
            AllCategoryListAdapter.OnItemClickCallback {
            override fun onItemClicked(
                category: CategoryResponse,
                categoryId: Int,
                categoryName: String
            ) {
                val toCategoryResultActivity = Intent(activity, CategoryResultActivity::class.java)
                toCategoryResultActivity.putExtra("category", category)
                startActivity(toCategoryResultActivity)
            }

        })
    }

}
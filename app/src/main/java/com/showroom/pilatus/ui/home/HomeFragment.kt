package com.showroom.pilatus.ui.home

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.showroom.pilatus.R
import com.showroom.pilatus.adapter.CategoryListAdapter
import com.showroom.pilatus.adapter.NewProductsListAdapter
import com.showroom.pilatus.databinding.FragmentHomeBinding
import com.showroom.pilatus.model.response.home.CategoryResponse
import com.showroom.pilatus.model.response.home.Data
import com.showroom.pilatus.ui.search.ProductByCategoryActivity
import com.showroom.pilatus.ui.detail.DetailActivity
import com.showroom.pilatus.ui.search.SearchActivity

class HomeFragment : Fragment(), HomeContract.View {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var listNewProduct: ArrayList<Data> = ArrayList()
    private lateinit var presenter: HomePresenter

    var progressDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        presenter = HomePresenter(this)
        presenter.getProduct()
        presenter.getCategory()

        binding.recyclerViewCategory.setHasFixedSize(true)

        binding.searchBar.setOnClickListener {
            val moveIntent = Intent(activity, SearchActivity::class.java)
            startActivity(moveIntent)
        }
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_cart -> {
                    // Handle favorite icon press
                    findNavController().navigate(R.id.action_navigationHome_to_cartFragment)
                    true
                }
                else -> false
            }
        }

        binding.labelViewAllCategory.setOnClickListener {
            findNavController().navigate(R.id.action_navigationHome_to_navigationCategory)
        }
    }

    private fun initView() {
        progressDialog = Dialog(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onProductSuccess(it1: List<Data>) {
        binding.recyclerViewNewProducts.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        val newProductsListAdapter = NewProductsListAdapter(it1)
        binding.recyclerViewNewProducts.adapter = newProductsListAdapter

        newProductsListAdapter.setOnItemClickCallback(object :
            NewProductsListAdapter.OnItemClickCallback {
            override fun onItemClicked(product: Data) {
                val toDetail = Intent(activity, DetailActivity::class.java)
                toDetail.putExtra("product", product)
                startActivity(toDetail)
            }
        })
    }

    override fun onProductFailed(message: String) {
        Toast.makeText(requireContext(), "Product gagal. $message", Toast.LENGTH_SHORT).show()
    }

    override fun onCategorySuccess(categoryResponse: List<CategoryResponse>) {
        binding.recyclerViewCategory.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val categoryListAdapter = CategoryListAdapter(categoryResponse)
        binding.recyclerViewCategory.adapter = categoryListAdapter

        categoryListAdapter.setOnItemClickCallback(object :
            CategoryListAdapter.OnItemClickCallback {
            override fun onItemClicked(category: CategoryResponse) {
                val toCategoryResultActivity =
                    Intent(activity, ProductByCategoryActivity::class.java)
                toCategoryResultActivity.putExtra("category", category)
                startActivity(toCategoryResultActivity)
            }

        })
    }

    override fun onCategoryFailed(message: String) {
        Toast.makeText(requireContext(), "Category gagal. $message", Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}
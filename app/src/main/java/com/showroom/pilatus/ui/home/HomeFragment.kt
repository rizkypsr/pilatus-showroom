package com.showroom.pilatus.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.showroom.pilatus.R
import com.showroom.pilatus.adapter.ProductListAdapter
import com.showroom.pilatus.databinding.FragmentHomeBinding
import com.showroom.pilatus.model.response.home.Data
import com.showroom.pilatus.ui.detail.DetailActivity
import com.showroom.pilatus.ui.search.SearchActivity

class HomeFragment : Fragment(), HomeContract.View {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: HomePresenter
//    private var progressDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = HomePresenter(this)
        presenter.getProduct()

        binding.searchBar.setOnClickListener {
            val moveIntent = Intent(activity, SearchActivity::class.java)
            startActivity(moveIntent)
        }
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_cart -> {
                    findNavController().navigate(R.id.action_navigationHome_to_cartFragment)
                    true
                }
                else -> false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onProductSuccess(it1: List<Data>) {
        Log.d("JUNET", "onProductSuccess: $it1")
        binding.recyclerViewNewProducts.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        val newProductsListAdapter = ProductListAdapter(it1)
        binding.recyclerViewNewProducts.adapter = newProductsListAdapter

        binding.shimmer.layoutManager = GridLayoutManager(
            activity,
            2,
            GridLayoutManager.VERTICAL,
            false
        )

        binding.shimmer.adapter

        newProductsListAdapter.setOnItemClickCallback(object :
            ProductListAdapter.OnItemClickCallback {
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

    override fun showLoading() {
        binding.shimmer.showShimmerAdapter()
    }

    override fun dismissLoading() {
        binding.shimmer.hideShimmerAdapter()
    }
}
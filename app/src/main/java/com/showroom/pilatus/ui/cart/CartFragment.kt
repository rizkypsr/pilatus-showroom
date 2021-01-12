package com.showroom.pilatus.ui.cart

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.showroom.pilatus.adapter.CartListAdapter
import com.showroom.pilatus.databinding.FragmentCartBinding
import com.showroom.pilatus.model.response.cart.CartItem
import com.showroom.pilatus.model.response.cart.ShoppingCart
import com.showroom.pilatus.ui.detail.DetailActivity
import io.paperdb.Paper

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Paper.init(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewCart.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val cartListAdapter = CartListAdapter(ShoppingCart.getCart())
        binding.recyclerViewCart.adapter = cartListAdapter

        cartListAdapter.setOnItemClickCallback(object :
            CartListAdapter.OnItemClickCallback {
            override fun onItemClicked(cartItem: CartItem) {
                val toDetail = Intent(activity, DetailActivity::class.java)
                toDetail.putExtra("product", cartItem.product)
                startActivity(toDetail)
            }

        })

        binding.topAppBar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
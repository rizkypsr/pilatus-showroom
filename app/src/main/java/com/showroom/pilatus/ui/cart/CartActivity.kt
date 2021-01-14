package com.showroom.pilatus.ui.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.showroom.pilatus.R
import com.showroom.pilatus.adapter.CartListAdapter
import com.showroom.pilatus.databinding.ActivityCartBinding
import com.showroom.pilatus.model.response.cart.CartItem
import com.showroom.pilatus.model.response.cart.ShoppingCart
import com.showroom.pilatus.ui.detail.DetailActivity
import com.showroom.pilatus.ui.ongkir.cost.OngkirActivity
import com.showroom.pilatus.utils.Helpers
import io.paperdb.Paper

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding

    private lateinit var cartListAdapter: CartListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Paper.init(this)

        var totalPrice = 0

        ShoppingCart.getCart().forEach {
            totalPrice += (it.product.price * it.quantity)
        }

        binding.tvTotalPrice.text = Helpers.getCurrencyIDR(totalPrice.toDouble())

        binding.btnOrder.setOnClickListener {
            val toOngkirActivity = Intent(this, OngkirActivity::class.java)
            startActivity(toOngkirActivity)
        }

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.recyclerViewCart.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        cartListAdapter = CartListAdapter(ShoppingCart.getCart())
        binding.recyclerViewCart.adapter = cartListAdapter

        cartListAdapter.setOnItemClickCallback(object :
            CartListAdapter.OnItemClickCallback {
            override fun onItemClicked(cartItem: CartItem) {
                val toDetail = Intent(this@CartActivity, DetailActivity::class.java)
                toDetail.putExtra("product", cartItem.product)
                startActivity(toDetail)
            }

            override fun onDeleteClicked(cartItem: CartItem) {
                ShoppingCart.removeItem(cartItem)
                Log.d("papaer", "onDeleteClicked: ${ShoppingCart.getCart()}")
                recreate()
            }
        })
    }
}
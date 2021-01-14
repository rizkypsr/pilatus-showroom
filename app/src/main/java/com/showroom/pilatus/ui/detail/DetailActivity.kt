package com.showroom.pilatus.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.showroom.pilatus.databinding.ActivityDetailBinding
import com.showroom.pilatus.model.response.cart.CartItem
import com.showroom.pilatus.model.response.cart.ShoppingCart
import com.showroom.pilatus.model.response.home.Data
import com.showroom.pilatus.ui.ongkir.cost.OngkirActivity
import com.showroom.pilatus.utils.Helpers

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private lateinit var data: Data

    var bundle: Bundle? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        data = intent.getParcelableExtra("product")!!

        Glide.with(this)
            .load(data.picturePath)
            .into(binding.ivProductPhoto)

        binding.apply {
            tvProductTitle.text = data.name
            tvProductDesc.text = data.description
            tvProductStock.text = data.stock.toString() + " left"
            tvProductPrice.text = Helpers.getCurrencyIDR(data.price.toDouble())

            if (data.stock == 0) {
                btnOrderNow.text = "Sold Out"
                btnOrderNow.isEnabled = false
            }
        }

        binding.btnOrderNow.setOnClickListener {
            val toOngkirActivity = Intent(this, OngkirActivity::class.java)
            toOngkirActivity.putExtra("product", data)
            startActivity(toOngkirActivity)
        }

        binding.btnAddToCart.setOnClickListener {
            val item = CartItem(data)

            ShoppingCart.addItem(item)

            // notify user
            Toast.makeText(this, "${data.name} added to your cart", Toast.LENGTH_LONG).show()
        }
    }
}
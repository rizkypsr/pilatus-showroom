package com.showroom.pilatus.ui.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.showroom.pilatus.databinding.ActivityDetailBinding
import com.showroom.pilatus.model.response.cart.CartItem
import com.showroom.pilatus.model.response.cart.ShoppingCart
import com.showroom.pilatus.model.response.home.Data
import com.showroom.pilatus.ui.payment.PaymentActivity
import com.showroom.pilatus.utils.Helpers

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private lateinit var data: Data

    var bundle: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        data = intent.getParcelableExtra("product")!!


//            Glide.with(this)
//                .load(data.picturePath)
//                .into(binding.ivProductPhoto)

        binding.apply {
            tvProductTitle.text = data.name
            tvProductDesc.text = data.description
            tvProductStock.text = data.stock.toString()
            tvProductPrice.text = Helpers.getCurrencyIDR(data.price.toDouble())
        }

        binding.btnOrderNow.setOnClickListener {
            val toPaymentActivity = Intent(this, PaymentActivity::class.java)
            toPaymentActivity.putExtra("productDetail", data)
            startActivity(toPaymentActivity)
        }

        binding.btnAddToCart.setOnClickListener {
            val item = CartItem(data)

            ShoppingCart.addItem(item)

            // notify user
            Toast.makeText(this, "${data.name} added to your cart", Toast.LENGTH_LONG).show()
        }
    }
}
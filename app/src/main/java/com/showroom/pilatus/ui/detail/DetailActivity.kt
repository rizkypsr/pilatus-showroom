package com.showroom.pilatus.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.showroom.pilatus.databinding.ActivityDetailBinding
import com.showroom.pilatus.model.response.home.Data
import com.showroom.pilatus.ui.payment.PaymentActivity
import com.showroom.pilatus.utils.Helpers

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    var bundle: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<Data>("product")

        if (data != null) {
//            Glide.with(this)
//                .load(data.picturePath)
//                .into(binding.ivProductPhoto)

            binding.apply {
                tvProductTitle.text = data.name
                tvProductDesc.text = data.description
                tvProductStock.text = data.stock.toString()
                tvProductPrice.text = Helpers.getCurrencyIDR(data.price.toDouble())
            }
        }

        binding.btnOrderNow.setOnClickListener {
            val toPaymentActivity = Intent(this, PaymentActivity::class.java)
            toPaymentActivity.putExtra("productDetail", data)
            toPaymentActivity.putExtra("productQuantity", binding.btnQuantity.number.toInt())
            startActivity(toPaymentActivity)
        }
    }
}
package com.showroom.pilatus.ui.orders

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.showroom.pilatus.PilatusShowroom
import com.showroom.pilatus.R
import com.showroom.pilatus.databinding.ActivityDetailBinding
import com.showroom.pilatus.databinding.ActivityDetailOrderBinding
import com.showroom.pilatus.model.response.home.Data
import com.showroom.pilatus.model.response.login.User
import com.showroom.pilatus.model.response.transaction.TransactionResponseItem
import com.showroom.pilatus.utils.Helpers

class DetailOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailOrderBinding

    private lateinit var data: TransactionResponseItem

    var bundle: Bundle? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        data = intent.getParcelableExtra("order")!!

        binding.apply {

            if (!(data.status == "ON_DELIVERY" || data.status == "PENDING")) {
                btnCancel.visibility = View.GONE
            }

            Glide.with(this@DetailOrderActivity)
                .load(data.product.picturePath)
                .into(ivProductPhoto)

            tvProductTitle.text = data.product.name
            tvProductPrice.text = Helpers.getCurrencyIDR(data.product.price.toDouble())
            tvProductPricePreview.text = Helpers.getCurrencyIDR(data.product.price.toDouble())
            tvTotalPrice.text = Helpers.getCurrencyIDR(data.total.toDouble())
            tvName.text = data.user.name
            tvPhone.text = data.user.phoneNumber
            tvAddress.text = data.user.address
            tvCity.text = data.user.city
            tvOrderId.text = "#SP" + data.id
            tvOrderStatus.text = data.status
        }
    }
}
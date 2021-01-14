package com.showroom.pilatus.ui.payment

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.showroom.pilatus.MainActivity
import com.showroom.pilatus.databinding.ActivityPaymentSuccessBinding
//import com.showroom.pilatus.ui.orders.OrdersFragment

class PaymentSuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnOrderOthers.setOnClickListener {
                val toMainActivity = Intent(this@PaymentSuccessActivity, MainActivity::class.java)
                startActivity(toMainActivity)
            }

            btnViewOrder.setOnClickListener {
                val toMainActivity = Intent(this@PaymentSuccessActivity, MainActivity::class.java)
                startActivity(toMainActivity)
            }
        }
    }
}
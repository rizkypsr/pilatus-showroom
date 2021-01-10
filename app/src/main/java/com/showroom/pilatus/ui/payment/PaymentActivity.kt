package com.showroom.pilatus.ui.payment

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.showroom.pilatus.PilatusShowroom
import com.showroom.pilatus.R
import com.showroom.pilatus.databinding.ActivityPaymentBinding
import com.showroom.pilatus.model.response.checkout.CheckoutResponse
import com.showroom.pilatus.model.response.home.Data
import com.showroom.pilatus.model.response.login.User
import com.showroom.pilatus.utils.Helpers

class PaymentActivity : AppCompatActivity(), PaymentContract.View {

    private lateinit var binding: ActivityPaymentBinding

    private lateinit var presenter: PaymentPresenter

    private var progressDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        presenter = PaymentPresenter(this)

        val data = intent.getParcelableExtra<Data>("productDetail")
        val quantity = intent.getIntExtra("productQuantity", 1)

        val dataUser = Gson().fromJson(PilatusShowroom.getApp().getUser(), User::class.java)

        if (data != null) {

            binding.apply {
                val totalPrice = quantity * data.price

                tvProductTitle.text = data.name
                tvProductPrice.text = Helpers.getCurrencyIDR(data.price.toDouble())
                tvProductPricePreview.text = Helpers.getCurrencyIDR(data.price.toDouble())
                tvTotalPrice.text = Helpers.getCurrencyIDR(totalPrice.toDouble())
                tvName.text = dataUser.name
                tvPhone.text = dataUser.phoneNumber
                tvAddress.text = dataUser.phoneNumber
                tvCity.text = dataUser.city

                btnCheckout.setOnClickListener {
                    presenter.getCheckout(
                        data.id.toString(),
                        dataUser.id.toString(),
                        quantity.toString(),
                        totalPrice.toString(),
                        it
                    )
                }
            }
        }
    }

    private fun initView() {
        progressDialog = Dialog(this)
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onCheckoutSuccess(checkoutResponse: CheckoutResponse, view: View) {
        val toPaymentSuccess = Intent(this, PaymentSuccessActivity::class.java)
        startActivity(toPaymentSuccess)
    }

    override fun onCheckoutFailed(message: String) {
        Toast.makeText(this, "Gagal Checkout. $message", Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}
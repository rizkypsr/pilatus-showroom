package com.showroom.pilatus.ui.payment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.showroom.pilatus.PilatusShowroom
import com.showroom.pilatus.R
import com.showroom.pilatus.databinding.ActivityPaymentBinding
import com.showroom.pilatus.model.response.home.Data
import com.showroom.pilatus.model.response.login.User
import com.showroom.pilatus.model.response.ongkir.city.Result
import com.showroom.pilatus.model.response.ongkir.cost.Cost
import com.showroom.pilatus.utils.Helpers

class PaymentActivity : AppCompatActivity(), PaymentContract.View {

    private lateinit var binding: ActivityPaymentBinding

    private lateinit var presenter: PaymentPresenter

    private var progressDialog: Dialog? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        presenter = PaymentPresenter(this)

        val data = intent.getParcelableExtra<Data>("productDetail")
        val dataCourier = intent.getParcelableExtra<Cost>("courierDetail")
        val dataCity = intent.getParcelableExtra<Result>("cityDetail")
        val quantity = intent.getIntExtra("productQuantity", 1)

        val dataUser = Gson().fromJson(PilatusShowroom.getApp().getUser(), User::class.java)

        if (data != null) {

            binding.apply {
                val totalPrice = (quantity * data.price) + dataCourier!!.cost[0].value

                Glide.with(this@PaymentActivity)
                    .load(data.picturePath)
                    .into(ivProductPhoto)

                tvProductTitle.text = data.name
                tvProductPrice.text = Helpers.getCurrencyIDR(data.price.toDouble())
                tvProductPricePreview.text = Helpers.getCurrencyIDR(data.price.toDouble())
                tvTotalPrice.text = Helpers.getCurrencyIDR(totalPrice.toDouble())
                tvName.text = dataUser.name
                tvPhone.text = dataUser.phoneNumber
                tvAddress.text = dataUser.address + ", ID ${dataCity!!.postalCode}"
                tvCity.text = dataCity.cityName
                tvProvince.text = dataCity.province
                tvCourierType.text = "${dataCourier.service} (${dataCourier.cost[0].etd} hari)"
                tvCourierPrice.text = Helpers.getCurrencyIDR(dataCourier.cost[0].value.toDouble())

                btnCheckout.setOnClickListener {
                    presenter.getCheckout(
                        data.id,
                        dataUser.id,
                        quantity,
                        totalPrice.toLong(),
                        dataCourier.service,
                        dataCourier.cost[0].value.toLong()
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

    override fun onCheckoutSuccess(
        checkoutResponse: com.showroom.pilatus.model.response.checkout.CheckoutData

    ) {
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
package com.showroom.pilatus.ui.payment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.showroom.pilatus.PilatusShowroom
import com.showroom.pilatus.R
import com.showroom.pilatus.databinding.ActivityPaymentBinding
import com.showroom.pilatus.model.response.cart.ShoppingCart
import com.showroom.pilatus.model.response.home.Data
import com.showroom.pilatus.model.response.login.User
import com.showroom.pilatus.model.response.ongkir.city.Result
import com.showroom.pilatus.model.response.ongkir.cost.Cost
import com.showroom.pilatus.utils.Helpers
import io.paperdb.Paper

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

        val dataCart = ShoppingCart.getCart()
        val dataCourier = intent.getParcelableExtra<Cost>("courierDetail")
        val dataCity = intent.getParcelableExtra<Result>("cityDetail")
        val dataUser = Gson().fromJson(PilatusShowroom.getApp().getUser(), User::class.java)
        val dataProduct: MutableList<Data> = mutableListOf()

        ShoppingCart.getCart().forEach {
            dataProduct.add(it.product)
        }

        binding.apply {
            val adapter = ProductPaymentListAdapter(dataCart)
            var totalPrice = 0

            dataCart.forEach {
                totalPrice += (it.quantity * it.product.price)
            }

            recyclerViewProduct.layoutManager = LinearLayoutManager(this@PaymentActivity)
            recyclerViewProduct.adapter = adapter

            tvTotalPrice.text = Helpers.getCurrencyIDR(totalPrice.toDouble())
            tvName.text = dataUser.name
            tvPhone.text = dataUser.phoneNumber
            tvAddress.text = dataUser.address + ", ID ${dataCity!!.postalCode}"
            tvCity.text = dataCity.cityName
            tvProvince.text = dataCity.province
            tvCourierType.text = "${dataCourier!!.service} (${dataCourier.cost[0].etd} hari)"
            tvCourierPrice.text = Helpers.getCurrencyIDR(dataCourier.cost[0].value.toDouble())

            btnCheckout.setOnClickListener {

                dataCart.forEach {
                    presenter.getCheckout(
                        it.product.id,
                        dataUser.id,
                        it.quantity,
                        (it.quantity * it.product.price).toLong(),
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
        Paper.book().delete("cart")
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
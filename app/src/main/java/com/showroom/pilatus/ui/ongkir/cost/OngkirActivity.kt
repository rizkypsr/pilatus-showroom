package com.showroom.pilatus.ui.ongkir.cost

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.showroom.pilatus.databinding.ActivityOngkirBinding
import com.showroom.pilatus.model.response.home.Data
import com.showroom.pilatus.model.response.ongkir.city.Result
import com.showroom.pilatus.model.response.ongkir.cost.Cost
import com.showroom.pilatus.ui.ongkir.search.SearchCityActivity
import com.showroom.pilatus.ui.payment.PaymentActivity


class OngkirActivity : AppCompatActivity(), OngkirContract.View {

    private var destinationId = ""

    private lateinit var binding: ActivityOngkirBinding
    private lateinit var presenter: OngkirPresenter
    private lateinit var adapter: OngkirListAdapter

    private lateinit var dataProduct: Data
    private lateinit var dataCity: Result

    companion object {
        private const val REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOngkirBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = OngkirPresenter(this)

        dataProduct = intent.getParcelableExtra("product")!!

        binding.inputCity.setOnClickListener {
            val intent = Intent(this@OngkirActivity, SearchCityActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }

        binding.btnCourier.setOnClickListener {
            val edtCity = binding.inputCity.text
            val origin = "1"
            val weight = 1700
            val courier = "jne"

            if (edtCity.isNullOrEmpty()) return@setOnClickListener

            presenter.getCourier(origin, destinationId, weight, courier)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == SearchCityActivity.RESULT_CODE) {
            dataCity =
                data!!.getParcelableExtra(SearchCityActivity.EXTRA_SELECTED_CITY)!!

            binding.edtCity.editText!!.setText(dataCity.cityName)
            destinationId = dataCity.cityId

        }
    }

    override fun onCourierSuccess(cost: List<Cost>) {
        adapter = OngkirListAdapter(cost)

        adapter.setOnItemClickCallback(object : OngkirListAdapter.OnItemClickCallback {
            override fun onItemClicked(courier: Cost) {
                val toPaymentActivity = Intent(this@OngkirActivity, PaymentActivity::class.java)
                toPaymentActivity.putExtra("productDetail", dataProduct)
                toPaymentActivity.putExtra("courierDetail", courier)
                toPaymentActivity.putExtra("cityDetail", dataCity)
                startActivity(toPaymentActivity)
            }

        })

        binding.apply {
            rcList.layoutManager = LinearLayoutManager(this@OngkirActivity)
            rcList.adapter = adapter
            rcList.addItemDecoration(
                DividerItemDecoration(
                    rcList.context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    override fun onCourierFailed(message: String) {
        Log.d("JUNET", "onCourierFailed: $message")
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }
}
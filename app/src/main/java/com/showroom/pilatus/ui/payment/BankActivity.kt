package com.showroom.pilatus.ui.payment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.showroom.pilatus.R
import com.showroom.pilatus.databinding.ActivityBankBinding
import com.showroom.pilatus.ui.orders.UploadContract
import com.showroom.pilatus.ui.orders.UploadPresenter

class BankActivity : AppCompatActivity(), UploadContract.View {

    private lateinit var binding: ActivityBankBinding
    private lateinit var presenter: UploadPresenter

    var filePath: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBankBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = UploadPresenter(this)

        val dataId = intent.getStringExtra("payment_id")

        binding.btnGetPhoto.setOnClickListener {
            ImagePicker.with(this).start()
        }

        binding.btnUploadPayment.setOnClickListener {
            if (dataId != null) {
                presenter.uploadPayment(dataId, filePath!!)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            filePath = data?.data

            Glide.with(this)
                .load(filePath)
                .into(binding.ivPhoto)

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onUploadPaymentSuccess(data: List<String>) {
        Toast.makeText(this, "Upload Sukses", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onUploadPaymentFailed(message: String) {
        Toast.makeText(this, "Upload Gagal. $message", Toast.LENGTH_LONG).show()
    }


    override fun showLoading() {

    }

    override fun dismissLoading() {

    }
}
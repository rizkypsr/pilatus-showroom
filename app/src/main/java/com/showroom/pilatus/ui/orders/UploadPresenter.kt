package com.showroom.pilatus.ui.orders

import android.net.Uri
import com.showroom.pilatus.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class UploadPresenter(private val view: UploadContract.View) : UploadContract.Presenter {

    private val mCompositeDisposable: CompositeDisposable?

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun uploadPayment(id : String, filePath: Uri) {
        view.showLoading()

        var paymentImageFile = File(filePath.path)
        val paymentImageRequestBody =
            RequestBody.create(MediaType.parse("multipart/form-data"), paymentImageFile)
        val paymentImageParms = MultipartBody.Part.createFormData(
            "file",
            paymentImageFile.name,
            paymentImageRequestBody
        )

        val disposable = HttpClient.getInstance().getApi()!!.uploadPayment(id, paymentImageParms)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()
                    if (it.meta.status.equals("success", true)) {
                        it.data.let { it1 -> view.onUploadPaymentSuccess(it1!!) }
                    } else {
                        it.meta.message.let { it1 -> view.onUploadPaymentFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onUploadPaymentFailed(it.message.toString())
                }
            )
        mCompositeDisposable!!.add(disposable)
    }

    override fun subscribe() {

    }

    override fun unSubscribe() {
        mCompositeDisposable!!.clear()
    }
}
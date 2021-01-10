package com.showroom.pilatus.ui.register

import android.view.View
import com.showroom.pilatus.model.request.RegisterRequest
import com.showroom.pilatus.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RegisterPresenter(private val view: RegisterContract.View) : RegisterContract.Presenter {

    private val mCompositeDisposable: CompositeDisposable?

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun submitRegister(registerRequest: RegisterRequest, view: View) {
        this.view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.register(
            registerRequest.name,
            registerRequest.email,
            registerRequest.password,
            registerRequest.password_confirmation,
            registerRequest.address,
            registerRequest.city,
            registerRequest.houseNumber,
            registerRequest.phoneNumber
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    this.view.dismissLoading()
                    if (it.meta?.status.equals("success", true)) {
                        it.data?.let { it1 -> this.view.onRegisterSuccess(it1, view) }
                    } else {
                        this.view.onRegisterFailed(it.meta.message.toString())
                    }
                },
                {
                    this.view.dismissLoading()
                    this.view.onRegisterFailed(it.message.toString())
                }
            )
        mCompositeDisposable!!.add(disposable)
    }

//    override fun submitPhoto(filePath: Uri, viewParms: View) {
//        view.showLoading()
//
//        val profileImageFile = File(filePath.path)
//        val profileImageRequestBody =
//            RequestBody.create(MediaType.parse("multipart/form-date"), profileImageFile)
//        val profileImageParms = MultipartBody.Part.createFormData(
//            "file",
//            profileImageFile.name,
//            profileImageRequestBody
//        )
//
//        val disposable = HttpClient.getInstance().getApi()!!.registerPhoto(profileImageParms)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    view.dismissLoading()
//                    if (it.meta?.status.equals("success", true)) {
//                        it.data?.let { it1 -> view.onRegisterPhotoSuccess(viewParms) }
//                    } else {
//                        view.onRegisterFailed(it?.meta?.message.toString())
//                    }
//
//                },
//                {
//                    view.dismissLoading()
//                    view.onRegisterFailed(it.message.toString())
//                }
//            )
//        mCompositeDisposable!!.add(disposable)
//    }

    override fun subscribe() {

    }

    override fun unSubscribe() {
        mCompositeDisposable!!.clear()
    }
}
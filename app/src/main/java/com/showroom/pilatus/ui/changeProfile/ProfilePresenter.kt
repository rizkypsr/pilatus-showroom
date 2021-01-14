package com.showroom.pilatus.ui.changeProfile

import com.showroom.pilatus.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProfilePresenter(private val view: ProfileContract.View) : ProfileContract.Presenter {

    private val mCompositeDisposable: CompositeDisposable?

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun changeProfile(
        name: String,
        email: String,
        address: String,
        city: String,
        houseNumber: String,
        phoneNumber: String
    ) {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.changeProfile(
            name,
            email,
            address,
            city,
            houseNumber,
            phoneNumber
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()
                    if (it.meta.code == 200) {
                        it.data?.let { it1 -> view.onChangeProfileSuccess(it1) }
                    } else {
                        it.meta.message.let { it1 -> view.onChangeProfileFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onChangeProfileFailed(it.message.toString())
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
package com.zkyc.example.feature.main

import com.google.gson.JsonObject
import com.zkyc.arms.base.presenter.BasePresenter
import com.zkyc.arms.base.presenter.IPresenter
import com.zkyc.arms.base.view.IView
import com.zkyc.arms.model.http.request
import com.zkyc.example.model.http.LoginApis
import timber.log.Timber
import javax.inject.Inject

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/5/12 9:26
 * desc   :
 */
class MainPresenter @Inject constructor(private var apis: LoginApis) :
    BasePresenter<MainContract.View>(), MainContract.Presenter {

    override fun startLooper() {
        request<JsonObject> {
            start {
                view?.build("start")
                Timber.d("1、start：${Thread.currentThread().name}")
            }
            call {
                Timber.d("2、call：${Thread.currentThread().name}")
                val map = mapOf(
                    Pair("loginName", "YTlikai"),
                    Pair("loginPassword", "likai0312"),
                    Pair("type", "oem")
                )
                apis.login(map)
            }
            process {
                view?.build("process")
                Timber.d("3、process：${Thread.currentThread().name}")
                it.data
            }
            success {
                view?.build("success")
                Timber.d("4、success：${Thread.currentThread().name}")
                view?.toast("成功:${it.toString()}")
            }
            fail {
                view?.build("fail")
                Timber.d("5、fail：${Thread.currentThread().name}")
                view?.toast("失败:$it")
            }
        }
    }
}

interface MainContract {

    interface View : IView {
        fun build(str: String)
    }

    interface Presenter : IPresenter<View> {
        fun startLooper()
    }
}
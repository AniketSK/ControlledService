package com.aniketkadam.servicecontrol.base

import android.app.Service
import com.aniketkadam.servicecontrol.base.models.ViewState
import com.aniketkadam.servicecontrol.base.mvp.IBasePresenter
import com.aniketkadam.servicecontrol.base.mvp.IBaseView
import io.reactivex.subjects.PublishSubject

abstract class BaseService<T : BasePresenter> : Service(), IBaseView {

    private lateinit var presenter: T
    protected val viewStatePublishSubject = PublishSubject.create<ViewState>()
    abstract fun getPresenter(): T

}
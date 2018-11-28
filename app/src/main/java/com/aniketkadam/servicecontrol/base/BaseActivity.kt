package com.aniketkadam.servicecontrol.base

import android.support.v7.app.AppCompatActivity
import androidx.annotation.CallSuper
import com.aniketkadam.servicecontrol.base.models.ViewState
import com.aniketkadam.servicecontrol.base.mvp.IBasePresenter
import com.aniketkadam.servicecontrol.base.mvp.IBaseView
import io.reactivex.subjects.PublishSubject

abstract class BaseActivity<T : IBasePresenter> : AppCompatActivity(),
    IBaseView {

    private lateinit var presenter: IBasePresenter
    private val viewStatePublishSubject = PublishSubject.create<ViewState>()

    @CallSuper
    override fun onStart() {
        super.onStart()
        presenter = getPresenter()
        presenter.onStart(this)
    }

    @CallSuper
    override fun onStop() {
        super.onStop()
        viewStatePublishSubject.onNext(ViewState.Stopping(isFinishing))
        viewStatePublishSubject.onComplete()
    }

    @Override
    override fun viewState() = viewStatePublishSubject

    abstract fun getPresenter(): IBasePresenter
}
package com.aniketkadam.servicecontrol.base

import com.aniketkadam.servicecontrol.base.models.ViewState
import io.reactivex.subjects.PublishSubject

interface IBaseView {
    fun viewState(): PublishSubject<ViewState>
}
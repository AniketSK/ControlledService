package com.aniketkadam.servicecontrol.demoactivity

import com.aniketkadam.servicecontrol.base.IBaseView
import com.aniketkadam.servicecontrol.base.models.ServiceStates
import com.aniketkadam.servicecontrol.base.models.ViewState
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

interface MainActivityContract {

    interface View : IBaseView {
        fun switchToggle(): Observable<Boolean>
        fun notificationButtonClick(): Observable<Unit>
        fun serviceRun(serviceStates: ServiceStates)
    }
}

package com.aniketkadam.servicecontrol.demoactivity

import com.aniketkadam.servicecontrol.base.mvp.IBaseView
import com.aniketkadam.servicecontrol.base.models.ServiceStates
import io.reactivex.Observable

interface MainActivityContract {

    interface View : IBaseView {
        fun switchToggle(): Observable<Boolean>
        fun serviceRun(serviceStates: ServiceStates)
        fun setSwitchChecked(checked: Boolean)
    }
}

package com.aniketkadam.servicecontrol

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

interface MainActivityContract {
    interface Presenter {
        fun onStart(view: View)
        // Stops are managed from the viewState emissions.
    }

    interface View {
        fun switchToggle(): Observable<Boolean>
        fun notificationButtonClick(): Observable<Unit>
        fun serviceRun(serviceStates: ServiceStates)
        fun viewState(): PublishSubject<ViewState>
    }
}

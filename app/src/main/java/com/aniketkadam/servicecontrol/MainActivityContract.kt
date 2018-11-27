package com.aniketkadam.servicecontrol

import io.reactivex.Observable

interface MainActivityContract {
    interface Presenter {
        fun onStart(view: View)
        fun onStop(isFinishing: Boolean)
    }

    interface View {
        fun switchToggle(): Observable<Boolean>
        fun notificationButtonClick(): Observable<Unit>
        fun serviceRun(boolean: Boolean)
    }
}

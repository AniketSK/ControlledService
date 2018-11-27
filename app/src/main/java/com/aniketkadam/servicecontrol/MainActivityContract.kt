package com.aniketkadam.servicecontrol

import io.reactivex.Observable


interface Presenter {
    fun onStart()
    fun onStop(isFinishing: Boolean)
}

interface View {
    fun switchToggle(): Observable<Boolean>
    fun notificationButtonClick(): Observable<Unit>
}
package com.aniketkadam.servicecontrol.demoactivity.model

interface DataSource {
    fun isServiceActive(): Boolean
    fun setServiceActive(active: Boolean)
}
package com.aniketkadam.servicecontrol.demoactivity.model

import android.content.Context
import android.preference.PreferenceManager

class PreferenceDataSource(val context: Context) : DataSource {
    private val SERVICE_ACTIVE_KEY = "service_active"
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override fun isServiceActive(): Boolean = sharedPreferences.getBoolean(SERVICE_ACTIVE_KEY, false)

    override fun setServiceActive(active: Boolean) =
        sharedPreferences.edit().putBoolean(SERVICE_ACTIVE_KEY, active).apply()

}
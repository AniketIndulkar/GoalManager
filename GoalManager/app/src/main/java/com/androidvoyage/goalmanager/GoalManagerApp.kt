package com.androidvoyage.goalmanager

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.VmPolicy


class GoalManagerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }
}
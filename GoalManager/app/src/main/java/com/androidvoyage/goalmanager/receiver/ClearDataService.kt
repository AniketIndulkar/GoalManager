package com.androidvoyage.goalmanager.receiver

import android.app.IntentService
import android.content.Intent
import android.util.Log


class ClearDataService : IntentService("MyTestService") {
    val TAG = "ClearDataService"
    override fun onHandleIntent(intent: Intent?) {
        // Do the task here
        Log.d(TAG, "onHandleIntent: Service Started")
    }
}

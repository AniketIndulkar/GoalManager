package com.androidvoyage.goalmanager.receiver

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.androidvoyage.goalmanager.R
import com.androidvoyage.goalmanager.activitie.MainActivity
import com.androidvoyage.goalmanager.database.GoalRepository


class NotificationWorker(private val mContext: Context, params: WorkerParameters) : Worker(mContext, params) {
    override fun doWork(): Result {
        Log.d("NotificationWorker", "doWork: Worker working yooo")
        showNotification(mContext = mContext)
        return Result.failure()
    }

    private fun showNotification(mContext: Context) {

        val goalRepository = GoalRepository()
        goalRepository.GoalRepository(mContext)
        val goals = goalRepository.getInCompletedGoal(false)

        val builder = NotificationCompat.Builder(mContext.applicationContext,"GoalManager_001")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Goal Manager")
            .setContentText(goals[0].goal)
        
//        val mBuilder = NotificationCompat.Builder(mContext.applicationContext, "notify_001")
        val ii = Intent(mContext.applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(mContext, 0, ii, 0)
//
//
//
//
//        val bigText = NotificationCompat.BigTextStyle()
//        bigText.bigText(goals[0].goal)
//        bigText.setBigContentTitle("Do you remember Doing this ?")
//
        builder.setContentIntent(pendingIntent)
        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.priority = Notification.PRIORITY_MAX
//        mBuilder.setStyle(bigText)

        val mNotificationManager =
            mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "1234"
            val channel = NotificationChannel(
                channelId,
                "Goal Manager Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            mNotificationManager.createNotificationChannel(channel)
            builder.setChannelId(channelId)
        }

        mNotificationManager.notify(0, builder.build())
    }
}
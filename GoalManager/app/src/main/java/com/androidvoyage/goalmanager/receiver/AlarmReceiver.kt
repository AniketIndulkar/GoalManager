package com.androidvoyage.goalmanager.receiver

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.androidvoyage.goalmanager.R
import com.androidvoyage.goalmanager.activitie.MainActivity
import com.androidvoyage.goalmanager.database.GoalRepository

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context!!, "Alarm Receiver", Toast.LENGTH_LONG).show()
        if (intent!!.getBooleanExtra("DailyClearData", false)) {
            clearDailyData(context)
        } else {
            showNotification(context, intent!!)
        }
    }


    private fun clearDailyData(mContext: Context) {
        val goalRepository = GoalRepository()
        goalRepository.GoalRepository(mContext)
        val goalList = goalRepository.getGoalByTypeList("Daily")
        for (goalData in goalList) {
            goalData.isCompleted = false
            goalRepository.insertTask(goalData)
        }
    }

    private fun showNotification(mContext: Context, intent: Intent) {
        val mBuilder = NotificationCompat.Builder(mContext.applicationContext, "notify_001")
        val ii = Intent(mContext.applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(mContext, 0, ii, 0)

        val goalRepository = GoalRepository()
        goalRepository.GoalRepository(mContext)
        val goals = goalRepository.getInCompletedGoal(false)


        val bigText = NotificationCompat.BigTextStyle()
        bigText.bigText(goals[0].goal)
        bigText.setBigContentTitle("Do you remember Doing this ?")

        mBuilder.setContentIntent(pendingIntent)
        mBuilder.setSmallIcon(R.drawable.ic_close)
        mBuilder.priority = Notification.PRIORITY_MAX
        mBuilder.setStyle(bigText)

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
            mBuilder.setChannelId(channelId)
        }

        mNotificationManager.notify(0, mBuilder.build())
    }
}
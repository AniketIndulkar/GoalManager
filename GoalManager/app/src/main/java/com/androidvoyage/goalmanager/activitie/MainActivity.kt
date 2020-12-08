package com.androidvoyage.goalmanager.activitie

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.androidvoyage.goalmanager.R
import com.androidvoyage.goalmanager.adapters.GoalListAdapter
import com.androidvoyage.goalmanager.datamodels.GoalData
import com.androidvoyage.goalmanager.receiver.AlarmReceiver
import com.androidvoyage.goalmanager.receiver.NotificationWorker
import com.androidvoyage.goalmanager.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), GoalListAdapter.ClickEvents {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(MainViewModel::class.java)

        val recurringWork: PeriodicWorkRequest = PeriodicWorkRequest.Builder(
            NotificationWorker::class.java,
            15,
            TimeUnit.MINUTES
        ).build()
        WorkManager.getInstance(this).enqueue(recurringWork)
        scheduleAlarm()
    }

    fun scheduleAlarm() {
        val intent = Intent(applicationContext, AlarmReceiver::class.java)
        val pIntent = PendingIntent.getBroadcast(
            this, AlarmReceiver.REQUEST_CODE,
            intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val firstMillis = System.currentTimeMillis()
        val alarm = this.getSystemService(ALARM_SERVICE) as AlarmManager
        alarm.setInexactRepeating(
            AlarmManager.RTC_WAKEUP, firstMillis,
            AlarmManager.INTERVAL_HALF_HOUR, pIntent
        )
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.getGoalsByType(this, getString(R.string.text_long)).observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                rvLongTermGoals.layoutManager = LinearLayoutManager(this)
                rvLongTermGoals.adapter =
                    GoalListAdapter(it, this, this as GoalListAdapter.ClickEvents)
                rvLongTermGoals.visibility = View.VISIBLE
                tvNoLongGoals.visibility = View.GONE
            } else {
                rvLongTermGoals.visibility = View.GONE
                tvNoLongGoals.visibility = View.VISIBLE
            }

        })

        mainViewModel.getGoalsByType(this, getString(R.string.text_short)).observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                rvShortTermGoals.layoutManager = LinearLayoutManager(this)
                rvShortTermGoals.adapter =
                    GoalListAdapter(it, this, this as GoalListAdapter.ClickEvents)
                rvShortTermGoals.visibility = View.VISIBLE
                tvNoShortGoals.visibility = View.GONE
            } else {
                rvShortTermGoals.visibility = View.GONE
                tvNoShortGoals.visibility = View.VISIBLE

            }

        })

        mainViewModel.getGoalsByType(this, getString(R.string.text_daily)).observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                rvDailyGoals.layoutManager = LinearLayoutManager(this)
                rvDailyGoals.adapter =
                    GoalListAdapter(it, this, this as GoalListAdapter.ClickEvents)
                rvDailyGoals.visibility = View.VISIBLE
                tvNoDailyGoals.visibility = View.GONE
            } else {
                rvDailyGoals.visibility = View.GONE
                tvNoDailyGoals.visibility = View.VISIBLE
            }

        })
    }

    fun addGoals(view: View) {
        startActivity(Intent(this, AddGoals::class.java))
    }

    override fun onDoubleClick(goalData: GoalData) {
        mainViewModel.setGoalAsCompleted(this, goalData)
    }

    override fun onDeleteGoal(goalData: GoalData) {
        mainViewModel.deleteGoalData(this, goalData)
        onResume()
    }
}

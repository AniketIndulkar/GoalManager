package com.androidvoyage.goalmanager.activitie

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidvoyage.goalmanager.R
import com.androidvoyage.goalmanager.adapters.GoalListAdapter
import com.androidvoyage.goalmanager.datamodels.GoalData
import com.androidvoyage.goalmanager.receiver.AlarmReceiver
import com.androidvoyage.goalmanager.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), GoalListAdapter.ClickEvents {

    lateinit var mainViewModel: MainViewModel
    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent
    private lateinit var clearDataIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(MainViewModel::class.java)

        alarmMgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(this, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(this, 0, intent, 0)
        }


        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 0)
        }

        alarmMgr?.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_FIFTEEN_MINUTES,
            alarmIntent
        )

        clearDataIntent = Intent(this, AlarmReceiver::class.java).let { intent ->
            intent.putExtra("DailyClearData", true)
            PendingIntent.getBroadcast(this, 0, intent, 0)
        }

        alarmMgr?.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            clearDataIntent
        )

    }

    override fun onResume() {
        super.onResume()
        mainViewModel.getGoalsByType(this, "Long").observe(this, Observer {
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

        mainViewModel.getGoalsByType(this, "Short").observe(this, Observer {
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

        mainViewModel.getGoalsByType(this, "Daily").observe(this, Observer {
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

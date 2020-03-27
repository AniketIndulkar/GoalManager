package com.androidvoyage.goalmanager.activitie

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidvoyage.goalmanager.R
import com.androidvoyage.goalmanager.adapters.GoalListAdapter
import com.androidvoyage.goalmanager.database.GoalRepository
import com.androidvoyage.goalmanager.datamodels.GoalData
import com.androidvoyage.goalmanager.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), GoalListAdapter.ClickEvents {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(MainViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.getGoalsByType(this, "Long")!!.observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                rvLongTermGoals.layoutManager = LinearLayoutManager(this)
                rvLongTermGoals.adapter = GoalListAdapter(it, this,this as GoalListAdapter.ClickEvents)
                rvLongTermGoals.visibility = View.VISIBLE
                tvNoLongGoals.visibility = View.GONE
            } else {
                rvLongTermGoals.visibility = View.GONE
                tvNoLongGoals.visibility = View.VISIBLE
            }

        })

        mainViewModel.getGoalsByType(this, "Short")!!.observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                rvShortTermGoals.layoutManager = LinearLayoutManager(this)
                rvShortTermGoals.adapter = GoalListAdapter(it, this,this as GoalListAdapter.ClickEvents)
                rvShortTermGoals.visibility = View.VISIBLE
                tvNoShortGoals.visibility = View.GONE
            } else {
                rvShortTermGoals.visibility = View.GONE
                tvNoShortGoals.visibility = View.VISIBLE

            }

        })

        mainViewModel.getGoalsByType(this, "Daily")!!.observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                rvDailyGoals.layoutManager = LinearLayoutManager(this)
                rvDailyGoals.adapter = GoalListAdapter(it, this,this as GoalListAdapter.ClickEvents)
                rvDailyGoals.visibility = View.VISIBLE
                tvNoDailyGoals.visibility = View.GONE
            } else {
                rvDailyGoals.visibility = View.GONE
                tvNoDailyGoals.visibility = View.VISIBLE
            }

        })
    }

    fun addGoals(view: View) {
        startActivity(Intent(MainActivity@ this, AddGoals::class.java))
    }

    override fun onDoubleClick(goalData: GoalData) {
        mainViewModel.setGoalAsCompleted(this,goalData)
    }

    override fun onDeleteGoal(goalData: GoalData) {
        mainViewModel.deleteGoalData(this,goalData)
        onResume()
    }
}

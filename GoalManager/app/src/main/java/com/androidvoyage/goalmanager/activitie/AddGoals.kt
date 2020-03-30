package com.androidvoyage.goalmanager.activitie

import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidvoyage.goalmanager.R
import com.androidvoyage.goalmanager.database.GoalRepository
import com.androidvoyage.goalmanager.datamodels.GoalData
import kotlinx.android.synthetic.main.activity_add_goals.*
import java.util.*

class AddGoals : AppCompatActivity() {

    private var goalType: String = "Long"
    private var goalPriority: String = "A"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_goals)
        setUpListeners()
    }

    private fun setUpListeners() {
        rgType.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbLong -> {
                    goalType = "Long"
                }
                R.id.rbShort -> {
                    goalType = "Short"
                }
                R.id.rbDaily -> {
                    goalType = "Daily"
                }
            }
        }

        rgPriority.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbHigh -> {
                    goalPriority = "A"
                }
                R.id.rbMedium -> {
                    goalPriority = "B"
                }
                R.id.rbLow -> {
                    goalPriority = "C"
                }
            }
        })
    }


    fun addGoal(view: View) {
        if (validate()) {
            val goalData = GoalData(etGoal.text.toString(), goalType, goalPriority, Date(), Date())
            val goalRepository = GoalRepository()
            goalRepository.GoalRepository(this)
            goalRepository.insertTask(goalData)
            finish()
        }
    }

    private fun validate(): Boolean {
        if (etGoal.text.toString().isEmpty()) {
            Toast.makeText(this, "Enter Goal", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }
}

package com.androidvoyage.goalmanager.activitie

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import com.androidvoyage.goalmanager.R
import com.androidvoyage.goalmanager.database.GoalRepository
import com.androidvoyage.goalmanager.datamodels.GoalData
import kotlinx.android.synthetic.main.activity_add_goals.*
import java.text.SimpleDateFormat
import java.util.*

class AddGoals : AppCompatActivity() {

    private var goalType: String = "Long"
    private var goalPriority: String = "A"
    private var timeRequired: String? = null

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
            val goalData = GoalData(etGoal.text.toString(), goalType, goalPriority, Date(), Date(),timeRequired)
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

        if (timeRequired!!.isEmpty()) {
            Toast.makeText(this, "Enter Time required", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }


    fun onClickTime(view: View) {
        if (goalType == "Daily") {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                etTime.setText(SimpleDateFormat("HH:mm").format(cal.time).toString())
            }
            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        } else {
            val popupMenu = PopupMenu(this, etTime)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_one ->
                        timeRequired = "One Month"
                    R.id.action_three ->
                        timeRequired = "Three Months"
                    R.id.action_six ->
                        timeRequired = "Six Month"
                    R.id.action_one_year ->
                        timeRequired = "One Year"
                    R.id.action_five_year ->
                        timeRequired = "Five Year"
                    R.id.action_ten_year ->
                        timeRequired = "Ten Year"
                    R.id.action_lifetime ->
                        timeRequired = "Lifetime"
                }

                etTime.setText(timeRequired)
                true
            }
            popupMenu.show()
        }

    }
}

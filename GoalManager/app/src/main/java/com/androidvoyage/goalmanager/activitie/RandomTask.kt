package com.androidvoyage.goalmanager.activitie

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.androidvoyage.goalmanager.R
import com.androidvoyage.goalmanager.database.GoalRepository
import kotlin.random.Random


class RandomTask : AppCompatActivity() {

    private lateinit var textSwitcher: TextSwitcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_task)

        textSwitcher = findViewById(R.id.textSwitcher)


        textSwitcher.setFactory {
            val t = TextView(this@RandomTask)
            t.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            t.textSize = 50f
            t.setTextColor(this@RandomTask.applicationContext.getColor(R.color.colorPrimary))
            t.setTypeface(null, Typeface.BOLD)
            t
        }


        textSwitcher.inAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        textSwitcher.outAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_out)

    }

    fun start(view: View) {
        findViewById<Button>(R.id.btn_start).animate().setDuration(200).alpha(0f).start()
        val goalRepository = GoalRepository()
        goalRepository.GoalRepository(this)
        goalRepository.getGoalByType(getString(R.string.text_daily)).observe(this, Observer {
            var listCounter = 0
            textSwitcher.setCurrentText(it[listCounter].goal)
            val time = (300 * (it.size - 1)).toLong()
            object : CountDownTimer(time, 300) {
                override fun onTick(millisUntilFinished: Long) {
                    if (listCounter < it.size)
                        textSwitcher.setText(it[listCounter].goal)
                    listCounter++
                }

                override fun onFinish() {
                    val data = it[Random.nextInt(0, it.size - 1)]
                    textSwitcher.setText(data.goal)
                    findViewById<LinearLayout>(R.id.decision_linear).visibility = View.VISIBLE
                }
            }.start()
        })
    }


    fun willDoNow(view: View) {
        val intent = Intent(this@RandomTask, TimerActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    fun willDoLater(view: View) {
        finish()
    }
}
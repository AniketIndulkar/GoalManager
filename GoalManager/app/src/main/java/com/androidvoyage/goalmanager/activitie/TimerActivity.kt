package com.androidvoyage.goalmanager.activitie

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.androidvoyage.goalmanager.R

class TimerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        val timerText = findViewById<TextView>(R.id.tv_timer)

        val time = 25 * 60 * 1000

        object : CountDownTimer(time.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val remainingSec =(time -  millisUntilFinished) / 1000
                val minRemain = remainingSec / 60
                val secRemain = remainingSec - (minRemain * 60)

                val minStr = if(minRemain <10) "0$minRemain" else minRemain.toString()
                val secStr = if(secRemain <10) "0$secRemain" else secRemain.toString()

                timerText.text = "$minStr : $secStr"
            }

            override fun onFinish() {
            }
        }.start()
    }
}
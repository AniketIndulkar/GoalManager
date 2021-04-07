package com.androidvoyage.goalmanager.activitie.ui.dashboard

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidvoyage.goalmanager.R
import com.androidvoyage.goalmanager.activitie.AddGoals
import com.androidvoyage.goalmanager.activitie.ui.home.GoalsViewModel
import com.androidvoyage.goalmanager.adapters.GoalListAdapter
import com.androidvoyage.goalmanager.database.GoalRepository
import com.androidvoyage.goalmanager.databinding.FragmentTaskBinding
import com.androidvoyage.goalmanager.datamodels.GoalData
import kotlin.random.Random

class TaskFragment : Fragment(), GoalListAdapter.ClickEvents {

    private lateinit var goalsViewModel: GoalsViewModel
    private lateinit var binding: FragmentTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        goalsViewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
                .create(GoalsViewModel::class.java)
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        binding.ivAddGoals.setOnClickListener {
            startActivity(Intent(requireActivity(), AddGoals::class.java))
        }
        binding.btnWhatToDo.setOnClickListener {
            binding.randonTaskLayout.visibility = View.VISIBLE
            binding.taskLayout.visibility = View.GONE
        }

        binding.btnStart.setOnClickListener {
            start()
        }
        binding.textSwitcher.setFactory {
            val t = TextView(requireContext())
            t.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            t.textSize = 50f
            t.setTextColor(requireContext().applicationContext.getColor(R.color.colorPrimary))
            t.setTypeface(null, Typeface.BOLD)
            t
        }
        return binding.root
    }


    override fun onResume() {
        super.onResume()

        goalsViewModel.getGoalsByType(requireContext(), getString(R.string.text_daily))
            .observe(this, Observer {
                if (it != null && it.isNotEmpty()) {
                    binding.rvDailyGoals.layoutManager = LinearLayoutManager(requireContext())
                    binding.rvDailyGoals.adapter =
                        GoalListAdapter(it, requireContext(), this as GoalListAdapter.ClickEvents)
                    binding.rvDailyGoals.visibility = View.VISIBLE
                    binding.tvNoDailyGoals.visibility = View.GONE
                } else {
                    binding.rvDailyGoals.visibility = View.GONE
                    binding.tvNoDailyGoals.visibility = View.VISIBLE
                }

            })
    }

    override fun onDoubleClick(goalData: GoalData) {
        goalsViewModel.setGoalAsCompleted(requireContext(), goalData)
    }

    override fun onDeleteGoal(goalData: GoalData) {
        goalsViewModel.deleteGoalData(requireContext(), goalData)
        onResume()
    }

    fun start() {
        binding.btnStart.animate().setDuration(200).alpha(0f).start()
        val goalRepository = GoalRepository()
        goalRepository.GoalRepository(requireContext())
        goalRepository.getGoalByType(getString(R.string.text_daily)).observe(requireActivity(), Observer {
            var listCounter = 0
            binding.textSwitcher.setCurrentText(it[listCounter].goal)
            val time = (300 * (it.size - 1)).toLong()
            object : CountDownTimer(time, 300) {
                override fun onTick(millisUntilFinished: Long) {
                    if (listCounter < it.size)
                        binding.textSwitcher.setText(it[listCounter].goal)
                    listCounter++
                }

                override fun onFinish() {
                    val data = it[Random.nextInt(0, it.size - 1)]
                    binding.textSwitcher.setText(data.goal)
                    binding.decisionLinear.visibility = View.VISIBLE
                }
            }.start()
        })
    }
}
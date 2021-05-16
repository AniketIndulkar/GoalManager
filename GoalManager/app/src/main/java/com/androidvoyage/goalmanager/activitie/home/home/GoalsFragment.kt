package com.androidvoyage.goalmanager.activitie.home.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidvoyage.goalmanager.R
import com.androidvoyage.goalmanager.activitie.AddGoals
import com.androidvoyage.goalmanager.adapters.GoalListAdapter
import com.androidvoyage.goalmanager.databinding.FragmentGoalsBinding
import com.androidvoyage.goalmanager.datamodels.GoalData


class GoalsFragment : Fragment(),GoalListAdapter.ClickEvents {

    private lateinit var goalsViewModel: GoalsViewModel
    private lateinit var binding : FragmentGoalsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        goalsViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
            .create(GoalsViewModel::class.java)
        binding = FragmentGoalsBinding.inflate(inflater, container, false)
        binding.ivAddGoals.setOnClickListener {
            startActivity(Intent(requireActivity(), AddGoals::class.java))
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        goalsViewModel.getGoalsByType(requireContext(), getString(R.string.text_long)).observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                binding.rvLongTermGoals.layoutManager = LinearLayoutManager(requireContext())
                binding.rvLongTermGoals.adapter =
                    GoalListAdapter(it, requireContext(), this as GoalListAdapter.ClickEvents)
                binding.rvLongTermGoals.visibility = View.VISIBLE
                binding.tvNoLongGoals.visibility = View.GONE
            } else {
                binding.rvLongTermGoals.visibility = View.GONE
                binding.tvNoLongGoals.visibility = View.VISIBLE
            }

        })

        goalsViewModel.getGoalsByType(requireContext(), getString(R.string.text_short)).observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                binding.rvShortTermGoals.layoutManager = LinearLayoutManager(requireContext())
                binding.rvShortTermGoals.adapter =
                    GoalListAdapter(it, requireContext(), this as GoalListAdapter.ClickEvents)
                binding.rvShortTermGoals.visibility = View.VISIBLE
                binding.tvNoShortGoals.visibility = View.GONE
            } else {
                binding.rvShortTermGoals.visibility = View.GONE
                binding.tvNoShortGoals.visibility = View.VISIBLE

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
}
package com.androidvoyage.goalmanager.activitie.home.study

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.androidvoyage.goalmanager.R
import com.androidvoyage.goalmanager.activitie.study.StudyActivity

class StudyFragment : Fragment() {

    companion object {
        fun newInstance() = StudyFragment()
    }

    private lateinit var viewModel: StudyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.study_fragment, container, false)
        val btnStart = rootView.findViewById<LinearLayout>(R.id.btnStartStudy)
        btnStart.setOnClickListener {
            startActivity(Intent(activity, StudyActivity::class.java))
        }
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StudyViewModel::class.java)
        // TODO: Use the ViewModel
    }



}
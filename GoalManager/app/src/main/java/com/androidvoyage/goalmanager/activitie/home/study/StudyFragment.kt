package com.androidvoyage.goalmanager.activitie.home.study

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidvoyage.goalmanager.R
import com.androidvoyage.goalmanager.activitie.study.StudyActivity

class StudyFragment : Fragment(), AllPostAdapter.AllPostClickListener {

    companion object {
        fun newInstance() = StudyFragment()
    }

    private lateinit var viewModel: StudyViewModel
    lateinit var rvAllStudy : RecyclerView
    lateinit var btnStart : LinearLayout
    lateinit var allPostAdapter : AllPostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.study_fragment, container, false)
        btnStart = rootView.findViewById(R.id.btnStartStudy)
        rvAllStudy= rootView.findViewById(R.id.rvAllStudy)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StudyViewModel::class.java)
        viewModel.getAllPost(requireActivity().baseContext)
        viewModel.postDataList.observe(requireActivity(), Observer {
            rvAllStudy.layoutManager = LinearLayoutManager(requireContext())
            allPostAdapter = AllPostAdapter()
            rvAllStudy.adapter= allPostAdapter
            allPostAdapter.setClickListener(this@StudyFragment)
            allPostAdapter.setData(it)
        })

        btnStart.setOnClickListener {
            startActivity(Intent(activity, StudyActivity::class.java))
        }
    }

    override fun postClicked(postId: Long) {
        val intent = Intent(requireActivity(), StudyActivity::class.java)
        intent.putExtra("PostId", postId)
        startActivity(intent)
    }
}
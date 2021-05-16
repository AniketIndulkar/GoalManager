package com.androidvoyage.goalmanager.activitie.study

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.androidvoyage.goalmanager.R
import com.androidvoyage.goalmanager.viewmodels.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior


class StudyActivity : AppCompatActivity() {

    lateinit var viewModel: StudyViewModel

    var sheetBehavior: BottomSheetBehavior<*>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study)
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(StudyViewModel::class.java)
        sheetBehavior = BottomSheetBehavior.from(findViewById<LinearLayout>(R.id.bottom_sheet));
        viewModel.studyData.observe(this, Observer {
            print("StudyActivity $it")
        })
    }

    fun onClickAddComponent(view: View) {
        if (sheetBehavior!!.state != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior!!.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            collapseSheep()
        }
    }

    private fun collapseSheep() {
        sheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED;
    }

    fun addText(view: View) {
        collapseSheep()
        viewModel.addData(1)
    }

    fun addPara(view: View) {
        collapseSheep()
        viewModel.addData(2)
    }

    fun addImage(view: View) {
        collapseSheep()
        viewModel.addData(3)
    }

    fun addList(view: View) {
        collapseSheep()
        viewModel.addData(4)
    }

    fun addQuote(view: View) {
        collapseSheep()
        viewModel.addData(5)
    }

}
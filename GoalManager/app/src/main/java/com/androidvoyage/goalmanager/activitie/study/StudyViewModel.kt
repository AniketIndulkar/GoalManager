package com.androidvoyage.goalmanager.activitie.study

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidvoyage.goalmanager.datamodels.StudyData

class StudyViewModel(application: Application) : AndroidViewModel(application) {

    private var _studyData: MutableLiveData<List<StudyData>> = MutableLiveData()
    val studyData: LiveData<List<StudyData>>
        get() = _studyData

    init {
        _studyData.value = ArrayList<StudyData>()
    }

    fun addData(type : Int){
        var data = StudyData(type)
        _studyData.value = _studyData.value!!.plus(data)
    }

}
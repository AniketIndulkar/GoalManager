package com.androidvoyage.goalmanager.activitie.study

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidvoyage.goalmanager.datamodels.StudyData

class StudyViewModel(application: Application) : AndroidViewModel(application) {

    private var _studyDataList: MutableLiveData<MutableList<StudyData>> = MutableLiveData()
    val studyDataList: LiveData<MutableList<StudyData>>
        get() = _studyDataList

    private var _studyData: MutableLiveData<StudyData> = MutableLiveData()
    val studyData: LiveData<StudyData>
        get() = _studyData

    init {
        _studyDataList.value = ArrayList<StudyData>()
    }

    fun addData(type : Int){
        var data = StudyData(type)
        val dataList = _studyDataList.value
        dataList!!.add(data)
        _studyData.value = data
    }
    fun addImageData(type : Int, imageUri : String){
        var data = StudyData(type, imageData= imageUri )
        val dataList = _studyDataList.value
        dataList!!.add(data)
        _studyData.value = data
    }

}
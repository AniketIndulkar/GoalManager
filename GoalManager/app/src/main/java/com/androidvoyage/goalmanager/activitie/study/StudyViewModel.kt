package com.androidvoyage.goalmanager.activitie.study

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidvoyage.goalmanager.datamodels.PostData

class StudyViewModel(application: Application) : AndroidViewModel(application) {

    private var _postDataList: MutableLiveData<MutableList<PostData>> = MutableLiveData()
    val postDataList: LiveData<MutableList<PostData>>
        get() = _postDataList

    private var _postData: MutableLiveData<PostData> = MutableLiveData()
    val postData: LiveData<PostData>
        get() = _postData

    init {
        _postDataList.value = ArrayList<PostData>()
    }

    fun addData(type : Int){
        var data = PostData()
        data.type = type
        val dataList = _postDataList.value
        dataList!!.add(data)
        _postData.value = data
    }
    fun addImageData(type : Int, imageUri : String){
        var data = PostData()
        data.type = type
        data.imageData=imageUri
        val dataList = _postDataList.value
        dataList!!.add(data)
        _postData.value = data
    }

}
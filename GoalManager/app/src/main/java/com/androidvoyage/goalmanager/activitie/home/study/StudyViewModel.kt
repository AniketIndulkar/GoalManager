package com.androidvoyage.goalmanager.activitie.home.study

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidvoyage.goalmanager.database.GoalRepository
import com.androidvoyage.goalmanager.datamodels.Post
import com.androidvoyage.goalmanager.datamodels.PostData

class StudyViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    private var _postDataList: MutableLiveData<List<Post>> = MutableLiveData()
    val postDataList: LiveData<List<Post>>
        get() = _postDataList

    fun getAllPost(context: Context){
        val repo = GoalRepository()
        repo.GoalRepository(context)
        _postDataList.value=repo.getAllPost()
    }

}
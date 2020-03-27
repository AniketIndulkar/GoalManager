package com.androidvoyage.goalmanager.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.androidvoyage.goalmanager.database.GoalRepository
import com.androidvoyage.goalmanager.datamodels.GoalData

class MainViewModel(application: Application) : AndroidViewModel(application) {

    fun getAllTheGoals(context: Context): LiveData<List<GoalData>> {
        val goalRepository = GoalRepository()
        goalRepository.GoalRepository(context)
        return goalRepository.getTasks()
    }

    fun getGoalsByType(context: Context, type: String): LiveData<List<GoalData>> {
        val goalRepository = GoalRepository()
        goalRepository.GoalRepository(context)
        return goalRepository.getGoalByType(type)
    }

    fun deleteGoalData(mContext:Context ,goalData: GoalData){
        val goalRepository = GoalRepository()
        goalRepository.GoalRepository(mContext)
        goalRepository.deleteTask(goalData)
    }


    fun setGoalAsCompleted(mContext: Context,goalData: GoalData){
        val goalRepository = GoalRepository()
        goalRepository.GoalRepository(mContext)
        goalRepository.insertTask(goalData)
    }

}
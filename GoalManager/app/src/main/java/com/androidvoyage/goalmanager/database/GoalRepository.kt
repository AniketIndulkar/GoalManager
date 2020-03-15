package com.androidvoyage.goalmanager.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.androidvoyage.goalmanager.datamodels.GoalData


class GoalRepository {

    private val DB_NAME = "GoalDb"

    private var noteDatabase: RoomDb? = null
    fun GoalRepository(context: Context) {
        noteDatabase =
            Room.databaseBuilder<RoomDb>(context, RoomDb::class.java, DB_NAME)
                .allowMainThreadQueries().build()
    }

    fun insertTask(goal: GoalData?) {
        noteDatabase!!.daoAccess()!!.insertTask(goal)
    }

    fun updateTask(note: GoalData) {
        noteDatabase!!.daoAccess()!!.updateTask(note)
    }

    fun deleteTask(goal: GoalData) {
        noteDatabase!!.daoAccess()!!.deleteTask(goal)
    }

    fun getGoalByType(type: String): LiveData<List<GoalData>> {
        return noteDatabase!!.daoAccess()!!.getGoalsByType(type)
    }

    fun getTasks(): LiveData<List<GoalData>> {
        return noteDatabase!!.daoAccess()!!.fetchAllTasks()
    }

}
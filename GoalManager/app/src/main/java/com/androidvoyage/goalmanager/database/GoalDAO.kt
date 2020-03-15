package com.androidvoyage.goalmanager.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.androidvoyage.goalmanager.datamodels.GoalData


@Dao
interface GoalDAO {

    @Insert
    fun insertTask(note: GoalData?): Long?

    @Query("SELECT * FROM GoalData ORDER BY GoalPriority desc")
    fun fetchAllTasks(): LiveData<List<GoalData>>

    @Query("SELECT * FROM GoalData WHERE GoalType =:type ORDER BY GoalPriority DESC")
    fun getGoalsByType(type: String): LiveData<List<GoalData>>

    @Update
    fun updateTask(note: GoalData?)

    @Delete
    fun deleteTask(note: GoalData?)
}
package com.androidvoyage.goalmanager.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.androidvoyage.goalmanager.datamodels.GoalData


class GoalRepository {

    private val DB_NAME = "GoalDb"

    private var noteDatabase: RoomDb? = null

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE GoalData ADD COLUMN 'Time_Required' TEXT;")
            database.execSQL("ALTER TABLE GoalData ADD COLUMN 'Times_Completed' INTEGER NOT NULL DEFAULT 0;")
        }
    }

    fun GoalRepository(context: Context) {
        noteDatabase =
            Room.databaseBuilder<RoomDb>(context, RoomDb::class.java, DB_NAME)
                .addMigrations(MIGRATION_1_2)
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

    fun getGoalByTypeList(type: String): List<GoalData> {
        return noteDatabase!!.daoAccess()!!.getGoalsByTypeList(type)
    }

    fun getInCompletedGoal(isCompleted: Boolean): List<GoalData> {
        return noteDatabase!!.daoAccess()!!.getInCompletedGoal(isCompleted, "Daily")
    }

    fun getTasks(): LiveData<List<GoalData>> {
        return noteDatabase!!.daoAccess()!!.fetchAllTasks()
    }

    fun getGoalById(id: Int): GoalData {
        return noteDatabase!!.daoAccess()!!.getGoalsById(id)
    }

}
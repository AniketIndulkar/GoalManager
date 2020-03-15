package com.androidvoyage.goalmanager.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.androidvoyage.goalmanager.datamodels.GoalData


@Database(entities = [GoalData::class], version = 1, exportSchema = false)
public abstract class RoomDb : RoomDatabase() {
    abstract fun daoAccess(): GoalDAO?

}
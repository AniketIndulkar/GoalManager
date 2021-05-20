package com.androidvoyage.goalmanager.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.androidvoyage.goalmanager.datamodels.GoalData
import com.androidvoyage.goalmanager.datamodels.Post
import com.androidvoyage.goalmanager.datamodels.PostData


@Database(
    entities = [GoalData::class, Post::class, PostData::class],
    version = 3,
    exportSchema = false
)
public abstract class RoomDb : RoomDatabase() {
    abstract fun daoAccess(): GoalDAO?

    abstract fun postDao(): PostDAO?
    abstract fun postDataDao(): PostDataDAO?

}
package com.androidvoyage.goalmanager.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.androidvoyage.goalmanager.datamodels.GoalData
import com.androidvoyage.goalmanager.datamodels.Post

@Dao
interface PostDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(note: Post?): Long?

    @Query("SELECT * FROM Post ORDER BY date desc")
    fun fetchAllPost(): List<Post>

    @Query("SELECT * FROM Post WHERE postId =:id")
    fun getPostById(id: Int): Post

    @Query("SELECT * FROM Post WHERE postId =:id")
    fun getPostByHeader(id: Int): Post

    @Update
    fun updatePost(note: Post?)

    @Delete
    fun deletePost(note: Post?)
}
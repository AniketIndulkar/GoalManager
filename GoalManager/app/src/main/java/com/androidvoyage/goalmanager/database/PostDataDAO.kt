package com.androidvoyage.goalmanager.database

import androidx.room.*
import com.androidvoyage.goalmanager.datamodels.PostData

@Dao
interface PostDataDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(note: PostData?): Long?

    @Query("SELECT * FROM PostData ORDER BY date desc")
    fun fetchAllPostData(): List<PostData>

    @Query("SELECT * FROM PostData WHERE postId =:postId")
    fun getPostDataById(postId: Long): List<PostData>

    @Update
    fun updatePost(note: PostData?)

    @Delete
    fun deletePost(note: PostData?)
}
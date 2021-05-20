package com.androidvoyage.goalmanager.datamodels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.androidvoyage.goalmanager.utils.TimestampConverter
import java.io.Serializable
import java.util.*

/*
* type
* 1 - Heading
* 2- Paragraph
* 3- Image
* 4- List
* 5- Quote
* */
@Entity
class PostData: Serializable{
    var type: Int=0
    @PrimaryKey(autoGenerate = true)
    var dataId: Long = 0
    var postId: Long = 0
    var stringData: String = ""
    var imageData: String = ""
    var listData: String = ""
    var quoteData: String = ""
    @ColumnInfo(name ="Date")
    @TypeConverters(TimestampConverter::class)
    var date: Date? = null
}
package com.androidvoyage.goalmanager.datamodels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.androidvoyage.goalmanager.utils.TimestampConverter
import java.io.Serializable
import java.util.*

@Entity
class Post : Serializable{
    constructor(header: String, date: Date?) {
        this.header = header
        this.date = date
    }

    @PrimaryKey(autoGenerate = true)
    var postId : Int = 0

    @ColumnInfo(name ="Header")
    var header : String = ""

    @ColumnInfo(name ="Date")
    @TypeConverters(TimestampConverter::class)
    var date : Date? = null

}
package com.androidvoyage.goalmanager.datamodels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.androidvoyage.goalmanager.utils.TimestampConverter
import java.io.Serializable
import java.util.*


@Entity
class GoalData : Serializable {

    @PrimaryKey(autoGenerate = true)
    private var goalId = 0

    @ColumnInfo(name = "Goal")
    var goal: String

    @ColumnInfo(name = "GoalType")
    var goalType: String

    @ColumnInfo(name = "GoalPriority")
    var goalPriority: String

    @ColumnInfo(name = "IsCompleted")
    var isCompleted: Boolean = false

    @ColumnInfo(name = "created_at")
    @TypeConverters(TimestampConverter::class)
    var createdAt: Date? = null

    @ColumnInfo(name = "modified_at")
    @TypeConverters(TimestampConverter::class)
    var modifiedAt: Date? = null

    @ColumnInfo(name = "Time_Required")
    var timeRequired: String? = null

    @ColumnInfo(name = "Times_Completed")
    private var timesCompleted = 0

    constructor(
        goal: String,
        goalType: String,
        goalPriority: String,
        createdAt: Date?,
        modifiedAt: Date?,
        timeRequired: String?
    ) {
        this.goal = goal
        this.goalType = goalType
        this.goalPriority = goalPriority
        this.createdAt = createdAt
        this.modifiedAt = modifiedAt
        this.timeRequired = timeRequired
    }


    fun getGoalId(): Int {
        return goalId
    }

    fun setGoalId(goalId: Int) {
        this.goalId = goalId
    }

    fun getTimesCompleted(): Int {
        return timesCompleted
    }

    fun setTimesCompleted(timesCompleted: Int) {
        this.timesCompleted = timesCompleted
    }
}
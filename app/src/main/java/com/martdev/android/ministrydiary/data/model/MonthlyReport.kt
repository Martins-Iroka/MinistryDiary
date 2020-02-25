package com.martdev.android.ministrydiary.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "monthly_report")
data class MonthlyReport (
    @PrimaryKey
    @ColumnInfo(name = "month_id")
    var id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "month")
    var date: Date? = null
)
package com.martdev.android.ministrydiary.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "ministry_report")
data class MinistryReport(
    @PrimaryKey
    @ColumnInfo(name = "report_id")
    var id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "placement")
    var placement: String = "",

    @ColumnInfo(name = "video_showing")
    var videoShowing: String = "",

    @ColumnInfo(name = "hours")
    var hours: String = "",

    @ColumnInfo(name = "minutes")
    var minutes: String = "",

    @ColumnInfo(name = "return_visits")
    var returnVisits: String = "",

    @ColumnInfo(name = "bible_students")
    var bibleStudies: String = "",

    @ColumnInfo(name = "dateString")
    var dateString: String = "",

    @ColumnInfo(name = "_date")
    var date: Date = Date()
)

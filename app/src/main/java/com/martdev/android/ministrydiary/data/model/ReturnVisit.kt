package com.martdev.android.ministrydiary.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "return_visit_table")
data class ReturnVisit(
    @PrimaryKey
    @ColumnInfo(name = "rv_Id")
    var id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "rv_name")
    var name: String = "",

    @ColumnInfo(name = "rv_address")
    var address: String = "",

    @ColumnInfo(name = "rv_phone_number")
    var phoneNumber: String = "",

    @ColumnInfo(name = "placement_id")
    var placementId: Int = 0,

    @ColumnInfo(name = "placement")
    var placement: String = "",

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "question_left")
    var question: String = "",

    @ColumnInfo(name = "_date")
    var date: Date = Date(),

    @ColumnInfo(name = "_time")
    var time: Date = Date(),

    @ColumnInfo(name = "visit_status")
    var isVisitingRV: Boolean = false

)

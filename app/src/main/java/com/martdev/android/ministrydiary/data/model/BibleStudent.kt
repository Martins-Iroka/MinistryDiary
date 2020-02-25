package com.martdev.android.ministrydiary.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "bible_student_table")
data class BibleStudent(
    @PrimaryKey
    @ColumnInfo(name = "bs_id")
    var id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "bs_name")
    var name: String = "",

    @ColumnInfo(name = "bs_address")
    var address: String = "",

    @ColumnInfo(name = "bs_phone_Number")
    var phoneNumber: String = "",

    @ColumnInfo(name = "study_material_id")
    var studyMaterialId: Int = 0,

    @ColumnInfo(name = "_chapter")
    var chapter: String = "",

    @ColumnInfo(name = "_paragraph")
    var paragraph: String = "",

    @ColumnInfo(name = "visit_status")
    var isVisitingBS: Boolean = false,

    @ColumnInfo(name = "_date")
    var date: Date = Date(),

    @ColumnInfo(name = "_time")
    var time: Date = Date()
)
package com.martdev.android.ministrydiary.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.martdev.android.ministrydiary.data.dao.BibleStudentDao
import com.martdev.android.ministrydiary.data.dao.MinistryReportDao
import com.martdev.android.ministrydiary.data.dao.MonthlyReportDao
import com.martdev.android.ministrydiary.data.dao.ReturnVisitDao
import com.martdev.android.ministrydiary.data.dateconverter.DateConverter
import com.martdev.android.ministrydiary.data.model.BibleStudent
import com.martdev.android.ministrydiary.data.model.MinistryReport
import com.martdev.android.ministrydiary.data.model.MonthlyReport
import com.martdev.android.ministrydiary.data.model.ReturnVisit

@Database(entities = [ReturnVisit::class, BibleStudent::class, MonthlyReport::class,
    MinistryReport::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class MinistryDiaryDB : RoomDatabase() {

    abstract fun rvDao(): ReturnVisitDao

    abstract fun bsDao(): BibleStudentDao

    abstract fun monthlyReportDao(): MonthlyReportDao

    abstract fun ministryReportDao(): MinistryReportDao

    companion object {

        private var INSTANCE: MinistryDiaryDB? = null

        private val sLock = Any()

        fun getInstance(context: Context): MinistryDiaryDB {
            synchronized(sLock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            MinistryDiaryDB::class.java, "MinistryDiary.db")
                            .build()
                }
                return INSTANCE!!
            }
        }
    }
}

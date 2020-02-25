package com.martdev.android.ministrydiary.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.martdev.android.ministrydiary.data.model.MinistryReport

@Dao
interface MinistryReportDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: MinistryReport)

    @Update
    suspend fun updateReport(report: MinistryReport)

    @Query("DELETE FROM ministry_report WHERE report_id = :reportId")
    suspend fun deleteReport(reportId: String)

    @Query("SELECT * FROM ministry_report WHERE dateString = :_date")
    fun getMinistryReports(date: String): LiveData<List<MinistryReport>>

    @Query("SELECT * FROM ministry_report WHERE report_id = :reportId")
    suspend fun getMinistryReport(reportId: String): MinistryReport

    @Query("DELETE FROM ministry_report WHERE dateString = :_date")
    suspend fun deleteMinistryReportsByDate(date: String)
}

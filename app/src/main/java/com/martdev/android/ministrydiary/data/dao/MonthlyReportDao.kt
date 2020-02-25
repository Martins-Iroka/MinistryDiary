package com.martdev.android.ministrydiary.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.martdev.android.ministrydiary.data.model.MonthlyReport

@Dao
interface MonthlyReportDao {

    @get:Query("SELECT * FROM monthly_report")
    val monthlyReports: LiveData<List<MonthlyReport>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMonthlyReport(report: MonthlyReport)

    @Query("DELETE FROM monthly_report WHERE month_id = :monthId")
    suspend fun deleteMonthlyReport(monthId: String)

    @Query("SELECT * FROM monthly_report WHERE month_id = :id")
    suspend fun getMonthlyReport(id: String): MonthlyReport
}

package com.martdev.android.ministrydiary.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.martdev.android.ministrydiary.data.model.ReturnVisit
import java.util.*

@Dao
interface ReturnVisitDao {

    @Query("SELECT * FROM return_visit_table")
    fun getReturnVisits(): LiveData<List<ReturnVisit>>

    @Query("SELECT * FROM return_visit_table WHERE _date = :date")
    fun filterByDate(date: Date): LiveData<List<ReturnVisit>>

    @Query("SELECT * FROM return_visit_table WHERE rv_Id = :rvId")
    suspend fun getReturnVisit(rvId: String): ReturnVisit

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReturnVisit(rv: ReturnVisit)

    @Update
    suspend fun updateReturnVisit(rv: ReturnVisit)

    @Query("UPDATE return_visit_table SET visit_status = :isVisiting WHERE rv_Id = :rvId")
    suspend fun visitStatus(rvId: String, isVisiting: Boolean)

    @Query("DELETE FROM return_visit_table WHERE rv_Id = :rvId")
    suspend fun deleteReturnVisit(rvId: String)
}

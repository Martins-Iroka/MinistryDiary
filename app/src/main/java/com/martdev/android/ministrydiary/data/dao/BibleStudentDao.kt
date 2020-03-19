package com.martdev.android.ministrydiary.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.martdev.android.ministrydiary.data.model.BibleStudent
import java.util.*

@Dao
interface BibleStudentDao {

    @Query("SELECT * FROM bible_student_table")
    fun getBibleStudents(): LiveData<List<BibleStudent>>

    @Query("SELECT * FROM bible_student_table WHERE _date = :date")
    fun filterByDate(date: Date): LiveData<List<BibleStudent>>

    @Query("SELECT * FROM bible_student_table WHERE bs_id = :bsId")
    suspend fun getBibleStudent(bsId: String): BibleStudent

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBibleStudent(bs: BibleStudent)

    @Update
    suspend fun updateBibleStudent(bs: BibleStudent)

    @Query("DELETE FROM bible_student_table WHERE bs_id = :bsId")
    suspend fun deleteBibleStudent(bsId: String)
}
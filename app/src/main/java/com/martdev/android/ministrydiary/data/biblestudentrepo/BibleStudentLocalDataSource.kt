package com.martdev.android.ministrydiary.data.biblestudentrepo

import androidx.lifecycle.LiveData
import com.martdev.android.ministrydiary.data.GenericDataSource
import com.martdev.android.ministrydiary.data.Result
import com.martdev.android.ministrydiary.data.dao.BibleStudentDao
import com.martdev.android.ministrydiary.data.model.BibleStudent
import com.martdev.android.ministrydiary.utils.getResultStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class BibleStudentLocalDataSource internal constructor(
    private val bibleStudentDao: BibleStudentDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GenericDataSource<BibleStudent> {

    override fun getList(dateString: String): Result<LiveData<List<BibleStudent>>> {
        return bibleStudentDao.getBibleStudents().getResultStatus()
    }

    override suspend fun getItem(id: String): Result<BibleStudent> {
        return withContext(ioDispatcher) {
            val bibleStudent = bibleStudentDao.getBibleStudent(id)
            bibleStudent.getResultStatus()
        }
    }

    override suspend fun insertItem(item: BibleStudent) = withContext(ioDispatcher) {
        bibleStudentDao.insertBibleStudent(item)
    }

    override suspend fun updateItem(item: BibleStudent) = withContext(ioDispatcher) {
        bibleStudentDao.updateBibleStudent(item)
    }

    override suspend fun deleteItem(id: String) = withContext(ioDispatcher) {
        bibleStudentDao.deleteBibleStudent(id)
    }

    override fun filterByData(date: Date): Result<LiveData<List<BibleStudent>>> {
        val filteredList = bibleStudentDao.filterByDate(date)
        return Result.Success(filteredList)
    }
}
package com.martdev.android.ministrydiary.data.biblestudentrepo

import androidx.lifecycle.LiveData
import com.martdev.android.ministrydiary.utils.checkResultStatus
import com.martdev.android.ministrydiary.data.Result
import com.martdev.android.ministrydiary.data.model.BibleStudent
import com.martdev.android.ministrydiary.data.GenericRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class BibleStudentRepo(
    private val dataSource: BibleStudentLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GenericRepo<BibleStudent> {

    //TODO this is the only place you called the extension function. Check it again
    override fun getList(dateString: String): Result<LiveData<List<BibleStudent>>> {
        val result = dataSource.getList()
        return result.checkResultStatus() as Result<LiveData<List<BibleStudent>>>
    }

    override suspend fun getItem(id: String): Result<BibleStudent> {
        return withContext(ioDispatcher) {
            val result = dataSource.getItem(id)
            if (result is Result.Success) Result.Success(result.data)
            else Result.Error(Exception("Error fetching from local"))
        }
    }

    override suspend fun insertItem(item: BibleStudent) = withContext(ioDispatcher) {
        dataSource.insertItem(item)
    }

    override suspend fun updateItem(item: BibleStudent) = withContext(ioDispatcher) {
        dataSource.updateItem(item)
    }

    override suspend fun deleteItem(id: String) = withContext(ioDispatcher) {
        dataSource.deleteItem(id)
    }

    override fun filterByData(date: Date): Result<LiveData<List<BibleStudent>>> {
        val result = dataSource.filterByData(date)
        return if (result is Result.Success) {
            Result.Success(result.data)
        } else Result.Error(Exception("Can't filter by date"))
    }
}
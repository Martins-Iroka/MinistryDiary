package com.martdev.android.ministrydiary.data.returnvisirepo

import androidx.lifecycle.LiveData
import com.martdev.android.ministrydiary.data.GenericDataSource
import com.martdev.android.ministrydiary.data.Result
import com.martdev.android.ministrydiary.data.dao.ReturnVisitDao
import com.martdev.android.ministrydiary.data.model.ReturnVisit
import com.martdev.android.ministrydiary.utils.getResultStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class ReturnVisitDataSource internal constructor(
    private val returnVisitDao: ReturnVisitDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GenericDataSource<ReturnVisit> {

    override fun getList(dateString: String): Result<LiveData<List<ReturnVisit>>> {
        return returnVisitDao.getReturnVisits().getResultStatus()
    }

    override suspend fun getItem(id: String): Result<ReturnVisit> {
        return withContext(ioDispatcher) {
            returnVisitDao.getReturnVisit(id).getResultStatus()
        }
    }

    override suspend fun insertItem(item: ReturnVisit) {
        withContext(ioDispatcher) {
            returnVisitDao.insertReturnVisit(item)
        }
    }

    override suspend fun updateItem(item: ReturnVisit) {
        withContext(ioDispatcher) {
            returnVisitDao.updateReturnVisit(item)
        }
    }

    override suspend fun deleteItem(id: String) {
        withContext(ioDispatcher) {
            returnVisitDao.deleteReturnVisit(id)
        }
    }

    override fun filterByData(date: Date): Result<LiveData<List<ReturnVisit>>> {
        return returnVisitDao.filterByDate(date).getResultStatus()
    }
}
package com.martdev.android.ministrydiary.data.ministryreportrepo

import androidx.lifecycle.LiveData
import com.martdev.android.ministrydiary.data.GenericRepo
import com.martdev.android.ministrydiary.data.Result
import com.martdev.android.ministrydiary.data.model.MinistryReport
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class MinistryReportRepo(
    private val dataSource: MinistryReportDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GenericRepo<MinistryReport> {

    override fun getList(dateString: String): Result<LiveData<List<MinistryReport>>> {
        return when (val ministryReports = dataSource.getList(dateString)) {
            is Result.Success -> Result.Success(ministryReports.data)
            is Result.Error -> Result.Error(Exception("Can'[t get ministry reports"))
            Result.Loading -> Result.Loading
        }
    }

    override suspend fun getItem(id: String): Result<MinistryReport> {
        return withContext(ioDispatcher) {
            val ministryReport = dataSource.getItem(id)
            if (ministryReport is Result.Success) Result.Success(ministryReport.data)
            else Result.Error(Exception("Can't get ministry"))
        }
    }

    override suspend fun insertItem(item: MinistryReport) {
        return withContext(ioDispatcher) {
            dataSource.insertItem(item)
        }
    }

    override suspend fun updateItem(item: MinistryReport) {
        return withContext(ioDispatcher) {
            dataSource.updateItem(item)
        }
    }

    override suspend fun deleteItem(id: String) {
        return withContext(ioDispatcher) {
            dataSource.deleteItem(id)
        }
    }

    override suspend fun deleteByDate(date: String) {
        return withContext(ioDispatcher) {
            dataSource.deleteByDate(date)
        }
    }

    override fun filterByData(date: Date): Result<LiveData<List<MinistryReport>>> {
        return super.filterByData(date)
    }
}
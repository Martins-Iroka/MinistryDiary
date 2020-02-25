package com.martdev.android.ministrydiary.data.monthlyreport

import androidx.lifecycle.LiveData
import com.martdev.android.ministrydiary.data.GenericDataSource
import com.martdev.android.ministrydiary.data.Result
import com.martdev.android.ministrydiary.data.dao.MonthlyReportDao
import com.martdev.android.ministrydiary.data.model.MonthlyReport
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MonthlyReportSource internal constructor(
    private val monthlyReportDao: MonthlyReportDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GenericDataSource<MonthlyReport>{

    override fun getList(dateString: String): Result<LiveData<List<MonthlyReport>>> {
        val monthlyReports = monthlyReportDao.monthlyReports
        Result.Loading
        return when {
            monthlyReports.value?.isNotEmpty()!! -> Result.Success(monthlyReports)
            else -> Result.Error(Exception("Can't retrieve"))
        }
    }

    override suspend fun getItem(id: String): Result<MonthlyReport> {
        return withContext(ioDispatcher) {
            val monthlyReport = monthlyReportDao.getMonthlyReport(id)
            Result.Success(monthlyReport)
        }
    }

    override suspend fun insertItem(item: MonthlyReport) {
        return withContext(ioDispatcher) {
            monthlyReportDao.insertMonthlyReport(item)
        }
    }

    override suspend fun deleteItem(id: String) {
        return withContext(ioDispatcher) {
            monthlyReportDao.deleteMonthlyReport(id)
        }
    }
}
package com.martdev.android.ministrydiary.data.monthlyreport

import androidx.lifecycle.LiveData
import com.martdev.android.ministrydiary.data.GenericRepo
import com.martdev.android.ministrydiary.data.Result
import com.martdev.android.ministrydiary.data.model.MonthlyReport
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MonthlyReportRepo(
    private val dataSource: MonthlyReportSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GenericRepo<MonthlyReport>{

    override fun getList(dateString: String): Result<LiveData<List<MonthlyReport>>> {
        return when(val monthlyReports = dataSource.getList()) {
            is Result.Success -> Result.Success(monthlyReports.data)
            is Result.Error -> Result.Error(Exception("Can't get monthly report list"))
            Result.Loading -> Result.Loading
        }
    }

    override suspend fun getItem(id: String): Result<MonthlyReport> {
        return withContext(ioDispatcher) {
            val monthlyReport = dataSource.getItem(id)
            if (monthlyReport is Result.Success) Result.Success(monthlyReport.data)
            else Result.Error(Exception("Can't get monthly report"))
        }
    }

    override suspend fun insertItem(item: MonthlyReport) {
        return withContext(ioDispatcher) {
            dataSource.insertItem(item)
        }
    }

    override suspend fun deleteItem(id: String) {
        return withContext(ioDispatcher) {
            dataSource.deleteItem(id)
        }
    }
}
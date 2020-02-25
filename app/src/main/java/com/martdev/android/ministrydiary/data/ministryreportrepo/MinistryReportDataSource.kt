package com.martdev.android.ministrydiary.data.ministryreportrepo

import androidx.lifecycle.LiveData
import com.martdev.android.ministrydiary.data.GenericDataSource
import com.martdev.android.ministrydiary.data.Result
import com.martdev.android.ministrydiary.data.dao.MinistryReportDao
import com.martdev.android.ministrydiary.data.model.MinistryReport
import com.martdev.android.ministrydiary.utils.getResultStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MinistryReportDataSource internal constructor(
    private val ministryReportDao: MinistryReportDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GenericDataSource<MinistryReport>{

    override fun getList(dateString: String): Result<LiveData<List<MinistryReport>>> {
        val ministryReports = ministryReportDao.getMinistryReports(dateString)
        return ministryReports.getResultStatus()
    }

    override suspend fun getItem(id: String): Result<MinistryReport> {
        return withContext(ioDispatcher) {
            val ministryReport = ministryReportDao.getMinistryReport(id)
            ministryReport.getResultStatus()
        }
    }

    override suspend fun insertItem(item: MinistryReport) = withContext(ioDispatcher) {
        ministryReportDao.insertReport(item)
    }

    override suspend fun updateItem(item: MinistryReport) = withContext(ioDispatcher) {
        ministryReportDao.updateReport(item)
    }

    override suspend fun deleteItem(id: String) = withContext(ioDispatcher) {
        ministryReportDao.deleteReport(id)
    }

    override suspend fun deleteByDate(date: String) = withContext(ioDispatcher) {
        ministryReportDao.deleteMinistryReportsByDate(date)
    }
}
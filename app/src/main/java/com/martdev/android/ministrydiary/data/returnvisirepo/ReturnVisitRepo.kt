package com.martdev.android.ministrydiary.data.returnvisirepo

import androidx.lifecycle.LiveData
import com.martdev.android.ministrydiary.data.GenericRepo
import com.martdev.android.ministrydiary.data.Result
import com.martdev.android.ministrydiary.data.Result.Error
import com.martdev.android.ministrydiary.data.Result.Success
import com.martdev.android.ministrydiary.data.model.ReturnVisit
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class ReturnVisitRepo(
    private val dataSource: ReturnVisitDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GenericRepo<ReturnVisit>() {

    override fun getList(dateString: String): Result<LiveData<List<ReturnVisit>>> {
        return when(val returnVisits = dataSource.getList()) {
            is Success -> Success(returnVisits.data)
            is Error -> Error(Exception("Can't get monthly report list"))
            Result.Loading -> Result.Loading
        }
    }

    override suspend fun getItem(id: String): Result<ReturnVisit> {
        return withContext(ioDispatcher) {
            val returnVisit = dataSource.getItem(id)
            if (returnVisit is Success) Success(returnVisit.data)
            else Error(Exception("Error fetching from local"))
        }
    }

    override suspend fun insertItem(item: ReturnVisit) {
        return withContext(ioDispatcher) {
            dataSource.insertItem(item)
        }
    }

    override suspend fun updateItem(item: ReturnVisit) {
        return withContext(ioDispatcher) {
            dataSource.updateItem(item)
        }
    }

    override suspend fun deleteItem(id: String) {
        return withContext(ioDispatcher) {
            dataSource.deleteItem(id)
        }
    }

    override fun filterByData(date: Date): Result<LiveData<List<ReturnVisit>>> {
        val result = dataSource.filterByData(date)
        return if (result is Success) Success(result.data)
        else Error(Exception("Can't filter by date"))
    }
}
package com.martdev.android.ministrydiary.data

import androidx.lifecycle.LiveData
import java.util.*

interface GenericRepo<T> : GenericDataSource<T>{

    override fun getList(dateString: String): Result<LiveData<List<T>>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getItem(id: String): Result<T> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun insertItem(item: T) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun updateItem(item: T) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteItem(id: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun filterByData(date: Date): Result<LiveData<List<T>>> {
        return super.filterByData(date)
    }
}
package com.martdev.android.ministrydiary.data

import androidx.lifecycle.LiveData
import java.util.*

interface GenericDataSource<T> {

    fun getList(dateString: String = ""): Result<LiveData<List<T>>>

    suspend fun getItem(id: String): Result<T>

    suspend fun insertItem(item: T)

    suspend fun updateItem(item: T) {}

    suspend fun deleteItem(id: String)

    suspend fun deleteByDate(date: String) {}

    fun filterByData(date: Date): Result<LiveData<List<T>>> {
        return Result.Loading
    }
}
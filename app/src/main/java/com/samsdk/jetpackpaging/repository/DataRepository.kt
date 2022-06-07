package com.samsdk.jetpackpaging.repository

import com.samsdk.jetpackpaging.model.Data
import kotlinx.coroutines.delay

class DataRepository {
    private val remoteDataSource = (1..100).map {
        Data(
            title = "Item $it",
            description = "Description $it"
        )
    }

    suspend fun getItems(page: Int, pageSize: Int): Result<List<Data>> {
        delay(2000)
        val startingIndex = page * pageSize
        return if (startingIndex + page <= remoteDataSource.size) {
            Result.success(
                remoteDataSource.slice(startingIndex until startingIndex + pageSize)
            )
        } else Result.success(emptyList())
    }
}
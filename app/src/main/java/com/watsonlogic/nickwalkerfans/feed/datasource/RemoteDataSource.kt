package com.watsonlogic.nickwalkerfans.feed.datasource

import kotlinx.coroutines.flow.Flow

interface RemoteDataSource<T> {

    fun getContent(): Flow<T>
}
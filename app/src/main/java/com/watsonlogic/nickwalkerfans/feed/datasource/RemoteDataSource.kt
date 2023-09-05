package com.watsonlogic.nickwalkerfans.feed.datasource

interface RemoteDataSource<T> {

    suspend fun getContent(nextPage: String? = null): T
}
package com.watsonlogic.nickwalkerfans.feed.datasource

import com.watsonlogic.nickwalkerfans.feed.model.Content
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    fun getContent(): Flow<Content>
}
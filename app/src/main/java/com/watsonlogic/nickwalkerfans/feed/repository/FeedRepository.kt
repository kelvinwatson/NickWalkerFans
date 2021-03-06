package com.watsonlogic.nickwalkerfans.feed.repository

import com.watsonlogic.nickwalkerfans.feed.model.Content
import kotlinx.coroutines.flow.Flow

interface FeedRepository {

    fun getContent(): Flow<Content>

    fun setNextPageToken(nextPageToken: String)
}
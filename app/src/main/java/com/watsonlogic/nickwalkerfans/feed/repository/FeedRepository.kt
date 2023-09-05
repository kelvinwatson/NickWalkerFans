package com.watsonlogic.nickwalkerfans.feed.repository

import com.watsonlogic.nickwalkerfans.feed.model.Content
import kotlinx.coroutines.flow.Flow

interface FeedRepository {

   fun getContent(nextPage: String? = null): Flow<Content>
}
package com.watsonlogic.nickwalkerfans.feed.repository

import com.watsonlogic.nickwalkerfans.feed.model.PostsFeed
import com.watsonlogic.nickwalkerfans.feed.model.Result

interface FeedRepository {

    suspend fun getPostsFeed(): Result<PostsFeed>
}
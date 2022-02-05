package com.watsonlogic.nickwalkerfans.feed.repository

import com.watsonlogic.nickwalkerfans.feed.datasource.InstagramDataSource
import com.watsonlogic.nickwalkerfans.feed.datasource.YouTubeDataSource
import com.watsonlogic.nickwalkerfans.feed.model.Content
import com.watsonlogic.nickwalkerfans.feed.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn

class FeedRepositoryImpl(
    private val youTubeDataSource: YouTubeDataSource,
    private val instagramDataSource: InstagramDataSource
) : FeedRepository {

    override fun getPostsFeed(): Flow<Content> =
        combine(getYouTubeFeed(), getInstagramFeed()) { youTubeFeed, instagramFeed ->
            combineFeeds(youTubeFeed, instagramFeed)
        }.flowOn(Dispatchers.IO)

    private fun getYouTubeFeed(): Flow<Content> = youTubeDataSource.getContent()

    private fun getInstagramFeed(): Flow<Content> = instagramDataSource.getContent()

    private fun combineFeeds(vararg feeds: Content): Content {
        val postList = mutableListOf<Post>()
        feeds.forEach { postList.addAll(it.posts) }
        // todo handle error scenarios
        return Content(posts = postList, status = Content.Status.SUCCESS)
    }
}
package com.watsonlogic.nickwalkerfans.feed.repository

import com.watsonlogic.nickwalkerfans.feed.datasource.InstagramDataSource
import com.watsonlogic.nickwalkerfans.feed.datasource.YouTubeDataSource
import com.watsonlogic.nickwalkerfans.feed.model.Content
import com.watsonlogic.nickwalkerfans.feed.model.Response
import com.watsonlogic.nickwalkerfans.feed.model.Post
import com.watsonlogic.nickwalkerfans.feed.model.YouTubeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn

class FeedRepositoryImpl(
    private val youTubeDataSource: YouTubeDataSource,
    private val instagramDataSource: InstagramDataSource
) : FeedRepository {

    override fun getContent(): Flow<Content> =
        combine(getYouTubeFeed(), getInstagramFeed()) { youTubeFeed, instagramFeed ->
            combineFeeds(youTubeFeed, instagramFeed)
        }.flowOn(Dispatchers.IO)

    private fun getYouTubeFeed(): Flow<YouTubeResponse> = youTubeDataSource.getContent()

    private fun getInstagramFeed(): Flow<Response> = instagramDataSource.getContent()

    private fun combineFeeds(vararg feeds: Response): Content {
        println(feeds)
//        val postList = mutableListOf<Post>()
//        feeds.forEach { postList.addAll(it.posts) }
//        // todo handle error scenarios
        //todo amalgamate responses
        return Content(posts = listOf())
    }
}
package com.watsonlogic.nickwalkerfans.feed.repository

import com.watsonlogic.nickwalkerfans.feed.datasource.YouTubeDataSource
import com.watsonlogic.nickwalkerfans.feed.model.Content
import com.watsonlogic.nickwalkerfans.feed.model.Post
import com.watsonlogic.nickwalkerfans.feed.model.YouTubePost
import com.watsonlogic.nickwalkerfans.feed.model.YouTubePost.Companion.BASE_YOUTUBE_VIDEO_URL
import com.watsonlogic.nickwalkerfans.feed.model.YouTubeResponse
import com.watsonlogic.nickwalkerfans.newsfeed.iso8601ToFormattedDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FeedRepositoryImpl(
    private val youTubeDataSource: YouTubeDataSource,
) : FeedRepository {

    override fun getContent(nextPage: String?): Flow<Content> = flow {
        emit(getYouTubeFeed(nextPage).toContent())
    }

    private suspend fun getYouTubeFeed(nextPage: String?): YouTubeResponse =
        youTubeDataSource.getContent(nextPage)

    private fun YouTubeResponse.toContent(): Content {
        val youTubePosts = mutableListOf<Post>()
        items?.forEach {
            youTubePosts.add(
                YouTubePost(
                    channelId = it.snippet?.channelId,
                    id = it.id?.videoId.toString(),
                    title = it.snippet?.title,
                    description = it.snippet?.description,
                    url = "${BASE_YOUTUBE_VIDEO_URL}${it.id?.videoId}",
                    imageUrl = it.snippet?.thumbnails?.high?.url, // choose high def by default
                    publishDateTime = it.snippet?.publishTime.iso8601ToFormattedDate()
                )
            )
        }
        return Content(
            posts = youTubePosts,
            youTubeNextPageToken = nextPageToken
        )
    }
}
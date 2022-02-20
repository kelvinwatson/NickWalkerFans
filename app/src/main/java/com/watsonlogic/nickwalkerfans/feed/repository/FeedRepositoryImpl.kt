package com.watsonlogic.nickwalkerfans.feed.repository

import com.watsonlogic.nickwalkerfans.feed.datasource.InstagramDataSource
import com.watsonlogic.nickwalkerfans.feed.datasource.YouTubeDataSource
import com.watsonlogic.nickwalkerfans.feed.model.*
import com.watsonlogic.nickwalkerfans.feed.model.YouTubePost.Companion.BASE_YOUTUBE_VIDEO_URL
import com.watsonlogic.nickwalkerfans.newsfeed.iso8601ToFormattedDate
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

    override fun setNextPageToken(nextPageToken: String) =
        youTubeDataSource.setNextPageToken(nextPageToken)

    private fun getYouTubeFeed(): Flow<YouTubeResponse> = youTubeDataSource.getContent()

    private fun getInstagramFeed(): Flow<Response> = instagramDataSource.getContent()

    private fun combineFeeds(vararg feeds: Response): Content {
        val youTubePosts = mutableListOf<Post>()
        var youTubeNextPageToken: String? = null
        feeds.forEach { response ->
            when (response) {
                is YouTubeResponse -> {
                    response.items?.forEach {
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
                    youTubeNextPageToken = response.nextPageToken
                }
            }
        }
        return Content(
            posts = youTubePosts,
            youTubeNextPageToken = youTubeNextPageToken
        )  // todo amalgamate responses
    }

    companion object {

        // TODO
        private const val NULL_ID = "null"
    }
}
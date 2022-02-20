package com.watsonlogic.nickwalkerfans.feed.remote

import com.watsonlogic.nickwalkerfans.BuildConfig
import com.watsonlogic.nickwalkerfans.feed.model.YouTubeResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class YouTubeApiServiceImpl(
    private val client: HttpClient = KtorSingletons.httpClient
) : YouTubeApiService {

    /**
     * Gets the first page of results from the YouTube search.list API.
     *
     * Sources:
     *   https://developers.google.com/youtube/v3/docs/search/list?apix=true&apix_params=%7B%22part%22%3A%5B%22snippet%22%5D%2C%22channelId%22%3A%22UCotK4Y3BtGTdt7qzFR3vI1A%22%2C%22order%22%3A%22viewCount%22%2C%22type%22%3A%5B%22video%22%5D%2C%22videoDefinition%22%3A%22high%22%7D
     *   https://developers.google.com/youtube/v3/docs/search/list#usage
     */
    override suspend fun getYouTubeSnippets(nextPageToken: String?): YouTubeResponse {
        val response = client.get<YouTubeResponse>(ApiRoutes.YOU_TUBE_SEARCH_URL) {
            method = HttpMethod.Get
            parameter(QUERY_KEY_PART, QUERY_VALUE_SNIPPET)
            parameter(QUERY_KEY_CHANNEL_ID, QUERY_VALUE_CHANNEL_ID)
            parameter(QUERY_KEY_ORDER, QUERY_VALUE_ORDER)
            parameter(QUERY_KEY_TYPE, QUERY_VALUE_TYPE)
            parameter(QUERY_KEY_VIDEO_DEFINITION, QUERY_VALUE_VIDEO_DEFINITION)
            parameter(QUERY_KEY_API_KEY, BuildConfig.YOUTUBE_API_KEY)
            nextPageToken?.run {
                parameter("pageToken", nextPageToken)
            }
        }
        // https://ktor.io/docs/client.html#response
        return response
    }

    companion object {
        const val QUERY_KEY_PART = "part"
        const val QUERY_VALUE_SNIPPET = "snippet"

        const val QUERY_KEY_CHANNEL_ID = "channelId"
        const val QUERY_VALUE_CHANNEL_ID = "UCotK4Y3BtGTdt7qzFR3vI1A"

        const val QUERY_KEY_ORDER = "order"
        const val QUERY_VALUE_ORDER = "date"

        const val QUERY_KEY_TYPE = "type"
        const val QUERY_VALUE_TYPE = "video"

        const val QUERY_KEY_VIDEO_DEFINITION = "videoDefinition"
        const val QUERY_VALUE_VIDEO_DEFINITION = "high"

        const val QUERY_KEY_API_KEY = "key"
    }
}
package com.watsonlogic.nickwalkerfans.feed.remote

import android.util.Log
import com.watsonlogic.nickwalkerfans.BuildConfig
import com.watsonlogic.nickwalkerfans.feed.model.YouTubeResponse
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*

class YouTubeApiServiceImpl(
    private val client: HttpClient = HttpClient {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(ResponseObserver) {
            onResponse { response ->
                Log.d("HTTP status:", "${response.status.value}")
            }
        }
        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }
) : YouTubeApiService {

    /**
     * Source: https://developers.google.com/youtube/v3/docs/search/list?apix=true&apix_params=%7B%22part%22%3A%5B%22snippet%22%5D%2C%22channelId%22%3A%22UCotK4Y3BtGTdt7qzFR3vI1A%22%2C%22order%22%3A%22viewCount%22%2C%22type%22%3A%5B%22video%22%5D%2C%22videoDefinition%22%3A%22high%22%7D
     */
    override suspend fun getYouTubeSnippets(): YouTubeResponse {
        val response = client.get<YouTubeResponse>(ApiRoutes.YOU_TUBE_SEARCH_URL) {
            method = HttpMethod.Get
            parameter("part", "snippet")
            parameter("channelId", "UCotK4Y3BtGTdt7qzFR3vI1A")
            parameter("order", "viewCount")
            parameter("type", "video")
            parameter("videoDefinition", "high")
            parameter("key", BuildConfig.GOOGLE_MAPS_API_KEY)
        }
        // https://ktor.io/docs/client.html#response
        return response
    }
}
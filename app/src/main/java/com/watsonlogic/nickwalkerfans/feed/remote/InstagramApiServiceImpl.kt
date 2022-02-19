package com.watsonlogic.nickwalkerfans.feed.remote

import com.watsonlogic.nickwalkerfans.feed.model.InstagramResponse
import io.ktor.client.*

class InstagramApiServiceImpl(
    private val client: HttpClient = KtorSingletons.httpClient
) : InstagramApiService {

    override suspend fun getYouTubeSnippets(): InstagramResponse {
        TODO("Not yet implemented")
    }
}
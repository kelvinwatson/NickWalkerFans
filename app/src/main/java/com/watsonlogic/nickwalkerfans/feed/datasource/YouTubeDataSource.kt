package com.watsonlogic.nickwalkerfans.feed.datasource

import com.watsonlogic.nickwalkerfans.feed.model.YouTubeResponse
import com.watsonlogic.nickwalkerfans.feed.remote.YouTubeDataService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class YouTubeDataSource(private val apiService: YouTubeDataService) :
    RemoteDataSource<YouTubeResponse> {

    override suspend fun getContent(nextPage: String?): YouTubeResponse =
        withContext(Dispatchers.IO) {
            apiService.getYouTubeSnippets(nextPage)
        }
}
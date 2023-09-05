package com.watsonlogic.nickwalkerfans.feed.remote

import com.watsonlogic.nickwalkerfans.feed.model.YouTubeResponse

interface YouTubeDataService {
    suspend fun getYouTubeSnippets(page: String? = null): YouTubeResponse
}
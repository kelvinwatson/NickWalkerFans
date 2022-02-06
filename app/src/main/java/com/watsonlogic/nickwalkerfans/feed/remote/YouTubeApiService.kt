package com.watsonlogic.nickwalkerfans.feed.remote

import com.watsonlogic.nickwalkerfans.feed.model.YouTubeResponse

interface YouTubeApiService {
    suspend fun getYouTubeSnippets(): YouTubeResponse
}
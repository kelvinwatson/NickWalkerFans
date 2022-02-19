package com.watsonlogic.nickwalkerfans.feed.remote

import com.watsonlogic.nickwalkerfans.feed.model.InstagramResponse

interface InstagramApiService {
    suspend fun getYouTubeSnippets(): InstagramResponse
}
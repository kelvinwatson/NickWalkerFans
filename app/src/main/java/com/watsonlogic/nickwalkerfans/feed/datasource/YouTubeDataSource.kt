package com.watsonlogic.nickwalkerfans.feed.datasource

import com.watsonlogic.nickwalkerfans.feed.model.YouTubeResponse
import com.watsonlogic.nickwalkerfans.feed.remote.YouTubeApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class YouTubeDataSource(private val apiService: YouTubeApiService) :
    RemoteDataSource<YouTubeResponse> {
    override fun getContent(): Flow<YouTubeResponse> = flow {
        emit(apiService.getYouTubeSnippets())
    }
}
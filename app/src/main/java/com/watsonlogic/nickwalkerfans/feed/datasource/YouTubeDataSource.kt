package com.watsonlogic.nickwalkerfans.feed.datasource

import com.watsonlogic.nickwalkerfans.feed.model.YouTubeResponse
import com.watsonlogic.nickwalkerfans.feed.remote.YouTubeApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class YouTubeDataSource(private val apiService: YouTubeApiService) :
    RemoteDataSource<YouTubeResponse> {

    private val nextPageToken = MutableStateFlow<String?>(null)

    fun setNextPageToken(nextPageToken: String) {
        this.nextPageToken.value = nextPageToken
    }

    override fun getContent(): Flow<YouTubeResponse> = flow {
        nextPageToken.collect {
            val response = apiService.getYouTubeSnippets(it)
            emit(response)
        }
    }
}
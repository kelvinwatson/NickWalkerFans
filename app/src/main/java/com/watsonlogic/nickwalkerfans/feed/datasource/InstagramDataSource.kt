package com.watsonlogic.nickwalkerfans.feed.datasource

import com.watsonlogic.nickwalkerfans.feed.model.InstagramResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InstagramDataSource : RemoteDataSource<InstagramResponse> {
    override fun getContent(): Flow<InstagramResponse> = flow {

        //fixme placeholder
        emit(InstagramResponse("some string! haha!"))

    }
}
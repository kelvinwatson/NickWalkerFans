package com.watsonlogic.nickwalkerfans.feed.model

import kotlinx.serialization.Serializable

/**
 * Source: https://developers.google.com/youtube/v3/docs/search/list?apix=true&apix_params=%7B%22part%22%3A%5B%22snippet%22%5D%2C%22channelId%22%3A%22UCotK4Y3BtGTdt7qzFR3vI1A%22%2C%22order%22%3A%22viewCount%22%2C%22type%22%3A%5B%22video%22%5D%2C%22videoDefinition%22%3A%22high%22%7D
 */
@Serializable
data class YouTubeResponse(
    val kind: String? = null,
    val etag: String? = null,
    val nextPageToken: String? = null,
    val prevPageToken: String? = null,
    val regionCode: String? = null,
    val pageInfo: PageInfo? = null,
    val items: List<Item>? = null
) : Response {

    @Serializable
    data class PageInfo(val totalResults: Int? = null, val resultsPerPage: Int)

    @Serializable
    data class Item(
        val kind: String? = null,
        val etag: String? = null,
        val id: Id? = null,
        val snippet: Snippet? = null
    ) {
        @Serializable
        data class Id(val kind: String? = null, val videoId: String? = null)
    }

    @Serializable
    data class Snippet(
        val publishedAt: String? = null,
        val channelId: String? = null,
        val title: String? = null,
        val description: String? = null,
        val thumbnails: Thumbnails? = null,
        val channelTitle: String? = null,
        val liveBroadcastContent: String? = null,
        val publishTime: String? = null
    ) {
        @Serializable
        data class Thumbnails(
            val default: Default? = null,
            val medium: Medium? = null,
            val high: High? = null
        ) {
            @Serializable
            data class Default(
                val url: String? = null,
                val width: Int = 0,
                val height: Int = 0
            )

            @Serializable
            data class Medium(
                val url: String? = null,
                val width: Int = 0,
                val height: Int = 0
            )

            @Serializable
            data class High(
                val url: String? = null,
                val width: Int = 0,
                val height: Int = 0
            )
        }
    }
}



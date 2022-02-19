package com.watsonlogic.nickwalkerfans.feed.model

sealed class Post {
    abstract val id: String?// shouldn't be null once we have a service built
    abstract val title: String?
    abstract val subtitle: String?
    abstract val description: String?
    abstract val url: String?
    abstract val imageUrl: String?
    abstract val publishDateTime: String?
}

data class YouTubePost(
    val channelId: String? = null, // unique to YouTube post
    override val id: String? = null, // shouldn't be null once we have a service built
    override val title: String? = null,
    override val subtitle: String? = null,
    override val description: String? = null,
    override val url: String? = null,
    override val imageUrl: String? = null,
    override val publishDateTime: String? = null
) : Post() {
    companion object {
        const val BASE_YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v="
    }
}

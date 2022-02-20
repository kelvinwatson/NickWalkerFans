package com.watsonlogic.nickwalkerfans.feed.model

/**
 * UI representation of the list of posts.
 */
data class Content(
    val posts: List<Post> = emptyList(),
    val youTubeNextPageToken: String? = null
)

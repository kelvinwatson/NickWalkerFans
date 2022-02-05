package com.watsonlogic.nickwalkerfans.feed.model

data class Content(
    val posts: List<Post>,
    val status: Status? = null,
    val errorMessage: String? = null,
) {
    /**
     * Returns a flattened list of all posts contained in the feed.
     */
//    val allPosts: List<Post> = instagramPosts + youTubePosts

    enum class Status {
        SUCCESS, FAILURE
    }
}

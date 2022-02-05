package com.watsonlogic.nickwalkerfans.feed.model

data class PostsFeed(
    val instagramPosts: List<Post>,
    val youTubePosts: List<Post>
) {
    /**
     * Returns a flattened list of all posts contained in the feed.
     */
    val allPosts: List<Post> = instagramPosts + youTubePosts
}

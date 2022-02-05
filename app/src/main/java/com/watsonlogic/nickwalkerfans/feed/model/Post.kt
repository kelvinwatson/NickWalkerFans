package com.watsonlogic.nickwalkerfans.feed.model

data class Post(
    val id: String,
    val title: String? = null,
    val subtitle: String? = null,
    val url: String? = null,
    val imageUrl: String? = null
)

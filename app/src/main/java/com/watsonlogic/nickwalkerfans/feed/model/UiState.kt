package com.watsonlogic.nickwalkerfans.model

sealed class UiState {
    object Loading : UiState()
    object Empty : UiState()
    data class Ready(val content: FeedContent) : UiState()
    data class Error(val message: String? = null)
}


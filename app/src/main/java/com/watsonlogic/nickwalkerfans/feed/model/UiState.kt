package com.watsonlogic.nickwalkerfans.feed.model

sealed class UiState {
    object Loading : UiState()
    object Empty : UiState()
    data class Ready(val content: Content) : UiState()
    data class Error(val errorMessage: String): UiState()
}


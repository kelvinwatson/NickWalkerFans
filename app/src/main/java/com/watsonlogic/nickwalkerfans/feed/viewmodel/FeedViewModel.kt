package com.watsonlogic.nickwalkerfans.feed.viewmodel

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.watsonlogic.nickwalkerfans.R
import com.watsonlogic.nickwalkerfans.feed.model.UiState
import com.watsonlogic.nickwalkerfans.feed.repository.FeedRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
class FeedViewModel(
    private val resources: Resources,
    private val repository: FeedRepository
) : ViewModel() {

    private val _uiState: StateFlow<UiState> = repository.getContent()
        .mapLatest { content ->
            UiState.Ready(content)
        }.catch { cause ->
            UiState.Error(cause.message ?: resources.getString(R.string.error_generic))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = SUBSCRIBE_TIMEOUT_FOR_CONFIG_CHANGE),
            initialValue = UiState.Loading
        )
    val uiState: StateFlow<UiState>
        get() = _uiState

    class FeedViewModelFactory(
        private val resources: Resources,
        private val repository: FeedRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            FeedViewModel(resources, repository) as T
    }

    companion object {
        const val SUBSCRIBE_TIMEOUT_FOR_CONFIG_CHANGE = 5000L
    }
}

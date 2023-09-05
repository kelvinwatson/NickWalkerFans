package com.watsonlogic.nickwalkerfans

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.watsonlogic.nickwalkerfans.feed.datasource.YouTubeDataSource
import com.watsonlogic.nickwalkerfans.feed.model.Content
import com.watsonlogic.nickwalkerfans.feed.model.UiState
import com.watsonlogic.nickwalkerfans.feed.remote.YouTubeDataServiceImpl
import com.watsonlogic.nickwalkerfans.feed.repository.FeedRepositoryImpl
import com.watsonlogic.nickwalkerfans.feed.viewmodel.FeedViewModel
import com.watsonlogic.nickwalkerfans.ui.components.CircularIndeterminateProgressBar
import com.watsonlogic.nickwalkerfans.ui.theme.NickWalkerFansTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

class MainActivity : ComponentActivity() {
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NickWalkerFansTheme {
                // A surface container using the 'background' color from the theme
                App()
            }
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun App() {
    FeedScreen()
}

@ExperimentalCoroutinesApi
@Composable
fun FeedScreen(
    viewModel: FeedViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = FeedViewModel.FeedViewModelFactory(
            LocalContext.current.resources,
            FeedRepositoryImpl(
                YouTubeDataSource(YouTubeDataServiceImpl()),
            )
        )
    )
) {

    val lifecycleOwner = LocalLifecycleOwner.current

    /**
     * Scoped to lifecycle of this composable.
     */
    val lifecycleAwareUiStateFlow: Flow<UiState> = remember(viewModel.uiState, lifecycleOwner) {
        viewModel.uiState.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    /**
     * Delegated to [State.getValue].
     */
    val uiState: UiState by lifecycleAwareUiStateFlow.collectAsState(initial = UiState.Loading)

    FeedListBridge(viewModel, uiState)
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun FeedListBridge(
    viewModel: FeedViewModel,
    uiState: UiState,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {

    when (uiState) {
        is UiState.Loading -> {
            CircularIndeterminateProgressBar()
        }

        is UiState.Empty -> {
            // render empty state
        }

        is UiState.Error -> {
            // If the UI state contains an error, show snackbar
            // `LaunchedEffect` will cancel and re-launch if
            // `scaffoldState.snackbarHostState` changes
            val retryActionLabel = stringResource(id = R.string.retry)
            LaunchedEffect(scaffoldState.snackbarHostState) {
                // Show snackbar using a coroutine, when the coroutine is cancelled the
                // snackbar will automatically dismiss. This coroutine will cancel whenever
                // `state.hasError` is false, and only start when `state.hasError` is true
                // (due to the above if-check), or if `scaffoldState.snackbarHostState` changes.
                scaffoldState.snackbarHostState.showSnackbar(
                    message = uiState.errorMessage,
                    actionLabel = retryActionLabel
                )
            }
        }

        is UiState.Ready -> {
            val lazyListScrollState = rememberLazyListState()

            Scaffold(
                scaffoldState = scaffoldState,
                topBar = { AppBar() },
            ) { innerPadding ->

                FeedLazyColumn(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = viewModel,
                    content = uiState.content,
                    scrollState = lazyListScrollState
                )
            }
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun FeedLazyColumn(
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel,
    content: Content,
    scrollState: LazyListState
) {

    Column(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            state = scrollState
        ) {
            items(items = content.posts) { post ->
                com.watsonlogic.nickwalkerfans.ui.components.Card(post)
            }
            item {
                Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {
                    Button(onClick = {
                        viewModel.setPageToken(content.youTubeNextPageToken)
                    }) {
                        Text(stringResource(id = R.string.load_more))
                        Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "")
                    }
                }
            }
        }
    }

}
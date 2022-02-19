package com.watsonlogic.nickwalkerfans

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import coil.compose.rememberImagePainter
import com.watsonlogic.nickwalkerfans.feed.datasource.InstagramDataSource
import com.watsonlogic.nickwalkerfans.feed.datasource.YouTubeDataSource
import com.watsonlogic.nickwalkerfans.feed.model.Post
import com.watsonlogic.nickwalkerfans.feed.model.UiState
import com.watsonlogic.nickwalkerfans.feed.remote.YouTubeApiServiceImpl
import com.watsonlogic.nickwalkerfans.feed.repository.FeedRepositoryImpl
import com.watsonlogic.nickwalkerfans.feed.viewmodel.FeedViewModel
import com.watsonlogic.nickwalkerfans.ui.components.CircularIndeterminateProgressBar
import com.watsonlogic.nickwalkerfans.ui.theme.NickWalkerFansTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

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

@Composable
fun AppBar() {

    TopAppBar {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(id = R.drawable.nickwalker), contentDescription = "",
                modifier = Modifier
                    .scale(2.1f)
                    .offset(y = 15.dp)
            )

            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.ExtraBold)
            )
        }
    }
}

@Composable
fun ScrollToTopFab(scrollState: LazyListState) {

    val coroutineScope = rememberCoroutineScope()

    FloatingActionButton(onClick = {

        coroutineScope.launch {
            scrollState.animateScrollToItem(0)
        }
    }) {
        Icon(imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = "")
    }
}

@ExperimentalCoroutinesApi
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
                YouTubeDataSource(YouTubeApiServiceImpl()),
                InstagramDataSource()
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

    FeedListBridge(uiState)
}

@Composable
fun FeedListBridge(
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
                floatingActionButton = { ScrollToTopFab(lazyListScrollState) }
            ) {
                FeedLazyColumn(posts = uiState.content.posts, scrollState = lazyListScrollState)
            }
        }
    }
}

@Composable
fun FeedLazyColumn(
    posts: List<Post> = listOf(),
    scrollState: LazyListState
) {

    LazyColumn(
        modifier = Modifier.padding(vertical = 4.dp),
        state = scrollState
    ) {
        items(items = posts) { post ->
            Card(post)
        }
    }
}

@Composable
fun Card(post: Post) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        backgroundColor = MaterialTheme.colors.surface,

        ) {
        Column(
            modifier = Modifier.padding(bottom = 8.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = rememberImagePainter(
//                    data = "https://dummyimage.com/16:9x1080"
                    data = post.imageUrl,
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxSize()
                    .align(CenterHorizontally)
            )

            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp, 8.dp, 8.dp, 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    post.title?.run {
                        Text(
                            text = this,
                            style = MaterialTheme.typography.subtitle1,
                            modifier = Modifier.weight(70f),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Text(
                        text = "Dec 19, 2021",
                        style = MaterialTheme.typography.subtitle2,
                        modifier = Modifier.weight(25f),
                        textAlign = TextAlign.Right
                    )
                }
                post.description?.trim()?.run {
                    Text(
                        text = this,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NickWalkerFansTheme {
        App()
    }
}
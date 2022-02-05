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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.watsonlogic.nickwalkerfans.feed.datasource.InstagramDataSource
import com.watsonlogic.nickwalkerfans.feed.datasource.YouTubeDataSource
import com.watsonlogic.nickwalkerfans.feed.repository.FeedRepository
import com.watsonlogic.nickwalkerfans.feed.repository.FeedRepositoryImpl
import com.watsonlogic.nickwalkerfans.feed.viewmodel.FeedViewModel
import com.watsonlogic.nickwalkerfans.ui.theme.NickWalkerFansTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
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

@Composable
fun App() {

    FeedScreen()
}

@Composable
fun FeedScreen(
    viewModel: FeedViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = FeedViewModel.FeedViewModelFactory(
            FeedRepositoryImpl(
                YouTubeDataSource(),
                InstagramDataSource()
            )
        )
    )
) {

    val lazyListScrollState = rememberLazyListState()

    Scaffold(
        topBar = { AppBar() },
        floatingActionButton = { ScrollToTopFab(lazyListScrollState) }
    ) {
        NewsLazyList(scrollState = lazyListScrollState)
    }
}

@Composable
fun NewsLazyList(
    names: List<String> = listOf(
        "Apples",
        "Bananas",
        "Oranges",
        "Pears",
        "Peaches",
        "Eggplants"
    ),
    scrollState: LazyListState
) {

    LazyColumn(
        modifier = Modifier.padding(vertical = 4.dp),
        state = scrollState
    ) {
        items(items = names) { name ->
            Card(name)
        }
    }
}

@Composable
fun Card(name: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        backgroundColor = MaterialTheme.colors.surface,

        ) {
        Column {
            Image(
                painter = rememberImagePainter(
                    data = "https://dummyimage.com/16:9x1080"
                ),
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
                modifier = Modifier
                    .height(180.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = name, style = MaterialTheme.typography.subtitle1)
                    Text(
                        text = "Description of the content",
                        style = MaterialTheme.typography.caption
                    )
                }
                Text(text = "Dec 19, 2021", style = MaterialTheme.typography.subtitle2)
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
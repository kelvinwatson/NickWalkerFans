package com.watsonlogic.nickwalkerfans.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.watsonlogic.nickwalkerfans.feed.model.Post

@Composable
fun Card(post: Post) {
    androidx.compose.material.Card(
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
                    data = post.imageUrl ?: "https://dummyimage.com/16:9x1080",
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally)
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

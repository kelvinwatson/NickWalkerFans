package com.watsonlogic.nickwalkerfans.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.watsonlogic.nickwalkerfans.App
import com.watsonlogic.nickwalkerfans.R
import com.watsonlogic.nickwalkerfans.feed.model.Post
import com.watsonlogic.nickwalkerfans.feed.model.YouTubePost
import com.watsonlogic.nickwalkerfans.ui.theme.NickWalkerFansTheme

@Composable
fun Card(
    post: Post,
    context: Context = LocalContext.current,
) {

    val webIntent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(post.url)) }

    androidx.compose.material.Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                context.startActivity(webIntent)
            },
        backgroundColor = MaterialTheme.colors.surface,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {

                Icon(
                    painter = painterResource(
                        id = if (isVideo(post)) R.drawable.ic_baseline_play_circle_filled_24 else
                            R.drawable.ic_baseline_photo_24
                    ),
                    contentDescription = null // decorative element
                )

                Column(
                    modifier = Modifier.fillMaxWidth().padding(start = 8.dp)
                ) {
                    Text(
                        text = post.title ?: "",
                        style = MaterialTheme.typography.subtitle1,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = post.publishDateTime,
                        style = MaterialTheme.typography.subtitle2,
                        textAlign = TextAlign.Right
                    )
                }
            }
        }
    }
}


private fun isVideo(post: Post) = post is YouTubePost

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NickWalkerFansTheme {
        Card(
            YouTubePost(
                channelId = "channelId",
                id = "id",
                title = "title",
                description = "desc",
                url = "https://youtube.com/",
                imageUrl = "https://dummyimage.com/16:9x1080",
                publishDateTime = "dateTime"
            )
        )
    }
}
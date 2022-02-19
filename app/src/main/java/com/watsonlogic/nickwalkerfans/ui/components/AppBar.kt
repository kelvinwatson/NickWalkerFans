package com.watsonlogic.nickwalkerfans

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

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
                style = MaterialTheme.typography.h5
            )
        }
    }
}

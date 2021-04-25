package progtips.vn.androidshowcase.main.welcome.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import progtips.vn.androidshowcase.R
import progtips.vn.androidshowcase.main.welcome.WelcomePage

@Composable
fun WelcomeItemUI(
    item: WelcomePage, position: Int, itemCount: Int,
    onClickSkip: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = item.title,
            modifier = Modifier.padding(top = 10.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_fingerprint),
            contentDescription = null,
            modifier = Modifier.height(180.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = item.description
        )

        Button(onClick = onClickSkip) {
            Text(text = "Skip")
        }

        Row(
            modifier = Modifier.padding(bottom = 10.dp)
        ) {
            for(i in 0 until itemCount) {
                Indicator(position == i)
            }
        }
    }
}

@Composable
fun Indicator(enabled: Boolean){
    val indicatorColor = if (enabled) MaterialTheme.colors.onSurface else Color.Gray

    Box(modifier = Modifier
        .wrapContentSize(Alignment.Center)
        .clip(CircleShape)) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(indicatorColor)
                .padding(horizontal = 5.dp)
        )
    }
}

@Preview
@Composable
fun PreviewWelcomeItemUI() {
    WelcomeItemUI(
        WelcomePage("Title 1", "Description 1"),
        1,
        3
    ) {}
}
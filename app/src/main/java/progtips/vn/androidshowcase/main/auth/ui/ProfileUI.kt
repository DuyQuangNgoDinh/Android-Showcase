package progtips.vn.androidshowcase.main.auth.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import progtips.vn.androidshowcase.R

@Composable
fun ProfileUI(
    username: String? = "",
    appName: String? = "",
    loggedInState: Boolean,
    onClickLogIn: () -> Unit,
    onClickLogOut: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        if (loggedInState) {
            ProfileInfoUI(username, onClickLogOut)
        } else {
            WelcomeUI(appName, onClickLogIn)
        }
    }
}

@Composable
fun ColumnScope.ProfileInfoUI(
    username: String? = "",
    onClickLogOut: () -> Unit
) {
    Text(
        text = "Hello $username"
    )
    Button(
        onClick = onClickLogOut,
        modifier = Modifier.align(Alignment.CenterHorizontally))
    {
        Text(text = stringResource(id = R.string.label_logout))
    }
}

@Composable
fun ColumnScope.WelcomeUI(
    appName: String? = "",
    onClickLogIn: () -> Unit
) {
    Text(
        text = "Welcome to $appName"
    )
    Button(
        onClick = onClickLogIn,
        modifier = Modifier.align(Alignment.CenterHorizontally)
    ) {
        Text(text = stringResource(id = R.string.label_login))
    }
}

@Preview
@Composable
fun DefaultPreview() {
    ProfileUI("Username", "App Name", false, {}, {})
}
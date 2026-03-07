package dk.chen.garbagev1.ui.features.settings

import dk.chen.garbagev1.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dk.chen.garbagev1.ui.components.ThemedPreviews
import dk.chen.garbagev1.ui.navigation.AppRoute
import dk.chen.garbagev1.ui.theme.theme.GarbageV1Theme
import kotlinx.serialization.Serializable

@Serializable
object Settings : AppRoute

@Composable
fun SettingsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.settings_screen_title),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@ThemedPreviews
@Composable
private fun SettingsScreenPreview() {
    GarbageV1Theme {
        SettingsScreen()
    }
}
package dk.chen.garbagev1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import dk.chen.garbagev1.domain.Theme
import dk.chen.garbagev1.ui.components.RequestNotificationPermission
import dk.chen.garbagev1.ui.features.settings.SettingsViewModel
import dk.chen.garbagev1.ui.navigation.MainNavigation
import dk.chen.garbagev1.ui.theme.theme.GarbageV1Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: SettingsViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val darkTheme = when (uiState.theme) {
                Theme.LIGHT -> false
                Theme.DARK -> true
                Theme.SYSTEM -> isSystemInDarkTheme()
            }

            // We call enableEdgeToEdge here, inside setContent, to recompose when the theme changes.
            // This ensures the status and navigation bar colors update dynamically.
            // The SystemBarStyle.auto() constructor requires us to explicitly provide the scrim
            // colors. We are using the library's default values to maintain standard behavior.
            enableEdgeToEdge(
                statusBarStyle = androidx.activity.SystemBarStyle.auto(
                    lightScrim = android.graphics.Color.TRANSPARENT,
                    darkScrim = android.graphics.Color.TRANSPARENT,
                ) { darkTheme },
                navigationBarStyle = androidx.activity.SystemBarStyle.auto(
                    lightScrim = android.graphics.Color.argb(
                        /* alpha = */0xe6,
                        /* red = */ 0xFF,
                        /* green = */ 0xFF,
                        /* blue = */ 0xFF
                    ),
                    darkScrim = android.graphics.Color.argb(
                        /* alpha = */ 0x80,
                        /* red = */ 0x1b,
                        /* green = */ 0x1b,
                        /* blue = */ 0x1b
                    ),
                ) { darkTheme },
            )
            GarbageV1Theme() {
                RequestNotificationPermission()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavigation()
                }
            }
        }
    }
}
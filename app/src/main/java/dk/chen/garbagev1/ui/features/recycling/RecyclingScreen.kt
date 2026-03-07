package dk.chen.garbagev1.ui.features.recycling

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dk.chen.garbagev1.ui.navigation.AppRoute
import kotlinx.serialization.Serializable

@Serializable
object Bins : AppRoute

@Composable
fun RecyclingScreen(
    modifier: Modifier = Modifier,
    viewModel: BinsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    BinsScreen(uiState = uiState, uiEvents = viewModel.uiEvents, modifier = modifier)
}
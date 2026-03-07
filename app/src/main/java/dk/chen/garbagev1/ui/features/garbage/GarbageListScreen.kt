package dk.chen.garbagev1.ui.features.garbage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dk.chen.garbagev1.R
import dk.chen.garbagev1.domain.Item
import dk.chen.garbagev1.domain.fullDescription
import dk.chen.garbagev1.ui.components.ItemOrNullProvider
import dk.chen.garbagev1.ui.components.ThemedPreviews
import dk.chen.garbagev1.ui.components.previewGarbageList
import dk.chen.garbagev1.ui.navigation.AppRoute
import dk.chen.garbagev1.ui.navigation.NestedGraph
import dk.chen.garbagev1.ui.theme.theme.GarbageV1Theme
import kotlinx.serialization.Serializable

@Serializable
object GarbageGraph

@Serializable
object GarbageSearch : NestedGraph {
    override val startDestination: AppRoute
        get() = SortingList()
}

@Serializable
data class SortingList(val itemId: String? = null) : AppRoute

@Serializable
object GarbageList

@Serializable
object ListGroup

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GarbageListScreen(
    onNavigate: (GarbageListViewModel.NavigationEvent) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GarbageListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.navigationEvents.collect {
            onNavigate(it)
        }
    }

    GarbageListScreen(
        modifier = modifier,
        uiState = uiState,
        uiEvents = viewModel.uiEvents
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GarbageListScreen(
    uiState: GarbageListViewModel.UiState,
    uiEvents: GarbageListViewModel.UiEvents,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                navigationIcon = {
                    IconButton(onClick = uiEvents::onUpClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Button(onClick = uiEvents::onAddItemClick) {
                    Text(text = "Add item")
                }
            }

            items(uiState.garbageList) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = item.fullDescription())
                    IconButton(onClick = { uiEvents.onEditItemClick(item) }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit item"
                        )
                    }
                }
            }
        }
    }
}

@ThemedPreviews
@Composable
fun GarbageListScreenPreview(@PreviewParameter(provider = ItemOrNullProvider::class) itemOrNull: Item?) {
    GarbageV1Theme {
        GarbageListScreen(
            modifier = Modifier,
            uiState = GarbageListViewModel.UiState(
                garbageList = previewGarbageList(),
            ),
            uiEvents = object : GarbageListViewModel.UiEvents {
                override fun onAddItemClick() {}
                override fun onEditItemClick(item: Item) {}
                override fun onUpClick() {}
            },
        )
    }
}
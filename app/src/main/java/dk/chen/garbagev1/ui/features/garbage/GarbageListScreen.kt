package dk.chen.garbagev1.ui.features.garbage

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import dk.chen.garbagev1.R
import dk.chen.garbagev1.domain.Item
import dk.chen.garbagev1.ui.components.GarbageTopAppBar
import dk.chen.garbagev1.ui.components.ItemOrNullProvider
import dk.chen.garbagev1.ui.components.NavigationType
import dk.chen.garbagev1.ui.components.ThemedPreviews
import dk.chen.garbagev1.ui.components.previewBins
import dk.chen.garbagev1.ui.components.previewGarbageList
import dk.chen.garbagev1.ui.navigation.AppRoute
import dk.chen.garbagev1.ui.navigation.NestedGraph
import dk.chen.garbagev1.ui.theme.theme.GarbageV1Theme
import kotlinx.serialization.Serializable
import java.io.File
import android.Manifest

@Serializable
object GarbageGraph : NestedGraph {
    override val startDestination: AppRoute
        get() = SortingSearch
}

@Serializable
object SortingSearch : AppRoute

@Serializable
object AffaldKbh : AppRoute

@Serializable
data class SortingList(val itemId: String? = null) : AppRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GarbageListScreen(
    onNavigate: (GarbageListViewModel.NavigationEvent) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GarbageListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.navigationEvents.collect {
            onNavigate(it)
        }
    }

    GarbageListScreen(uiState = uiState, uiEvents = viewModel.uiEvents, modifier = modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GarbageListScreen(
    uiState: GarbageListViewModel.UiState,
    uiEvents: GarbageListViewModel.UiEvents,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            GarbageTopAppBar(
                titleRes = R.string.app_name,
                navigationType = NavigationType.BACK,
                onUpClick = uiEvents::onUpClick
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = uiEvents::onAddItemClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_button_label)
                )
            }
        }
    ) { contentPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues = contentPadding)
        ) {
            BinPhotoSection(modifier = Modifier.padding(16.dp))

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(uiState.garbageList) { item ->
                    val bin = uiState.bins.find { it.name.equals(item.where, ignoreCase = true) }
                    ListItem(
                        item = item,
                        imageUrl = bin?.imageUrl,
                        binColor = bin?.binColor,
                        onItemClick = { uiEvents.onEditItemClick(item) }
                    )
                }
            }
        }
    }

    uiState.selectedItem?.let {
        DetailsSheet(
            item = it,
            isWhatError = uiState.isWhatError,
            isWhereError = uiState.isWhereError,
            showDeleteConfirmation = uiState.showDeleteConfirmation,
            uiEvents = uiEvents
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListItem(item: Item, imageUrl: String?, binColor: Color?, onItemClick: () -> Unit) {
    Card(
        onClick = onItemClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(weight = 1f)) {
                Text(
                    text = item.what.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = binColor ?: MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${item.what} should be placed in ${item.where}",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Thin),
                    color = binColor ?: MaterialTheme.colorScheme.onSurface
                )
            }

            // TODO Add shop logo
            AsyncImage(
                model = imageUrl,
                contentDescription = "Bin Logo",
                modifier = Modifier.size(48.dp),
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                error = painterResource(R.drawable.ic_launcher_background)
            )

            IconButton(onClick = onItemClick) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(id = R.string.edit_item_title)
                )
            }
        }
    }
}

@ThemedPreviews
@Composable
fun GarbageListScreenPreview(@PreviewParameter(provider = ItemOrNullProvider::class) itemOrNull: Item?) {
    GarbageV1Theme {
        GarbageListScreen(
            uiState = GarbageListViewModel.UiState(
                garbageList = previewGarbageList(),
                bins = previewBins(),
                selectedItem = itemOrNull,
                isWhatError = false,
                isWhereError = false,
                showDeleteConfirmation = false,
            ),
            uiEvents = object : GarbageListViewModel.UiEvents {
                override fun onWhatChange(what: String) {}
                override fun onWhereChange(where: String) {}
                override fun onSaveClick(): Boolean {
                    return true
                }
                override fun onDeleteClick() {}
                override fun onConfirmDelete() {}
                override fun onDismissDeleteConfirmation() {}
                override fun onDismissDetails() {}
                override fun onAddItemClick() {}
                override fun onEditItemClick(item: Item) {}
                override fun onUpClick() {}
            }
        )
    }
}


@Composable
fun BinPhotoSection(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var tempUri by remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            imageUri = tempUri
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val file = File(context.cacheDir, "garbage_memo_${System.currentTimeMillis()}.jpg")
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                file
            )
            tempUri = uri
            cameraLauncher.launch(uri)
        }
    }


    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (imageUri != null) {
            AsyncImage(
                model = imageUri,
                contentDescription = "Garbage Memo",
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = { imageUri = null }) {
                Text("Clear Photo", color = MaterialTheme.colorScheme.error)
            }
        } else {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Unsure where it goes? Snap a memo!",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { permissionLauncher.launch(Manifest.permission.CAMERA) }) {
                        Text("Take a Photo")
                    }
                }
            }
        }
    }
}
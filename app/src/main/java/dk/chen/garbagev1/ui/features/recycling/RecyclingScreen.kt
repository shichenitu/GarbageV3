package dk.chen.garbagev1.ui.features.recycling

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dk.chen.garbagev1.ui.navigation.AppRoute
import kotlinx.serialization.Serializable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dk.chen.garbagev1.ui.components.GarbageTopAppBar
import dk.chen.garbagev1.ui.components.NavigationType
import dk.chen.garbagev1.R
import dk.chen.garbagev1.domain.Bin
import dk.chen.garbagev1.domain.RecyclingStation
import dk.chen.garbagev1.ui.components.BinOrNullProvider
import dk.chen.garbagev1.ui.components.ThemedPreviews
import dk.chen.garbagev1.ui.components.previewBins
import dk.chen.garbagev1.ui.theme.theme.GarbageV1Theme

@Serializable
object Bins : AppRoute

@Composable
fun RecyclingScreen(
    modifier: Modifier = Modifier,
    viewModel: RecyclingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    BinsScreen(uiState = uiState, uiEvents = viewModel.uiEvents, modifier = modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BinsScreen(
    uiState: RecyclingViewModel.UiState,
    uiEvents: RecyclingViewModel.UiEvents,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            GarbageTopAppBar(
                titleRes = R.string.bins_near_you_title,
                navigationType = NavigationType.NONE
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                Text(
                    text = "Your Bins",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )
                HorizontalUncontainedCarousel(
                    state = rememberCarouselState { uiState.bins.count() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    itemWidth = 186.dp,
                    itemSpacing = 8.dp,
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) { i ->
                    val bin = uiState.bins[i]
                    Card(
                        modifier = Modifier
                            .fillMaxHeight()
                            .clip(shape = MaterialTheme.shapes.extraLarge)
                            .clickable { uiEvents.onBinSelected(bin) }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AsyncImage(
                                model = bin.imageUrl,
                                contentDescription = bin.name,
                                modifier = Modifier.weight(1f),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = bin.name,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Recycling Stations (API)",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            items(
                uiState.stations
            ) { station ->
                StationCard(station = station)
            }
        }
    }

    uiState.selectedBin?.let {
        BinDetailsSheet(
            bin = it,
            onDismiss = uiEvents::onDismissBinDetails
        )
    }
}

@Composable
private fun StationCard(station: RecyclingStation) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = station.name, style = MaterialTheme.typography.titleMedium)
            Text(text = station.address, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Badge(
                    containerColor = if (station.status == "Aktiv")
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.error
                ) {
                    Text(text = station.status, color = MaterialTheme.colorScheme.onPrimary)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = station.category,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@ThemedPreviews
@Composable
private fun BinsScreenPreview(@PreviewParameter(provider = BinOrNullProvider::class) binOrNull: Bin?) {
    GarbageV1Theme {
        BinsScreen(
            uiState = RecyclingViewModel.UiState(
                bins = previewBins(),
                selectedBin = binOrNull
            ),
            uiEvents = object : RecyclingViewModel.UiEvents {
                override fun onBinSelected(bin: Bin) {}
                override fun onDismissBinDetails() {}
            }
        )
    }
}

package dk.chen.garbagev1.ui.features.recycling

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.chen.garbagev1.domain.Bin
import dk.chen.garbagev1.domain.BinRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.annotation.concurrent.Immutable
import javax.inject.Inject
import dk.chen.garbagev1.domain.RecyclingStation
import dk.chen.garbagev1.domain.RecyclingStationRepository

@HiltViewModel
class RecyclingViewModel @Inject constructor(
    private val binRepository: BinRepository,
    private val recyclingStationRepository: RecyclingStationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(value = UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            binRepository.getBins().collect { bins ->
                println("DEBUG: Received ${bins.size} bins from Firestore")
                _uiState.update { it.copy(bins = bins) }
            }
        }

        viewModelScope.launch {
            recyclingStationRepository.getStations().collect { stations ->
                _uiState.update { it.copy(stations = stations) }
            }
        }
    }

    val uiEvents: UiEvents = object : UiEvents {
        override fun onBinSelected(bin: Bin) {
            _uiState.update { it.copy(selectedBin = bin) }
        }

        override fun onDismissBinDetails() {
            _uiState.update { it.copy(selectedBin = null) }
        }

        override fun onTrackRecyclingClick(bin: Bin) {
            viewModelScope.launch {
                binRepository.updateBinPickupTime(bin.name, System.currentTimeMillis())
            }
        }

        override fun onEnableGeofencingClick() {
            _uiState.update { it.copy(showBackgroundPermission = true) }
        }

        override fun onBackgroundPermissionHandled(isGranted: Boolean) {
            _uiState.update { it.copy(showBackgroundPermission = false) }

            if (isGranted) {
                println("DEBUG: Background Location Permission Granted!")
            }
        }
    }

    data class UiState(
        val bins: List<Bin> = emptyList(),
        val stations: List<RecyclingStation> = emptyList(),
        val selectedBin: Bin? = null,
        val showBackgroundPermission: Boolean = false
    )

    @Immutable
    interface UiEvents {
        fun onBinSelected(bin: Bin)
        fun onDismissBinDetails()
        fun onTrackRecyclingClick(bin: Bin)
        fun onEnableGeofencingClick()
        fun onBackgroundPermissionHandled(isGranted: Boolean)
    }
}
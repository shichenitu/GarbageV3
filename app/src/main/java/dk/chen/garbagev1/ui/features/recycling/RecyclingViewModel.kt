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
    }

    data class UiState(
        val bins: List<Bin> = emptyList(),
        val stations: List<RecyclingStation> = emptyList(),
        val selectedBin: Bin? = null
    )

    @Immutable
    interface UiEvents {
        fun onBinSelected(bin: Bin)
        fun onDismissBinDetails()
    }
}
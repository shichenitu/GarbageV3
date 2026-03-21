package dk.chen.garbagev1.domain

import kotlinx.coroutines.flow.Flow

interface RecyclingStationRepository {
    fun getStations(): Flow<List<RecyclingStation>>
}
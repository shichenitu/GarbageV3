package dk.chen.garbagev1.data

import com.google.firebase.firestore.FirebaseFirestore
import dk.chen.garbagev1.data.remote.RecyclingStationApiService
import dk.chen.garbagev1.domain.RecyclingStation
import dk.chen.garbagev1.domain.RecyclingStationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecyclingStationRepositoryImpl @Inject constructor(
    private val apiService: RecyclingStationApiService
) : RecyclingStationRepository {
    override fun getStations(): Flow<List<RecyclingStation>> = flow {
        try {
            val response = apiService.getRecyclingStations()

            println("DEBUG: Raw API Data Records - ${response.result.records}")

            val stations = response.result.records.map { dto ->
                dto.toDomain()
            }

            stations.forEach { station ->
                FirebaseFirestore.getInstance()
                    .collection("stations")
                    .document(station.id)
                    .set(station)
            }

            emit(stations)
        } catch (e: Exception) {
            println("NETWORK_ERROR: ${e.message}")
            emit(emptyList())
        }
    }
}

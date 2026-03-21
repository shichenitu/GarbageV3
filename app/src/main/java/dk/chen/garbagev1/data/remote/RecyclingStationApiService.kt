package dk.chen.garbagev1.data.remote

import dk.chen.garbagev1.data.RecyclingStationResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RecyclingStationApiService {
    @GET("datastore_search")
    suspend fun getRecyclingStations(
        @Query("resource_id") resourceId: String = "3fa9b19e-b4ad-4f1e-a535-876879f0d051",
        @Query("limit") limit: Int = 100
    ): RecyclingStationResponse
}
package dk.chen.garbagev1.core.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dk.chen.garbagev1.core.NotificationHelper
import dk.chen.garbagev1.domain.BinRepository
import dk.chen.garbagev1.domain.RecyclingStationRepository
import kotlinx.coroutines.flow.first
import kotlin.collections.filter
import kotlin.collections.first
import kotlin.collections.isNotEmpty

@HiltWorker
class SmartGeofenceWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val binRepository: BinRepository,
    private val stationRepository: RecyclingStationRepository,
    private val notificationHelper: NotificationHelper
) : CoroutineWorker(appContext = context, params = workerParams) {

    override suspend fun doWork(): Result {
        // 1. Get the bin name passed from the GeofenceReceiver
        val binName = inputData.getString(key = "bin_name") ?: return Result.failure()

        // 2. Fetch the current bin list
        val stations = stationRepository.getStations().first()
        val bins = binRepository.getBins().first()

        val currentStation = stations.find { it.name == binName } ?: return Result.success()

        // 3. Filter the bins that need to be cleaned
        val overdueBins = bins.filter { bin ->
            val isAccepted = currentStation.bins.contains(bin.name)
            val isOverdue = bin.lastPickupTime != 0L &&
                    (System.currentTimeMillis() - bin.lastPickupTime) > 7 * 24 * 3600 * 1000L
            isAccepted && isOverdue
        }

        // 4. Show the notification
        if (overdueBins.isNotEmpty()) {
            val sampleBin = overdueBins.first().name

            // TODO use the NotificationHelper to show a notification
            // TODO HINT: notificationId = shopName.hashCode()

            notificationHelper.showNotification(
                context = context,
                title = "Recycling Opportunity!",
                message = "You are near $binName. It's a great time to empty your: $sampleBin!",
                notificationId = binName.hashCode()
            )

        } else {
            Log.d(/* tag = */ "SmartGeofenceWorker", "At $binName, but no overdue matching bins. Staying quiet.")
        }

        return Result.success()
    }
}
package dk.chen.garbagev1.data

import androidx.compose.ui.graphics.Color
import dk.chen.garbagev1.domain.Item
import dk.chen.garbagev1.domain.Bin
import java.util.UUID
import androidx.core.graphics.toColorInt
import dk.chen.garbagev1.domain.RecyclingStation
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemDto(
    val id: String = UUID.randomUUID().toString(),
    val what: String = "",
    val where: String = ""
)

@Serializable
data class BinDto(
    val name: String = "",
    val imageUrl: String = "",
    val binColor: String = "",
    val lastPickupTime: Long = 0L
)

@Serializable
data class RecyclingStationResponse(
    val result: RecyclingResultDto
)

@Serializable
data class RecyclingResultDto(
    val records: List<RecyclingStationDto>
)

@Serializable
data class RecyclingStationDto(
    @SerialName("_id") val id: Int,
    @SerialName("navn") val name: String = "Unknown Station",
    @SerialName("kategori") val category: String = "General",
    @SerialName("adresse") val address: String = "No address available",
    @SerialName("status") val status: String = "Unknown",
    val bins: List<String> = emptyList(),
    @SerialName("latitude") val latitude: Double = 0.0,
    @SerialName("longitude") val longitude: Double = 0.0
)

fun RecyclingStationDto.toDomain(): RecyclingStation = RecyclingStation(
    id = this.id.toString(),
    name = this.name,
    address = this.address,
    status = this.status,
    category = this.category,
    bins = this.bins,
    latitude = this.latitude,
    longitude = this.longitude
)

fun BinDto.toBin(): Bin = Bin(
    name = this.name,
    imageUrl = this.imageUrl,
    binColor = Color(color = this.binColor.toColorInt()),
    lastPickupTime = this.lastPickupTime
)

fun ItemDto.toItem(): Item = Item(
    id = this.id,
    what = this.what,
    where = this.where
)

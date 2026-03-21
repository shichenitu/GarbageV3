package dk.chen.garbagev1.data

import androidx.compose.ui.graphics.Color
import dk.chen.garbagev1.domain.Item
import dk.chen.garbagev1.domain.Bin
import java.util.UUID
import androidx.core.graphics.toColorInt
import dk.chen.garbagev1.data.database.ItemEntity
import dk.chen.garbagev1.data.database.BinEntity
import dk.chen.garbagev1.domain.RecyclingStation
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemDto(
    val id: String = UUID.randomUUID().toString(),
    val what: String,
    val where: String
)

@Serializable
data class BinDto(
    val name: String,
    val imageUrl: String,
    val binColor: String
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
    @SerialName("Navn") val name: String,
    @SerialName("Kategori") val category: String,
    @SerialName("Adresse") val address: String,
    @SerialName("Status") val status: String,
    val bins: List<String>,
    val latitude: Double,
    val longitude: Double
)

fun RecyclingStationDto.toDomain(): RecyclingStation = RecyclingStation(
    id = this.id.toString(),
    name = this.name,
    category = this.category,
    address = this.address,
    status = this.status,
    bins = this.bins,
    latitude = this.latitude,
    longitude = this.longitude
)

fun BinDto.toBin(): Bin = Bin(
    name = this.name, imageUrl = this.imageUrl, binColor = Color(
        color = this.binColor.toColorInt()
    )
)

fun ItemDto.toItem(): Item = Item(
    id = this.id,
    what = this.what,
    where = this.where
)

fun ItemEntity.toItemDto() = ItemDto(
    id = id,
    what = what,
    where = where
)

fun BinEntity.toBinDto() = BinDto(
    name = name,
    imageUrl = imageUrl,
    binColor = binColor
)
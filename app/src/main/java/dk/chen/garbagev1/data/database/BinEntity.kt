package dk.chen.garbagev1.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bins")
data class BinEntity(
    @PrimaryKey
    val name: String,
    val imageUrl: String,
    val binColor: String
)


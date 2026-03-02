package dk.chen.garbagev1.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dk.chen.garbagev1.R
import dk.chen.garbagev1.domain.Item
import dk.chen.garbagev1.domain.ItemRepository
import dk.chen.garbagev1.domain.toDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context) : ItemRepository {

    private val _garbageSorting = MutableStateFlow<List<ItemDto>>(emptyList())

    init {
        try {
            val rawText = context.resources.openRawResource(R.raw.garbage)
                .bufferedReader().use { it.readText() }
            populateItems(rawText)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getSortingList(): Flow<List<Item>> {
        return _garbageSorting.map { dtoList ->
            dtoList.sortedByWhereAndWhat().map { it.toItem() }
        }
    }

    override fun getItem(id: String): Flow<Item?> {
        return _garbageSorting.map { dtoList ->
            dtoList.find { it.id == id }?.toItem()
        }
    }

    override fun addItem(item: Item) {
        val formattedItemDto =
            item.copy(what = item.what.toTitleCase(), where = item.where.toTitleCase()).toDto()

        _garbageSorting.update { currentList ->
            if (currentList.any { it.what == formattedItemDto.what && it.where == formattedItemDto.where }) {
                currentList
            } else {
                currentList + formattedItemDto
            }
        }
    }

    fun populateItems(rawText: String) {
        val allLines = rawText.split(Regex("\\r?\\n"))

        val newList = allLines
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .mapNotNull { line ->
                if (line.contains(", ")) {
                    val parts = line.split(", ", limit = 2)
                    ItemDto(
                        id = java.util.UUID.randomUUID().toString(),
                        what = parts[0].trim(),
                        where = parts[1].trim()
                    )
                } else {
                    null
                }
            }
        _garbageSorting.update { newList }
    }

    override fun removeItem(item: Item) {
        _garbageSorting.update { currentList ->
            currentList.filter { it.id != item.id }
        }
    }

    override fun updateItem(item: Item) {
        val formattedItemDto = item.copy(what = item.what.toTitleCase(), where = item.where.toTitleCase()).toDto()

        _garbageSorting.update { currentList ->
            currentList.map {
                if (it.id == formattedItemDto.id) {
                    formattedItemDto
                } else {
                    it
                }
            }
        }
    }

    private fun String.toTitleCase(): String {
        return this.trim().split(" ").joinToString(separator = " ") { word ->
            word.lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }
    }

    private fun List<ItemDto>.sortedByWhereAndWhat() =
        this.sortedWith(
            compareBy(
                { it.where.lowercase() },
                { it.what.lowercase() }
            )
        )
}
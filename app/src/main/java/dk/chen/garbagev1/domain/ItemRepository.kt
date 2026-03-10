package dk.chen.garbagev1.domain

import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    fun getGarbageList(): Flow<List<Item>>
    fun getItem(id: String): Flow<Item?>
    suspend fun addItem(item: Item)
    suspend fun removeItem(item: Item)
    suspend fun updateItem(item: Item)
    suspend fun getItemByWhat(what: String): Item?
}
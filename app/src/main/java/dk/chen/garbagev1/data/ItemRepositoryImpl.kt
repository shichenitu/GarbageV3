package dk.chen.garbagev1.data

import dk.chen.garbagev1.data.database.ItemDao
import dk.chen.garbagev1.data.database.ItemEntity
import dk.chen.garbagev1.domain.Item
import dk.chen.garbagev1.domain.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepositoryImpl @Inject constructor(
    private val itemDao: ItemDao
) : ItemRepository {

    override fun getGarbageList(): Flow<List<Item>> {
        return itemDao.getGarbageList().map { entityList ->
            entityList.map { it.toItem() }
        }
    }

    override fun getItem(id: String): Flow<Item?> {
        return itemDao.getItem(id = id).map { it?.toItemDto()?.toItem() }
    }

    override suspend fun addItem(item: Item) {
        val formattedItem =
            item.copy(what = item.what.toTitleCase(), where = item.where.toTitleCase())
        itemDao.insert(item = formattedItem.toEntity())
    }

    override suspend fun removeItem(item: Item) {
        itemDao.delete(id = item.id)
    }

    override suspend fun updateItem(item: Item) {
        val formattedItem =
            item.copy(what = item.what.toTitleCase(), where = item.where.toTitleCase())
        itemDao.update(item = formattedItem.toEntity())
    }

    override suspend fun getItemByWhat(what: String): Item? {
        return itemDao.getItemWhere(what.toTitleCase())
            .map { it?.toItem() }
            .first()
    }

    private fun ItemEntity.toItem() = Item(id = id, what = what, where = where)

    private fun Item.toEntity() = ItemEntity(id = id, what = what, where = where)

    private fun String.toTitleCase(): String {
        return this.trim().split(" ").joinToString(separator = " ") { word ->
            word.lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }
    }
}
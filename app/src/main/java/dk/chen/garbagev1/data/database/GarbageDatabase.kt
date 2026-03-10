package dk.chen.garbagev1.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dk.chen.garbagev1.data.ItemDto
import dk.chen.garbagev1.data.BinDto
import java.util.UUID
import javax.inject.Provider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [ItemEntity::class, BinEntity::class], version = 1)
abstract class GarbageDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun binDao(): BinDao

    class Callback(
        private val itemDaoProvider: Provider<ItemDao>,
        private val binDaoProvider: Provider<BinDao>
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db = db)
            CoroutineScope(context = Dispatchers.IO).launch {
                prepopulateItems()
                prepopulateBins()
            }
        }

        private suspend fun prepopulateItems() {
            val itemDao = itemDaoProvider.get()
            val initialItems = listOf(
                ItemDto(
                    id = UUID.randomUUID().toString(),
                    what = "Book",
                    where = "Paper",
                ),
                ItemDto(
                    id = UUID.randomUUID().toString(),
                    what = "Battery",
                    where = "Batteries",
                ),
                ItemDto(
                    id = UUID.randomUUID().toString(),
                    what = "Cables",
                    where = "Metal",
                ),
                ItemDto(
                    id = "deep-link-item",
                    what = "Can",
                    where = "Metal",
                ),
                ItemDto(
                    id = UUID.randomUUID().toString(),
                    what = "Cabbage",
                    where = "Food Waste",
                ),
                ItemDto(
                    id = UUID.randomUUID().toString(),
                    what = "magazine",
                    where = "paper",
                ),
                ItemDto(
                    id = UUID.randomUUID().toString(),
                    what = "milk carton",
                    where = "plastic",
                ),
                ItemDto(
                    id = UUID.randomUUID().toString(),
                    what = "teddy bears",
                    where = "daily waste",
                ),
                ItemDto(
                    id = UUID.randomUUID().toString(),
                    what = "musical instrument",
                    where = "wood",
                ),
                ItemDto(
                    id = UUID.randomUUID().toString(),
                    what = "carpets",
                    where = "bulky waste",
                ),
                ItemDto(
                    id = UUID.randomUUID().toString(),
                    what = "chips bag",
                    where = "other",
                ),
                ItemDto(
                    id = UUID.randomUUID().toString(),
                    what = "paint",
                    where = "chemical",
                ),
                ItemDto(
                    id = UUID.randomUUID().toString(),
                    what = "printer",
                    where = "electronics",
                ),
                ItemDto(
                    id = UUID.randomUUID().toString(),
                    what = "shoe box",
                    where = "cardboard",
                ),
                ItemDto(
                    id = UUID.randomUUID().toString(),
                    what = "jars",
                    where = "glass",
                ),
                ItemDto(
                    id = UUID.randomUUID().toString(),
                    what = "jeans",
                    where = "Textile Waste",
                ),
                ItemDto(
                    id = UUID.randomUUID().toString(),
                    what = "sofa",
                    where = "Bulky Waste",
                ),
                ItemDto(
                    id = UUID.randomUUID().toString(),
                    what = "letter",
                    where = "paper",
                ),
                ItemDto(
                    id = UUID.randomUUID().toString(),
                    what = "clothes",
                    where = "other",
                ),
                ItemDto(
                    id = UUID.randomUUID().toString(),
                    what = "flower pot (plastic)",
                    where = "daily waste",
                )
            )
            initialItems.forEach {
                itemDao.insert(
                    item = ItemEntity(
                        id = it.id,
                        what = it.what,
                        where = it.where,
                    )
                )

            }
        }

        private suspend fun prepopulateBins() {
            val binDao = binDaoProvider.get()
            val initialBins = listOf(
                BinDto(
                    name = "Plastic",
                    imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fd4b071f8f5d2a86d8a04_PLAST_rgb_72dpi.jpg",
                    binColor = "#961e82"
                ),
                BinDto(
                    name = "Glass",
                    imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fda2d8e67ca67900e1a09_GLAS_rgb_72dpi.jpg",
                    binColor = "#21b685"
                ),
                BinDto(
                    name = "Paper",
                    imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fdb43d294ae564485d5a0_PAPIR_rgb_72dpi.jpg",
                    binColor = "#0082be"
                ),
                BinDto(
                    name = "Chemical",
                    imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fdf3d530bde43a3dc25ba_FARLIGT_AFFALD_rgb_72dpi.jpg",
                    binColor = "#e10f1e"
                ),
                BinDto(
                    name = "Cardboard",
                    imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fdd1d337f1620e240ff30_PAP_rgb_72dpi.jpg",
                    binColor = "#bea064"
                ),
                BinDto(
                    name = "Metal",
                    imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fdd52d278a18c574354e1_METAL_rgb_72dpi.jpg",
                    binColor = "#5a6e78"
                ),
                BinDto(
                    name = "Textile Waste",
                    imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fddfef9c6379888d26315_TEKSTILAFFALD%20rgb%2072dpi.jpg",
                    binColor = "#a01e41"
                ),
                BinDto(
                    name = "Other",
                    imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/6343c4cd5ac1fe60e0ffae48_DEPONI_rgb_72dpi_sort.jpg",
                    binColor = "#141414"
                ),
                BinDto(
                    name = "Daily Waste",
                    imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fe03386c06e05046d0208_RESTAFFALD_rgb_72dpi.jpg",
                    binColor = "#141414"
                ),
                BinDto(
                    name = "Electronics",
                    imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/6343b1f1025b9f2da35f381f_ELEKTRONIK_rgb_72dpi.jpg",
                    binColor = "#f0910a"
                ),
                BinDto(
                    name = "Batteries",
                    imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/6343b68a4d9033df76a2bf9f_BATTERIER_rgb_72dpi.jpg",
                    binColor = "#e10f1e"
                ),
                BinDto(
                    name = "Bulky Waste",
                    imageUrl = "https://cdn.josafety.dk/media/catalog/product/cache/dfd87d7c740a5795409624593b2701be/W/A/WA3002_445b573e_1_6.png",
                    binColor = "#000000"
                ),
                BinDto(
                    name = "Food Waste",
                    imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fd4b071f8f5c4166d8a01_MADAFFALD_rgb_72dpi.jpg",
                    binColor = "#00a04b"
                ),
                BinDto(
                    name = "Wood",
                    imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/65aeb6bb7397ecb9ee1afff6_TRAE_rgb_72dpi.jpg",
                    binColor = "#826428"
                )
            )
            binDao.insertAll(shops = initialBins.map {
                BinEntity(
                    name = it.name,
                    imageUrl = it.imageUrl,
                    binColor = it.binColor
                )
            })
        }
    }
}

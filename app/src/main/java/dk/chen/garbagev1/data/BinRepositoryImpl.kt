package dk.chen.garbagev1.data

import com.google.firebase.firestore.FirebaseFirestore
import dk.chen.garbagev1.domain.Bin
import dk.chen.garbagev1.domain.BinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.google.firebase.firestore.snapshots

class BinRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : BinRepository {

    private val binsCollection = db.collection("bins")

    override fun getBins(): Flow<List<Bin>> {
        return binsCollection
            .orderBy("name")
            .snapshots()
            .map { snapshot ->
                snapshot.toObjects(BinDto::class.java).map { it.toBin() }
            }
    }

    override suspend fun insertBins() {
        val bins = listOf(
            BinDto(name = "Food", imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fd4b071f8f5c4166d8a01_MADAFFALD_rgb_72dpi.jpg", binColor = "#00a04b"),
            BinDto(name = "Plastic", imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fd4b071f8f5d2a86d8a04_PLAST_rgb_72dpi.jpg", binColor = "#961e82"),
            BinDto(name = "Glass", imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fda2d8e67ca67900e1a09_GLAS_rgb_72dpi.jpg", binColor = "#21b685"),
            BinDto(name = "Paper", imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fdb43d294ae564485d5a0_PAPIR_rgb_72dpi.jpg", binColor = "#0082be"),
            BinDto(name = "Chemical", imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fdf3d530bde43a3dc25ba_FARLIGT_AFFALD_rgb_72dpi.jpg", binColor = "#e10f1e"),
            BinDto(name = "Cardboard", imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fdd1d337f1620e240ff30_PAP_rgb_72dpi.jpg", binColor = "#bea064"),
            BinDto(name = "Metal", imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fdd52d278a18c574354e1_METAL_rgb_72dpi.jpg", binColor = "#5a6e78"),
            BinDto(name = "Textile Waste", imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fddfef9c6379888d26315_TEKSTILAFFALD%20rgb%2072dpi.jpg", binColor = "#a01e41"),
            BinDto(name = "Other", imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/6343c4cd5ac1fe60e0ffae48_DEPONI_rgb_72dpi_sort.jpg", binColor = "#141414"),
            BinDto(name = "Daily Waste", imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fe03386c06e05046d0208_RESTAFFALD_rgb_72dpi.jpg", binColor = "#141414"),
            BinDto(name = "Electronics", imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/6343b1f1025b9f2da35f381f_ELEKTRONIK_rgb_72dpi.jpg", binColor = "#f0910a"),
            BinDto(name = "Batteries", imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/6343b68a4d9033df76a2bf9f_BATTERIER_rgb_72dpi.jpg", binColor = "#e10f1e"),
            BinDto(name = "Bulky Waste", imageUrl = "https://cdn.josafety.dk/media/catalog/product/cache/dfd87d7c740a5795409624593b2701be/W/A/WA3002_445b573e_1_6.png", binColor = "#000000"),
            BinDto(name = "Wood", imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/65aeb6bb7397ecb9ee1afff6_TRAE_rgb_72dpi.jpg", binColor = "#826428")
        )

        val batch = db.batch()
        bins.forEach { dto ->
            val docRef = binsCollection.document(dto.name)
            batch.set(docRef, dto)
        }
        batch.commit().await()
    }

    override suspend fun updateBinPickupTime(binName: String, time: Long) {
        binsCollection.document(binName)
            .update("lastPickupTime", time)
            .await()
    }
}

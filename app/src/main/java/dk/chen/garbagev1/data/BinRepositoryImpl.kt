package dk.chen.garbagev1.data

import dk.chen.garbagev1.domain.Bin
import dk.chen.garbagev1.domain.BinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BinRepositoryImpl @Inject constructor() : BinRepository {

    private val _binList = MutableStateFlow(
        value = listOf(
            BinDto(
                name = "Foodwaste",
                imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fd4b071f8f5c4166d8a01_MADAFFALD_rgb_72dpi.jpg",
                binColor = "#fbdc12"
            ),
            BinDto(
                name = "Plastics",
                imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fd4b071f8f5d2a86d8a04_PLAST_rgb_72dpi.jpg",
                binColor = "#023ea5"
            ),
            BinDto(
                name = "Glass",
                imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fda2d8e67ca67900e1a09_GLAS_rgb_72dpi.jpg",
                binColor = "#c50e20"
            ),
            BinDto(
                name = "Paper",
                imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fdb43d294ae564485d5a0_PAPIR_rgb_72dpi.jpg",
                binColor = "#c31315"
            ),
            BinDto(
                name = "Food & beverage cartons",
                imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fdbea687ae413c1a95fb6_MAD-_%26_DRIKKEKARTONER_rgb_72dpi.jpg",
                binColor = "#086036"
            ),
            BinDto(
                name = "Cardboard",
                imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fdd1d337f1620e240ff30_PAP_rgb_72dpi.jpg",
                binColor = "#0e223b"
            ),
            BinDto(
                name = "Metal",
                imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fdd52d278a18c574354e1_METAL_rgb_72dpi.jpg",
                binColor = "#009fe3"
            ),
            BinDto(
                name = "Textile waste",
                imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fddfef9c6379888d26315_TEKSTILAFFALD%20rgb%2072dpi.jpg",
                binColor = "#000000"
            ),
            BinDto(
                name = "Hazardous waste",
                imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fdf3d530bde43a3dc25ba_FARLIGT_AFFALD_rgb_72dpi.jpg",
                binColor = "#000000"
            ),
            BinDto(
                name = "Residual waste",
                imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fe03386c06e05046d0208_RESTAFFALD_rgb_72dpi.jpg",
                binColor = "#000000"
            )
        )
    )

    override fun getBins(): Flow<List<Bin>> {
        return _binList.map { dtoList -> dtoList.map { it.toBin() } }
    }
}

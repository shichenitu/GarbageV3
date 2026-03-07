package dk.chen.garbagev1.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import dk.chen.garbagev1.domain.Item
import dk.chen.garbagev1.domain.Bin
import java.util.UUID

class BinProvider : PreviewParameterProvider<Bin> {
    override val values = sequenceOf(
        Bin(
            name = "Foodwaste",
            imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fd4b071f8f5c4166d8a01_MADAFFALD_rgb_72dpi.jpg",
            binColor = Color(color = 0xFFfbdc12)
        ),
        Bin(
            name = "Plastics",
            imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fd4b071f8f5d2a86d8a04_PLAST_rgb_72dpi.jpg",
            binColor = Color(color = 0xFFfbdc12)
        ),
        Bin(
            name = "Glass",
            imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fda2d8e67ca67900e1a09_GLAS_rgb_72dpi.jpg",
            binColor = Color(color = 0xFFfbdc12)
        ),
        Bin(
            name = "Paper",
            imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fdb43d294ae564485d5a0_PAPIR_rgb_72dpi.jpg",
            binColor = Color(color = 0xFFfbdc12)
        ),
        Bin(
            name = "Food & beverage cartons",
            imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fdbea687ae413c1a95fb6_MAD-_%26_DRIKKEKARTONER_rgb_72dpi.jpg",
            binColor = Color(color = 0xFFfbdc12)
        ),
        Bin(
            name = "Cardboard",
            imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fdd1d337f1620e240ff30_PAP_rgb_72dpi.jpg",
            binColor = Color(color = 0xFFfbdc12)
        ),
        Bin(
            name = "Metal",
            imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fdd52d278a18c574354e1_METAL_rgb_72dpi.jpg",
            binColor = Color(color = 0xFFfbdc12)
        ),
        Bin(
            name = "Textile waste",
            imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fddfef9c6379888d26315_TEKSTILAFFALD%20rgb%2072dpi.jpg",
            binColor = Color(color = 0xFFfbdc12)
        ),
        Bin(
            name = "Hazardous waste",
            imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fdf3d530bde43a3dc25ba_FARLIGT_AFFALD_rgb_72dpi.jpg",
            binColor = Color(color = 0xFFfbdc12)
        ),
        Bin(
            name = "Residual waste",
            imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fe03386c06e05046d0208_RESTAFFALD_rgb_72dpi.jpg",
            binColor = Color(color = 0xFFfbdc12)
        )
    )
}

class BinOrNullProvider : PreviewParameterProvider<Bin?> {
    override val values = sequenceOf(
        Bin(
            name = "Foodwaste",
            imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fd4b071f8f5c4166d8a01_MADAFFALD_rgb_72dpi.jpg",
            binColor = Color(color = 0xFFfbdc12)
        ),
        null
    )
}

class ItemOrNullProvider : PreviewParameterProvider<Item?> {
    override val values = sequenceOf(
        Item(
            id = UUID.randomUUID().toString(),
            what = "Can",
            where = "Metal",
        ),
        null
    )
}

class BooleanProvider : PreviewParameterProvider<Boolean> {
    override val values = sequenceOf(
        true,
        false
    )
}

fun previewBins() = listOf(
    Bin(
        name = "Foodwaste",
        imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fd4b071f8f5c4166d8a01_MADAFFALD_rgb_72dpi.jpg",
        binColor = Color(color = 0xFFfbdc12)
    ),
    Bin(
        name = "Plastics",
        imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fd4b071f8f5d2a86d8a04_PLAST_rgb_72dpi.jpg",
        binColor = Color(color = 0xFFfbdc12)
    ),
    Bin(
        name = "Glass",
        imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fda2d8e67ca67900e1a09_GLAS_rgb_72dpi.jpg",
        binColor = Color(color = 0xFFfbdc12)
    ),
    Bin(
        name = "Paper",
        imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fdb43d294ae564485d5a0_PAPIR_rgb_72dpi.jpg",
        binColor = Color(color = 0xFFfbdc12)
    ),
    Bin(
        name = "Food & beverage cartons",
        imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fdbea687ae413c1a95fb6_MAD-_%26_DRIKKEKARTONER_rgb_72dpi.jpg",
        binColor = Color(color = 0xFFfbdc12)
    ),
    Bin(
        name = "Cardboard",
        imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fdd1d337f1620e240ff30_PAP_rgb_72dpi.jpg",
        binColor = Color(color = 0xFFfbdc12)
    ),
    Bin(
        name = "Metal",
        imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fdd52d278a18c574354e1_METAL_rgb_72dpi.jpg",
        binColor = Color(color = 0xFFfbdc12)
    ),
    Bin(
        name = "Textile waste",
        imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fddfef9c6379888d26315_TEKSTILAFFALD%20rgb%2072dpi.jpg",
        binColor = Color(color = 0xFFfbdc12)
    ),
    Bin(
        name = "Hazardous waste",
        imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fdf3d530bde43a3dc25ba_FARLIGT_AFFALD_rgb_72dpi.jpg",
        binColor = Color(color = 0xFFfbdc12)
    ),
    Bin(
        name = "Residual waste",
        imageUrl = "https://cdn.prod.website-files.com/633fd4b071f8f56baf6d88c9/633fe03386c06e05046d0208_RESTAFFALD_rgb_72dpi.jpg",
        binColor = Color(color = 0xFFfbdc12)
    )
)

fun previewGarbageList() = listOf(
    Item(
        id = UUID.randomUUID().toString(),
        what = "Can",
        where = "Metal",
    ),
    Item(
        id = UUID.randomUUID().toString(),
        what = "Newspaper",
        where = "Paper",
    ),
    Item(
        id = UUID.randomUUID().toString(),
        what = "Potato",
        where = "Food",
    ),
    Item(
        id = UUID.randomUUID().toString(),
        what = "Computer",
        where = "Electronics",
    ),
    Item(
        id = UUID.randomUUID().toString(),
        what = "Battery",
        where = "Batteries",
    )
)

@Preview(name = "Light Mode", uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
annotation class ThemedPreviews


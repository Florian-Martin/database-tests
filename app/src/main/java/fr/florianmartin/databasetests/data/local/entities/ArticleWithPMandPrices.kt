package fr.florianmartin.databasetests.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ArticleWithPMandPrices(
    @Embedded
    val article: ArticleEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "apID"
    )
    val aps: List<ArticlePriceEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "pmID"
    )
    val pms: List<PaymentMethodEntity>
)

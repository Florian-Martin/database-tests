package fr.florianmartin.databasetests.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

data class ArticleWithPMsAndAps(
    @Embedded
    val article: ArticleEntity,
    @Relation(
        parentColumn = "articleId",
        entityColumn = "pmId",
        associateBy = Junction(ArticlePMsCrossRef::class)
    )
    val pms: List<PaymentMethodEntity>,
    @Relation(
        parentColumn = "articleId",
        entityColumn = "articleId"
    )
    val aps: List<ArticlePriceEntity>
)


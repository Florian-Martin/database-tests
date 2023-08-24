package fr.florianmartin.databasetests.data.local.entities

import androidx.room.Entity

@Entity(
    tableName = "cross_ref",
    primaryKeys = ["articleId", "pmId"]
)
data class ArticlePMsCrossRef(
    val articleId: Int,
    val pmId: Int
)
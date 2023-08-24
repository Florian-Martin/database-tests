package fr.florianmartin.databasetests.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article_price")
data class ArticlePriceEntity(
    @PrimaryKey
    val apId: Int,
    val name: String,
    val total: Int,
    val articleId: Int
)

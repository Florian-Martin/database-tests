package fr.florianmartin.databasetests.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "linked_articles",
    primaryKeys = ["article_id", "linked_article_id"],
    foreignKeys = [
        ForeignKey(
            entity = ArticleEntity::class,
            parentColumns = ["articleId"],
            childColumns = ["article_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ArticleEntity::class,
            parentColumns = ["articleId"],
            childColumns = ["linked_article_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LinkedArticleEntity(
    @ColumnInfo(name = "article_id")
    val articleId: Int,

    @ColumnInfo(name = "linked_article_id")
    val linkedArticleId: Int
)

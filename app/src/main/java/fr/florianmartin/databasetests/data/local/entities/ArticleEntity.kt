package fr.florianmartin.databasetests.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.florianmartin.databasetests.model.Article

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey(true)
    val id: Int = 0,
    val name: String,
    val author: String,
    val publicationDate: String
) {
    fun asDomainModel(): Article {
        return Article(
            nameTest = name,
            author = author,
            publicationDate = publicationDate
        )
    }
}

package fr.florianmartin.databasetests.model

import fr.florianmartin.databasetests.data.local.entities.ArticleEntity

data class Article(
    val nameTest: String,
    val author: String,
    val publicationDate: String
){
    fun asDatabaseModel(): ArticleEntity {
        return ArticleEntity(
            name = nameTest,
            author = author,
            publicationDate = publicationDate
        )
    }
}


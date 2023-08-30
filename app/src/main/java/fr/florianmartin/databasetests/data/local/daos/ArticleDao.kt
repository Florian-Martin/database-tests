package fr.florianmartin.databasetests.data.local.daos

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Junction
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import fr.florianmartin.databasetests.data.local.entities.ArticleEntity
import fr.florianmartin.databasetests.data.local.entities.ArticleWithPMsAndAps
import fr.florianmartin.databasetests.data.local.entities.LinkedArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(list: List<ArticleEntity>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(articleEntity: ArticleEntity)

    @Delete
    suspend fun deleteAll(list: List<ArticleEntity>)

    @Delete
    suspend fun delete(articleEntity: ArticleEntity)

    @Query("SELECT * FROM articles")
    fun getAllArticles(): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM articles WHERE articleId = :id")
    fun getArticles(id: Int): Flow<ArticleEntity>

    @Transaction
    @Query("SELECT * FROM articles")
    fun getFullArticles(): List<ArticleWithPMsAndAps>

    @Transaction
    @Query("SELECT * FROM articles")
    suspend fun getArticles2(): List<DatabaseFetch2>

    @Transaction
    @Query(
        "SELECT articles.articleId, article_price.apId " +
                "FROM articles " +
                "JOIN article_price USING(articleId)"
    )
    suspend fun getArticles3(): List<DatabaseFetch3>

//    @Transaction
//    @Query(
//        "SELECT articles.articleId, articles.author, articles.name, articles.publicationDate " +
//                "FROM articles " +
//                "JOIN linked_articles ON articles.articleId = linked_articles.linked_article_id"
//    )
//    suspend fun getArticles4(): List<DatabaseFetch4>
}


data class DatabaseFetch2(
    @Embedded
    val article: ArticleEntity,
)

data class DatabaseFetch3(
    @ColumnInfo(name = "articleId")
    val abcd: Int,
    val apId: Int
)

//data class DatabaseFetch4(
//    @Embedded
//    val articleEntity: ArticleEntity,
//
//    @Relation(
//        parentColumn = "articleId",
//        entityColumn = "article_id",
//        associateBy = Junction(LinkedArticleEntity::class)
//    )
//    val linkedArticles: List<ArticleEntity>
//)

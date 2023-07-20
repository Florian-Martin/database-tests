package fr.florianmartin.databasetests.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.florianmartin.databasetests.data.local.entities.ArticleEntity
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

    @Query("SELECT * FROM articles WHERE id = :id")
    fun getArticle(id: Int): Flow<ArticleEntity>
}
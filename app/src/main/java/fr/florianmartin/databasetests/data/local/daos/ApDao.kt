package fr.florianmartin.databasetests.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.florianmartin.databasetests.data.local.entities.ArticleEntity
import fr.florianmartin.databasetests.data.local.entities.ArticlePriceEntity
import fr.florianmartin.databasetests.data.local.entities.PaymentMethodEntity

@Dao
interface ApDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(list: List<ArticlePriceEntity>)

    @Query("SELECT * FROM article_price")
    fun getAllAp(): List<ArticlePriceEntity>
}
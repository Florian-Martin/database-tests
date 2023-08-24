package fr.florianmartin.databasetests.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.florianmartin.databasetests.data.local.entities.ArticleEntity
import fr.florianmartin.databasetests.data.local.entities.PaymentMethodEntity

@Dao
interface PmDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(list: List<PaymentMethodEntity>)

    @Query("SELECT * FROM payment_method")
    fun getAllPms(): List<PaymentMethodEntity>
}
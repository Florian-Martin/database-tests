package fr.florianmartin.databasetests.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fr.florianmartin.databasetests.data.local.daos.ApDao
import fr.florianmartin.databasetests.data.local.daos.ArticleDao
import fr.florianmartin.databasetests.data.local.daos.CrossRefDao
import fr.florianmartin.databasetests.data.local.daos.PmDao
import fr.florianmartin.databasetests.data.local.entities.ArticleEntity
import fr.florianmartin.databasetests.data.local.entities.ArticlePMsCrossRef
import fr.florianmartin.databasetests.data.local.entities.ArticlePriceEntity
import fr.florianmartin.databasetests.data.local.entities.LinkedArticleEntity
import fr.florianmartin.databasetests.data.local.entities.PaymentMethodEntity

@Database(
    entities = [
        ArticleEntity::class,
        ArticlePriceEntity::class,
        PaymentMethodEntity::class,
        ArticlePMsCrossRef::class,
        LinkedArticleEntity::class
    ],
    version = 5,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getArticlesDao(): ArticleDao
    abstract fun getPmDao(): PmDao
    abstract fun getApDao(): ApDao
    abstract fun getCrossRefDao(): CrossRefDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "test"
                )
                    .addMigrations(MIGRATION_2_3)
                    .addMigrations(MIGRATION_3_4)
                    .addMigrations(MIGRATION_4_5)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
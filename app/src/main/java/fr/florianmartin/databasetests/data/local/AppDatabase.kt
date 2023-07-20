package fr.florianmartin.databasetests.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fr.florianmartin.databasetests.data.local.daos.ArticleDao
import fr.florianmartin.databasetests.data.local.entities.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getArticlesDao(): ArticleDao

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
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
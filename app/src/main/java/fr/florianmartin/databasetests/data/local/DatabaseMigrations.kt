package fr.florianmartin.databasetests.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE articles " +
                    "ADD COLUMN test_add_column INTEGER NOT NULL DEFAULT 0"
        )
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        with(database) {
            execSQL(
                "CREATE TABLE articles_backup(" +
                        "articleId INTEGER NOT NULL, " +
                        "name TEXT NOT NULL, " +
                        "author TEXT NOT NULL, " +
                        "publicationDate TEXT NOT NULL, " +
                        "PRIMARY KEY(articleId))"
            )
            execSQL(
                "INSERT INTO articles_backup " +
                        "SELECT articleId, name, author, publicationDate " +
                        "FROM articles"
            )
            execSQL("DROP TABLE articles")
            execSQL("ALTER TABLE articles_backup RENAME TO articles")
        }
    }
}

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        with(database) {
            execSQL("CREATE TABLE linked_articles(" +
                    "article_id INTEGER NOT NULL, " +
                    "linked_article_id INTEGER NOT NULL, " +
                    "PRIMARY KEY(article_id, linked_article_id), " +
                    "FOREIGN KEY(article_id) REFERENCES articles(articleId) ON DELETE CASCADE, " +
                    "FOREIGN KEY(linked_article_id) REFERENCES articles(articleId) ON DELETE CASCADE)")
        }
    }
}
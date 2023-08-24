package fr.florianmartin.databasetests.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import fr.florianmartin.databasetests.data.local.entities.ArticlePMsCrossRef

@Dao
interface CrossRefDao {
    @Insert
    fun insertAll(crossRefs: List<ArticlePMsCrossRef>)
}
package fr.florianmartin.databasetests.data.repositories


import fr.florianmartin.databasetests.data.local.AppDatabase
import fr.florianmartin.databasetests.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ArticlesRepository(database: AppDatabase) : AppRepository<Article> {

    private val dao = database.getArticlesDao()

    override fun fetchAllFromLocal(): Flow<List<Article>> =
        dao.getAllArticles().map { articlesList ->
            articlesList.map { article ->
                article.asDomainModel()
            }
        }

    override fun fetchFromLocal(id: Int): Flow<Article> {
        return flow {
            dao.getArticle(id)
        }
    }

    override suspend fun insertAllIntoDb(list: List<Article>) {
        dao.insertAll(list.map { it.asDatabaseModel() })
    }

    override suspend fun insertIntoDb(item: Article) {
        dao.insert(item.asDatabaseModel())
    }

}

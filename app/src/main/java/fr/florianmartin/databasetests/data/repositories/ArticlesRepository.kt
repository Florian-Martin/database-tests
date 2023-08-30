package fr.florianmartin.databasetests.data.repositories


import fr.florianmartin.databasetests.data.local.AppDatabase
import fr.florianmartin.databasetests.data.local.daos.DatabaseFetch2
import fr.florianmartin.databasetests.data.local.daos.DatabaseFetch3
import fr.florianmartin.databasetests.data.local.entities.ArticlePMsCrossRef
import fr.florianmartin.databasetests.data.local.entities.ArticlePriceEntity
import fr.florianmartin.databasetests.data.local.entities.ArticleWithPMsAndAps
import fr.florianmartin.databasetests.data.local.entities.PaymentMethodEntity
import fr.florianmartin.databasetests.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ArticlesRepository(database: AppDatabase) : AppRepository<Article> {

    private val articleDao = database.getArticlesDao()
    private val pmDAO = database.getPmDao()
    private val apDAO = database.getApDao()
    private val crossRefsDao = database.getCrossRefDao()

    override fun fetchAllFromLocal(): Flow<List<Article>> =
        articleDao.getAllArticles().map { articlesList ->
            articlesList.map { article ->
                article.asDomainModel()
            }
        }

    override fun fetchFromLocal(id: Int): Flow<Article> {
        return flow {
            articleDao.getArticles(id)
        }
    }

    override suspend fun insertAllIntoDb(list: List<Article>) {
        articleDao.insertAll(list.map { it.asDatabaseModel() })
    }

    override suspend fun insertIntoDb(item: Article) {
        articleDao.insert(item.asDatabaseModel())
    }

    suspend fun insertArticles(articles: List<Article>) {
        withContext(Dispatchers.IO) {
            articleDao.insertAll(articles.map { it.asDatabaseModel() })
        }
    }

    suspend fun insertPms(pms: List<PaymentMethodEntity>) {
        withContext(Dispatchers.IO) {
            pmDAO.insertAll(pms)
        }
    }

    suspend fun insertAps(aps: List<ArticlePriceEntity>) {
        withContext(Dispatchers.IO) {
            apDAO.insertAll(aps)
        }
    }

    suspend fun getFullArticles(): List<ArticleWithPMsAndAps> {
        return withContext(Dispatchers.IO) {
            articleDao.getFullArticles()
        }
    }

    suspend fun insertCrossRefs(crossRefs: List<ArticlePMsCrossRef>) {
        return withContext(Dispatchers.IO) {
            crossRefsDao.insertAll(crossRefs)
        }
    }

    suspend fun getArticles2(): List<DatabaseFetch2> = articleDao.getArticles2()
    suspend fun getArticles3(): List<DatabaseFetch3> = articleDao.getArticles3()
//    suspend fun getArticles4(): List<DatabaseFetch4> = articleDao.getArticles4()

}

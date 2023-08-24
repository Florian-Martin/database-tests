package fr.florianmartin.databasetests.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import fr.florianmartin.databasetests.data.local.entities.ArticlePMsCrossRef
import fr.florianmartin.databasetests.data.local.entities.ArticlePriceEntity
import fr.florianmartin.databasetests.data.local.entities.ArticleWithPMsAndAps
import fr.florianmartin.databasetests.data.local.entities.PaymentMethodEntity
import fr.florianmartin.databasetests.data.repositories.ArticlesRepository
import fr.florianmartin.databasetests.model.Article
import fr.florianmartin.databasetests.utils.shuffle
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val articlesRepo: ArticlesRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    val chars = "abcdefgh"

    val articlesList = listOf(
        Article("aa", "aa", "aa"),
        Article("bb", "bb", "bb")
    )
    val pms = listOf(
        PaymentMethodEntity(1, "cb"),
        PaymentMethodEntity(2, "cash"),
        PaymentMethodEntity(3, "cheques"),
    )
    val crossRefs = listOf(
        ArticlePMsCrossRef(1, 2),
        ArticlePMsCrossRef(1, 3)
    )
    val aps = listOf(
        ArticlePriceEntity(1, "taxe10", 10, 1),
        ArticlePriceEntity(2, "taxe20", 20, 1),
        ArticlePriceEntity(3, "taxe10", 10, 2),
    )

    private var _articlesFlow = MutableStateFlow<List<ArticleWithPMsAndAps>>(emptyList())
    val articlesFlow: StateFlow<List<ArticleWithPMsAndAps>>
        get() = _articlesFlow


    fun insertAllArticlesInDb() {
        viewModelScope.launch {
            articlesRepo.insertArticles(articlesList)
            articlesRepo.insertPms(pms)
            articlesRepo.insertAps(aps)
            articlesRepo.insertCrossRefs(crossRefs)
        }
    }

    fun insertArticleInDb(article: Article) {
        viewModelScope.launch(dispatcher) {
            articlesRepo.insertIntoDb(article)
        }
    }

    fun getFullArticles() {
        viewModelScope.launch {
            _articlesFlow.value = articlesRepo.getFullArticles()
        }
    }


    // FONCTIONNE
//    fun getArticles() {
//        viewModelScope.launch {
//            articlesRepo.fetchAllFromLocal().collect {
//                _articlesFlow.value = it
//            }
//        }
//    }

    fun getArticles(): Flow<List<Article>> = articlesRepo.fetchAllFromLocal()

    fun createRandomArticle(): Article =
        Article(
            chars.shuffle(),
            chars.shuffle(),
            chars.shuffle()
        )


    class Factory(
        private val repository: ArticlesRepository,
        private val dispatcher: CoroutineDispatcher
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(repository, dispatcher) as T
            }
            throw IllegalArgumentException("Unknown viewModel class")
        }
    }
}
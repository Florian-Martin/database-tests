package fr.florianmartin.databasetests.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import fr.florianmartin.databasetests.data.repositories.ArticlesRepository
import fr.florianmartin.databasetests.model.Article
import fr.florianmartin.databasetests.utils.shuffle
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val articlesRepo: ArticlesRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    val chars = "abcdefgh"

    val articlesList = listOf(
        Article("aa", "aa", "aa"),
        Article("bb", "bb", "bb")
    )

//    private var _articlesFlow = MutableStateFlow<List<Article>>(emptyList())
//    val articlesFlow: StateFlow<List<Article>>
//        get() = _articlesFlow

    fun insertAllArticlesInDb(articles: List<Article>) {
        viewModelScope.launch {
            articlesRepo.insertAllIntoDb(articlesList)
        }
    }

    fun insertArticleInDb(article: Article) {
        viewModelScope.launch(dispatcher) {
            articlesRepo.insertIntoDb(article)
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
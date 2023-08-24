package fr.florianmartin.databasetests.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import fr.florianmartin.databasetests.R
import fr.florianmartin.databasetests.data.local.AppDatabase
import fr.florianmartin.databasetests.data.repositories.ArticlesRepository
import fr.florianmartin.databasetests.databinding.ActivityMainBinding
import fr.florianmartin.databasetests.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            MainViewModel.Factory(
                ArticlesRepository(AppDatabase.getInstance(application)),
                Dispatchers.IO
            )
        )[MainViewModel::class.java]
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.insertAllArticlesInDb()

        lifecycleScope.launch {
            viewModel.articlesFlow.collect {
                it.forEach { articleWithPms ->
                    Log.e("*****", articleWithPms.toString())
                }
            }
        }

//        lifecycleScope.launch {
//            viewModel.getArticles().collect { list ->
//                list.forEach {
//                    Log.e("floooow", it.nameTest)
//                }
//            }
//        }
        setListeners(binding)
    }

    private fun setListeners(binding: ActivityMainBinding) {
        binding.apply {
            insert1.setOnClickListener {
                viewModel.insertArticleInDb(viewModel.createRandomArticle())
            }
            insertAList.setOnClickListener {

            }
            get1ById.setOnClickListener {

            }
            getAList.setOnClickListener {
                viewModel.getFullArticles()
            }
        }
    }
}
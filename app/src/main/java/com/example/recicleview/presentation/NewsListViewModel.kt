package com.example.recicleview.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recicleview.data.remote.NewsDto
import com.example.recicleview.data.remote.NewsService
import com.example.recicleview.data.remote.RetrofitModule
import kotlinx.coroutines.launch

class NewsListViewModel(
    private val  newsService: NewsService
): ViewModel() {

    private val _newsListLiveData = MutableLiveData<List<NewsDto>>()
    val newsListLiveData: LiveData<List<NewsDto>> = _newsListLiveData

    init {
        getNewsList()
    }

    private fun getNewsList(){
        viewModelScope.launch {
            try {
                val response =newsService.fetchTopNews()
                _newsListLiveData.value = response.data
            }catch (ex:Exception){
                ex.printStackTrace()
            }
        }
    }

    companion object{

        fun create (): NewsListViewModel {
            val newsService = RetrofitModule.createNewsService()
            return NewsListViewModel(newsService)
        }

    }
}
package com.example.recicleview.data.remote

import retrofit2.Call
import retrofit2.http.GET

interface NewsService {

    @GET("news?category=science")
    fun fetchNews(): Call<NewsResponse>
}
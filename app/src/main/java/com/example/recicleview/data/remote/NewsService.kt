package com.example.recicleview.data.remote

import com.example.recicleview.BuildConfig
import retrofit2.http.GET

interface NewsService {

    @GET("top?api_token=${BuildConfig.API_KEY}&locale=us")
    suspend fun fetchTopNews(): NewsResponse
}
package com.example.recicleview.data.remote

import com.google.gson.annotations.SerializedName

data class NewsResponse (
val data:List<NewsDto>
)
data class NewsDto(
    @SerializedName("uuid")
    val id:String,
    @SerializedName("snippet")
    val content:String,
    @SerializedName("image_url")
    val imgUrl:String,
    val title:String,
)


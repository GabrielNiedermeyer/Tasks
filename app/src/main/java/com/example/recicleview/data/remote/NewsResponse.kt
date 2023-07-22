package com.example.recicleview.data.remote

data class NewsResponse (
val category:String,
val data:List<NewsDto>
)
data class NewsDto(

    val id:String,
    val content:String,
    val imgUrl:String,
    val title:String,
)


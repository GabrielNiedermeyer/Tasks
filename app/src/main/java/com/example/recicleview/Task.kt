package com.example.recicleview

import java.io.Serializable

data class Task(
    val id: Int,
    val title:String,
    val description:String
    ): Serializable

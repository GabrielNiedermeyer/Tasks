package com.example.recicleview.presentation

import com.example.recicleview.data.local.Task
import java.io.Serializable

// CRUD MEANS : CREATE, READ, UPDATE AND DELETE

enum class ActionType {
    DELETE,
    UPDATE,
    CREATE
}

data class  TaskAction(
    val task: Task?=null,
    val actionType: String
): Serializable
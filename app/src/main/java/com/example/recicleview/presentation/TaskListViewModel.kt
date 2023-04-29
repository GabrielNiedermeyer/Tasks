package com.example.recicleview.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.recicleview.TaskBeatsApplication
import com.example.recicleview.data.TaskDao

class TaskListViewModel(private val TaskDao: TaskDao): ViewModel() {

    companion object{

        fun create ( apllication: Application): TaskListViewModel{
            val dataBaseInstance = (apllication as TaskBeatsApplication).getAppDataBase()
            val dao = dataBaseInstance.taskDao()
            return TaskListViewModel(dao)
        }
    }
}
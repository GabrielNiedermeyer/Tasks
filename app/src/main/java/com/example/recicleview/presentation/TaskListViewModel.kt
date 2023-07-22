package com.example.recicleview.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.recicleview.TaskBeatsApplication
import com.example.recicleview.data.local.Task
import com.example.recicleview.data.local.TaskDao

class TaskListViewModel(TaskDao: TaskDao): ViewModel() {
    fun execute(taskAction: TaskAction) {

    }

    val taskListLiveData: LiveData<List<Task>> = TaskDao.getAll()
    companion object{
        fun create ( application: Application): TaskListViewModel{
            val dataBaseInstance = (application as TaskBeatsApplication).getAppDataBase()
            val dao = dataBaseInstance.taskDao()
            return TaskListViewModel(dao)
        }
    }
}
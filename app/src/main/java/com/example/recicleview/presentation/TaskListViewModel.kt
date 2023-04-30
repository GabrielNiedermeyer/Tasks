package com.example.recicleview.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recicleview.TaskBeatsApplication
import com.example.recicleview.data.Task
import com.example.recicleview.data.TaskDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskListViewModel(private val TaskDao: TaskDao): ViewModel() {

    val taskListLiveData: LiveData<List<Task>> = TaskDao.getAll()

    fun execute(taskAction: TaskAction){
        when (taskAction.actionType) {
            ActionType.DELETE.name -> deleteById(taskAction.task.id)
            ActionType.CREATE.name -> insertIntoDataBase(taskAction.task)
            ActionType.UPDATE.name -> updateIntoDataBase(taskAction.task)
        }
    }
    private fun deleteById(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            TaskDao.deleteById(id)
        }
    }
    private fun insertIntoDataBase(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            TaskDao.insert(task)

        }
    }
    private fun updateIntoDataBase(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            TaskDao.update(task)

        }
    }
    companion object{
        fun create ( apllication: Application): TaskListViewModel{
            val dataBaseInstance = (apllication as TaskBeatsApplication).getAppDataBase()
            val dao = dataBaseInstance.taskDao()
            return TaskListViewModel(dao)
        }
    }
}
package com.example.recicleview.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.recicleview.TaskBeatsApplication
import com.example.recicleview.data.local.Task
import com.example.recicleview.data.local.TaskDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskDetailViewModel(
    private val TaskDao: TaskDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

): ViewModel(){
    fun execute(taskAction: TaskAction){
        when (taskAction.actionType) {
            ActionType.DELETE.name -> deleteById(taskAction.task!!.id)
            ActionType.CREATE.name -> insertIntoDataBase(taskAction.task!!)
            ActionType.UPDATE.name -> updateIntoDataBase(taskAction.task!!)
        }
    }
    private fun deleteById(id: Int){
        viewModelScope.launch(dispatcher) {
            TaskDao.deleteById(id)
        }
    }
    private fun insertIntoDataBase(task: Task) {
        viewModelScope.launch(dispatcher) {
            TaskDao.insert(task)

        }
    }
    private fun updateIntoDataBase(task: Task) {
        viewModelScope.launch(dispatcher) {
            TaskDao.update(task)

        }
    }

    companion object{
        fun getVMFactory(application: Application): ViewModelProvider.Factory {
            val dataBaseInstance =  (application as  TaskBeatsApplication).getAppDataBase()

            val dao = dataBaseInstance.taskDao()

            val factory =  object : ViewModelProvider.Factory{

                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return TaskDetailViewModel(dao) as T
                }
            }
            return factory
        }
    }
}
package com.example.recicleview.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recicleview.TaskBeatsApplication
import com.example.recicleview.data.Task
import com.example.recicleview.data.TaskDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskListViewModel(
    private val TaskDao: TaskDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    val taskListLiveData: LiveData<List<Task>> = TaskDao.getAll()

    fun execute(taskAction: TaskAction){
        when (taskAction.actionType) {
            ActionType.DELETE.name -> deleteById(taskAction.task!!.id)
            ActionType.CREATE.name -> insertIntoDataBase(taskAction.task!!)
            ActionType.UPDATE.name -> updateIntoDataBase(taskAction.task!!)
            ActionType.DELETE_ALL.name -> deleteAll()
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
    private fun deleteAll(){
        viewModelScope.launch(dispatcher) {
            TaskDao .deleteALl()

        }
    }
    companion object{
        fun create ( application: Application): TaskListViewModel{
            val dataBaseInstance = (application as TaskBeatsApplication).getAppDataBase()
            val dao = dataBaseInstance.taskDao()
            return TaskListViewModel(dao)
        }
    }
}
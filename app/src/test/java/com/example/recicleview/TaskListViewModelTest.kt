package com.example.recicleview

import com.example.recicleview.data.Task
import com.example.recicleview.data.TaskDao
import com.example.recicleview.presentation.ActionType
import com.example.recicleview.presentation.TaskAction
import com.example.recicleview.presentation.TaskListViewModel
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.description
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class TaskListViewModelTest {

    private val taskDao: TaskDao = mock()
    private  val  underTest: TaskListViewModel by lazy {
        TaskListViewModel(
            taskDao,
        UnconfinedTestDispatcher())
    }

    //Test case delete_all

    @Test
    fun delete_all()= runTest{
        //Given
        val taskAction = TaskAction(
            task = null,
            actionType = ActionType.DELETE_ALL.name
        )
        //When
        underTest.execute(taskAction)

        //Then
        verify(taskDao).deleteALl()
    }

    @Test
    fun update_task() = runTest {
        // Given
        val task = Task(
            id = 1,
            title = "title",
            description = "Description")
        val taskAction = TaskAction(
            task = task,
            actionType = ActionType.UPDATE.name
        )

        //When
        underTest.execute(taskAction)

        //Then
        verify(taskDao).update(task)

    }
}

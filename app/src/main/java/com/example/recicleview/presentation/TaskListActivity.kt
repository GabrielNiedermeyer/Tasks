package com.example.recicleview.presentation

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import androidx.room.InvalidationTracker.Observer
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recicleview.R
import com.example.recicleview.TaskBeatsApplication
import com.example.recicleview.data.AppDataBase
import com.example.recicleview.data.Task
import com.example.recicleview.data.TaskDao_Impl
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.io.Serializable

class MainActivity : AppCompatActivity() {


    private lateinit var ctnContent: LinearLayout

    private val adapter: TaskListAdapter by lazy {
        TaskListAdapter(::onListItemClicked)
    }

    private val  viewModel: TaskListViewModel by lazy {
        TaskListViewModel.create(application)
    }

    lateinit var dataBase :AppDataBase

    private val dao by lazy {
        dataBase.taskDao()
    }


    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK){

            //take the result
            val data = result.data
            val taskAction = data?.getSerializableExtra(TASK_ACTION_RESULT) as TaskAction
            val task: Task = taskAction.task

            when (taskAction.actionType) {
                ActionType.DELETE.name -> deleteById(task.id)
                ActionType.CREATE.name -> insertIntoDataBase(task)
                ActionType.UPDATE.name -> updateIntoDataBase(task)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        setSupportActionBar(findViewById(R.id.toolbar))

        ctnContent = findViewById(R.id.ctn_content)


        val rvTasks: RecyclerView = findViewById(R.id.rv_task_list)
        rvTasks.adapter = adapter

        val fab = findViewById<FloatingActionButton>(R.id.fab_add)
        fab.setOnClickListener{
            openTaskListDetail(null)
        }
    }

    override fun onStart() {
        super.onStart()

       dataBase = (application as TaskBeatsApplication).getAppDataBase()

        listFromDataBase()
    }

    private fun insertIntoDataBase(task: Task) {
        CoroutineScope(IO).launch {
            dao.insert(task)

        }
    }
    private fun updateIntoDataBase(task: Task) {
        CoroutineScope(IO).launch {
        dao.update(task)

        }
    }

    private fun deleteAll(){
        CoroutineScope(IO).launch {
        dao.deleteALl()

        }
    }

    private fun deleteById(id: Int){
        CoroutineScope(IO).launch {
            dao.deleteById(id)
        }
    }
    private fun listFromDataBase(){

            //observer
            val listObserver = androidx.lifecycle.Observer<List<Task>> { listTask ->
                adapter.submitList(listTask)
            }

            //Live data
            dao.getAll().observe(this@MainActivity,listObserver)


    }

    private fun showMessage (view: View, message: String){
        Snackbar.make(view,message,Snackbar.LENGTH_LONG)
            .setAction("Action",null)
            .show()


    }

    private fun onListItemClicked(task: Task) {
        openTaskListDetail(task)
    }

    private fun openTaskListDetail(task: Task?){
        val intent = TaskDetailActivity.start(this, task)
        startForResult.launch(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_task_list,menu)
        return true
    }

    //delete all tasks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_all_task -> {
                deleteAll()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

// CRUD MEANS : CREATE, READ, UPDATE AND DELETE

enum class ActionType {
    DELETE,
    UPDATE,
    CREATE
}

data class  TaskAction(
    val task: Task,
    val actionType: String
    ): Serializable

const val  TASK_ACTION_RESULT = "TASK_ACTION_RESULT"
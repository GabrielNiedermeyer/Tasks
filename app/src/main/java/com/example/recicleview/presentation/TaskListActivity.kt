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

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK){

            //take the result
            val data = result.data
            val taskAction = data?.getSerializableExtra(TASK_ACTION_RESULT) as TaskAction

            viewModel.execute(taskAction)
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
        listFromDataBase()
    }



    private fun deleteAll(){
        val taskAction = TaskAction(null, ActionType.DELETE_ALL.name)
        viewModel.execute(taskAction)
    }


    private fun listFromDataBase(){

            //observer
            val listObserver = androidx.lifecycle.Observer<List<Task>> { listTask ->
            if(listTask.isEmpty()){
                ctnContent.visibility = View.VISIBLE
            }else{
                ctnContent.visibility = View.GONE
            }
                adapter.submitList(listTask)

            }

            //Live data
        viewModel.taskListLiveData.observe(this@MainActivity,listObserver)
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
    DELETE_ALL,
    UPDATE,
    CREATE
}

data class  TaskAction(
    val task: Task?=null,
    val actionType: String
    ): Serializable

const val  TASK_ACTION_RESULT = "TASK_ACTION_RESULT"
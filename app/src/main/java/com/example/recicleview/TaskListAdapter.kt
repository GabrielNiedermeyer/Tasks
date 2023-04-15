package com.example.recicleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class TaskListAdapter(
    private val openTaskDetailView:(task: Task ) -> Unit
    ):
    ListAdapter<Task,TaskViewHolder>(TaskListAdapter) {

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {

        val view: View = LayoutInflater
        .from(parent.context).
        inflate(R.layout.item_task, parent,false)
        return  TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
    val task = getItem(position)
    holder.bind(task, openTaskDetailView)
    }

    companion object: DiffUtil.ItemCallback<Task>() {

        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.title == newItem.title &&
                  oldItem.description == newItem.description

        }
    }
}

class TaskViewHolder(private val view:View) : RecyclerView.ViewHolder(view){

    private val tvTitle = view.findViewById<TextView>(R.id.Tv_task_title)
    private val tvDesc = view.findViewById<TextView>(R.id.Tv_task_description)

    fun bind(
        task: Task,
        openTaskDetailView:(task: Task ) -> Unit)
    {
        tvTitle.text = task.title
        tvDesc.text = task.description
        view.setOnClickListener{
            openTaskDetailView.invoke(task)
        }
    }
}
package com.example.recicleview.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    //Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task: Task)

    @Query("Select * from task")
    fun getAll():LiveData<List<Task>>

    //Update
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(task: Task)

    //Delete all
    @Query("DELETE from task")
    fun deleteALl()

    //Delete one task
    @Query("DELETE from task WHERE id =:id")
    fun deleteById(id:Int)
}
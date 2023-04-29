package com.example.recicleview

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import com.example.recicleview.data.AppDataBase


class TaskBeatsApplication: Application() {

    private lateinit var dataBase :AppDataBase

    override fun onCreate() {
        super.onCreate()

        dataBase = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java, "taskbeats-database"
        ).build()
    }

    fun getAppDataBase():AppDataBase{
        return dataBase
    }
}
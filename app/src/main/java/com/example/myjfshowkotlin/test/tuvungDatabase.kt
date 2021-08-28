package com.example.myjfshowkotlin.test

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myjfshowkotlin.model.Tuvung

@Database(entities = [Tuvung::class], version = 1,exportSchema = false)
abstract class tuvungDatabase : RoomDatabase(){
    abstract fun mytuvungDAO(): tuvungDAO?

    companion object{
        private const val DATABASE_NAME = "mytuvung.db"
        private var instance: tuvungDatabase? = null
        @Synchronized
        fun getInstance(context: Context): tuvungDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                    tuvungDatabase::class.java,
                    DATABASE_NAME)
                    .allowMainThreadQueries().build()
            }
            return instance
        }
    }
}
package com.example.myjfshowkotlin.test

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.myjfshowkotlin.model.Tuvung

@Dao
interface tuvungDAO{
    @Insert(onConflict = REPLACE)
    fun insertTuvung(myTuvung: Tuvung)

    @Query("SELECT * FROM  myTuvung")
    fun getAlltuvung(): List<Tuvung>

    @Delete
    fun deleteTuvung(myTuvung: Tuvung)

    @Query("Select * from myTuvung where name like '%' || :tuvungname || '%' ")
    infix fun findlocaltuvung(tuvungname:String): List<Tuvung>
}
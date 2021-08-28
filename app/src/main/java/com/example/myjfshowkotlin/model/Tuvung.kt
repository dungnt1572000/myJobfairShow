package com.example.myjfshowkotlin.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "myTuvung")
data class Tuvung(
    val id: Int,
    @PrimaryKey
    val name: String,
    val imi: String,
    val maucau: String,
    val omoimasu: Boolean
) {

}
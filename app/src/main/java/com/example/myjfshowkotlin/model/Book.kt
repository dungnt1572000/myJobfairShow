package com.example.myjfshowkotlin.model

data class Book(val id:Int, val name:String, val url_Img:String, val rating: Double,val type:String) {
    override fun toString(): String {
        return super.toString()
    }
}
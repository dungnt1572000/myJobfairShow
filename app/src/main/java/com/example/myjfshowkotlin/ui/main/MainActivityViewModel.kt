package com.example.myjfshowkotlin.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myjfshowkotlin.model.Book
import com.example.myjfshowkotlin.model.User
import com.google.firebase.database.FirebaseDatabase

class MainActivityViewModel : ViewModel() {
    var mutableUser = MutableLiveData<User?>()
    var mutableBook = MutableLiveData<ArrayList<Book>>()
    var database = FirebaseDatabase.getInstance().getReference("Users")

    fun setMutableUserbyUser(user:User){
        mutableUser.value = user
    }
    fun setMutableListBookbyList(bookArrayList: ArrayList<Book>){
        mutableBook.value = bookArrayList
    }
    fun setMutableDataUser(str: String) {
        database.child(str).get().addOnSuccessListener {
            if (it.exists()) {
               val str_userurl = it.child("user_url").value as String?
                val user_age= Integer.parseInt(it.child("userAge").value.toString())
                val user_name = it.child("userName").value as String?
                val user_email = it.child("userEmail").value as String?
                val user_pass = it.child("userPass").value as String?
                var user = User(str_userurl,user_name,user_pass,user_age,user_email)
                mutableUser.value = user
                Log.e("Khong nULL", "it da Khong NULL ${user.userEmail} and ${user.userName}")
            } else {
                Log.e("Khong nULL", "it da null")
            }
        }.addOnFailureListener {
            mutableUser.value = null
        }
    }

    fun getMutableDataUser() = mutableUser
    @JvmName("getMutableBook1")
    fun getMutableBook() = mutableBook
}
package com.example.myjfshowkotlin.ui.Login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myjfshowkotlin.model.User
import com.google.firebase.database.FirebaseDatabase

class LoginViewModel:ViewModel(){
    var mutableUser = MutableLiveData<User>()
    lateinit var myuser: User
    fun setMutableUser(user: User){
        mutableUser.value = user
    }
    @JvmName("getMutableUser1")
    fun getMutableUser(): MutableLiveData<User>? {
        return mutableUser
    }

    fun getUserfromDb(string: String){
        val database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(string).get().addOnSuccessListener {
            if (it.exists()){
                Log.e("TAGG", it.value!!.javaClass.toString())
                Log.e("MyTag",it.value.toString())
                var userName = it.child("userName").value.toString()

                var userPass = it.child("userPass").value.toString()

                var userAge = it.child("userAge").value.toString().toInt()

                var user_url = it.child("user_url").value.toString()

                var userEmail = it.child("userEmail").value.toString()

                myuser = User(user_url,userName,userPass,userAge,userEmail)

                if (myuser==null){
                    Log.e("UserNULL","NULL")
                }else
                    mutableUser.value = myuser

            }else{
                Log.e("Tag","not exits")
            }
        }
    }


}
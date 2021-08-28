package com.example.myjfshowkotlin.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class Book_ViewModel:ViewModel() {
    private var soluongBook = MutableLiveData<Int>()
    fun setMutableSoluongBook(string: String){
        soluongBook.value = 1
        var database = FirebaseFirestore.getInstance()
        database.collection(string).get().addOnSuccessListener {
            soluongBook.value = it.size()
        }.addOnFailureListener {
            soluongBook.value = 1
        }
    }
    fun getMutableSoluongBook() = soluongBook
}
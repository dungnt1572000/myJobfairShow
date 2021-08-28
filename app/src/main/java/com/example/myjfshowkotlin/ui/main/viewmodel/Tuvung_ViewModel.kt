package com.example.myjfshowkotlin.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myjfshowkotlin.model.Book
import com.example.myjfshowkotlin.model.Tuvung
import com.example.myjfshowkotlin.ui.main.getData.getDataTuvung
import com.google.firebase.firestore.FirebaseFirestore

class Tuvung_ViewModel: ViewModel() {
    var database = FirebaseFirestore.getInstance()
    private var mutableStringBook= MutableLiveData<Book>()
    private var mutableTuvung = MutableLiveData<ArrayList<Tuvung>>()
    private var soluongTuvung = MutableLiveData<Int>()
    fun setBook(book: Book){
        mutableStringBook.value = book
    }
    fun getMutableBook() = mutableStringBook

    fun setListtuvungbyList(arrtuvung: ArrayList<Tuvung>){
        mutableTuvung.value = arrtuvung
    }
    fun inittoFindSoluongtuvunginBook(book: Book){
        soluongTuvung.value = 3
        database.collection(book.type).document(book.id.toString()).collection("tuvung").get().addOnSuccessListener {
            soluongTuvung.value = it.size()
        }.addOnFailureListener {
            soluongTuvung.value= 3
        }
    }
    fun getSoluongTuvung() = soluongTuvung
    private fun getsoluongtuvung(bookid: Int,type: String): Int {
        var soluong=0
        database.collection(type).document(bookid.toString()).collection("tuvung").get().addOnSuccessListener {
            soluong = it.size()
        }.addOnFailureListener {
            soluong=3
        }
        return soluong
    }

    fun setMutableListtuvung(){

    }

    fun getMutableTuvungList() = mutableTuvung
}
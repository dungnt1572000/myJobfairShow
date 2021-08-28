package com.example.myjfshowkotlin.ui.main.getData

import android.util.Log
import com.example.myjfshowkotlin.model.Book
import com.example.myjfshowkotlin.ui.main.adapter.BookAdapter
import com.google.firebase.firestore.FirebaseFirestore

class getDataBook {
    var database = FirebaseFirestore.getInstance()
    fun getdata(n:Int,string: String):BookAdapter{
        var arrBook = ArrayList<Book>()
        var bookAdapter = BookAdapter()
        Log.e("getData Book, this is String",string)
        try {
            for (i in 0 until n) {
                Log.e("So luong BOok trong getDataBook",i.toString())
                database.collection(string).document(i.toString()).get().addOnSuccessListener {
                    if (it!=null) {
                        Log.e("this is id: ",it.get("id").toString())
                        var id = it.get("id").toString().toInt()
                        var rating = it.get("rating").toString().toDouble()
                        var name = it.get("name").toString()
                        var type = it.get("type").toString()
                        var url_img = it.get("url_Img").toString()
                        var book = Book(id, name, url_img, rating, type)
                        arrBook.add(book)
                        arrBook.sortBy { book -> book.id }
                        bookAdapter.initBookData(arrBook)
                    }
                }
            }
        }catch (e:NullPointerException){
            var book = Book(1, "Nothing", "", 0.0, "None")
            arrBook.add(book)
            bookAdapter.initBookData(arrBook)
        }
        return  bookAdapter
    }

}
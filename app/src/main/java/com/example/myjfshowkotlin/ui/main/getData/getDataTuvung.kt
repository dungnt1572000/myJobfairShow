package com.example.myjfshowkotlin.ui.main.getData
import android.util.Log
import com.example.myjfshowkotlin.model.Tuvung
import com.example.myjfshowkotlin.ui.main.adapter.TuvungAdapter
import com.google.firebase.firestore.FirebaseFirestore

class getDataTuvung {
    var database = FirebaseFirestore.getInstance()
    fun getData(bookid:Int,n:Int,type:String): TuvungAdapter {
        var tuvungAdapter = TuvungAdapter()
        var tuvungArrayList = ArrayList<Tuvung>()
            for (i in 0 until n){
                database.collection(type).document(bookid.toString()).collection("tuvung").document(i.toString()).get().addOnSuccessListener {
                    it?.let {
                        var id = it.get("id").toString().toInt()
                        var imi = it.get("imi").toString()
                        var maucau = it.get("maucau").toString()
                        var name = it.get("name").toString()
                        var omoimasu = it.get("omoimasu").toString().toBoolean()
                        var tuvung = Tuvung(id,name, imi, maucau, omoimasu)
                        tuvungArrayList.add(tuvung)
                        Log.e("tu vung",tuvung.toString())
                        Log.e("SO luong tu vung: ",tuvungArrayList.size.toString())
                        tuvungArrayList.sortBy {tuvung.id }
                        tuvungAdapter.setData(tuvungArrayList)
                    }
                }
            }
        return tuvungAdapter
    }

    private fun getsoluongtuvung(bookid: Int,type: String): Int {
        var soluong=0
        database.collection(type).document(bookid.toString()).collection("tuvung").get().addOnSuccessListener {
            soluong = it.size()
        }.addOnFailureListener {
            soluong=3
        }
        return soluong
    }

}
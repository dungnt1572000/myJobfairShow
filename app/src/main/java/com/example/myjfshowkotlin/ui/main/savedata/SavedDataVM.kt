package com.example.myjfshowkotlin.ui.main.savedata

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myjfshowkotlin.model.Tuvung
import com.example.myjfshowkotlin.test.tuvungDatabase

class SavedDataVM(application: Application):AndroidViewModel(application) {
    private var mutableArrListSavedData = MutableLiveData<List<Tuvung>>()
    fun setMutableSaveData(application: Application){
        mutableArrListSavedData.value = tuvungDatabase.getInstance(application.applicationContext)?.mytuvungDAO()?.getAlltuvung()
    }
     fun getMutableSavedData() = mutableArrListSavedData
}
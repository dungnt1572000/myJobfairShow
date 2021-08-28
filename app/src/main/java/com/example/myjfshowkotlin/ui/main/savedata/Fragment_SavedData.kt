package com.example.myjfshowkotlin.ui.main.savedata

import android.app.Application
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.myjfshowkotlin.R
import com.example.myjfshowkotlin.model.Tuvung
import com.example.myjfshowkotlin.myInterface.clearTuvung
import com.example.myjfshowkotlin.test.tuvungDatabase
import com.example.myjfshowkotlin.ui.getClick
import com.example.myjfshowkotlin.ui.main.adapter.LocalTuvungAdapter
import com.example.myjfshowkotlin.ui.main.adapter.TuvungAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Fragment_SavedData : Fragment(),clearTuvung {
    lateinit var localTuvungAdapter: LocalTuvungAdapter
    lateinit var text_search : EditText
    lateinit var recyclerView: RecyclerView
    lateinit var arrTuvung : ArrayList<Tuvung>
    lateinit var viewmodel: SavedDataVM
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewmodel = ViewModelProvider(requireActivity()).get(SavedDataVM::class.java)
        var view:View = LayoutInflater.from(inflater.context).inflate(R.layout.fragment__saved_data,container,false)
        // Inflate the layout for this fragment
        recyclerView = view.findViewById(R.id.recycleViewSaveData)
        text_search = view.findViewById(R.id.text_search_tuvung)
        localTuvungAdapter = LocalTuvungAdapter()
        viewmodel.setMutableSaveData(context?.applicationContext as Application)
        viewmodel.getMutableSavedData().observe(viewLifecycleOwner, Observer {
            arrTuvung = ArrayList(it)
            localTuvungAdapter.setData(arrTuvung)
            localTuvungAdapter.setonclicktoclear(this)
        })
        text_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                var mList :List<Tuvung> = tuvungDatabase.getInstance(context!!)?.mytuvungDAO()!!findlocaltuvung(s.toString())
                localTuvungAdapter.setData((ArrayList(mList)))
                recyclerView.adapter = localTuvungAdapter
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        recyclerView.adapter = localTuvungAdapter
        return view
    }
    override fun clear(tuvung: Tuvung,positon:Int) {
        Log.e("Da xoa tu vung ",tuvung.toString())
        context?.let {
            tuvungDatabase.getInstance(it)?.mytuvungDAO()?.deleteTuvung(tuvung)
            arrTuvung.remove(tuvung)
        }
        localTuvungAdapter.notifyItemRemoved(positon)
        localTuvungAdapter.notifyItemRangeChanged(positon,arrTuvung.size)
    }
}
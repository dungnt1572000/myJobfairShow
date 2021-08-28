package com.example.myjfshowkotlin.ui.main.tuvungFragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.myjfshowkotlin.R
import com.example.myjfshowkotlin.databinding.FragmentTuvungBinding
import com.example.myjfshowkotlin.model.Tuvung
import com.example.myjfshowkotlin.myInterface.getintoRoomdb
import com.example.myjfshowkotlin.test.tuvungDatabase
import com.example.myjfshowkotlin.ui.main.adapter.TuvungAdapter
import com.example.myjfshowkotlin.ui.main.getData.getDataTuvung
import com.example.myjfshowkotlin.ui.main.viewmodel.Tuvung_ViewModel
import kotlin.properties.Delegates

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Fragment_Tuvung : Fragment(),getintoRoomdb {
    lateinit var viewModel: Tuvung_ViewModel
    private var soluongtuvungtrongbook by Delegates.notNull<Int>()
    private lateinit var recycleView:RecyclerView
     var data = ArrayList<Tuvung>()
     var tuvungAdapter = TuvungAdapter()
    private lateinit var binding: FragmentTuvungBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(Tuvung_ViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment__tuvung,container,false)
        recycleView = binding.tuvungRecycleView
        viewModel.getSoluongTuvung().observe(viewLifecycleOwner, Observer {
            soluongtuvungtrongbook = it
            Log.e("So luong tu vung trong book ",soluongtuvungtrongbook.toString())
        })
        Handler().postDelayed(Runnable {

            binding.waitWhenLoading.visibility = View.GONE
            viewModel.getMutableBook().observe(viewLifecycleOwner, Observer {
                Log.e("Book ten ",it.name)
                tuvungAdapter = getDataTuvung().getData(it.id,soluongtuvungtrongbook,it.type)
                tuvungAdapter.setOntoDB(this)
                recycleView.adapter = tuvungAdapter
            })
        },700)
        return binding.root
    }

    override fun onClicktoDB(tuvung: Tuvung) {
        requireContext().let {
            tuvungDatabase.getInstance(it)?.mytuvungDAO()?.insertTuvung(tuvung)
        }
        Log.e("Click Item Tuvung",tuvung.toString())
        Toast.makeText(context,"You can see it when offline",Toast.LENGTH_SHORT).show()
    }
}
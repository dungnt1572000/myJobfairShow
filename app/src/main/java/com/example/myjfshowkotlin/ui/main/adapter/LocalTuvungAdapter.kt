package com.example.myjfshowkotlin.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myjfshowkotlin.R
import com.example.myjfshowkotlin.databinding.LocalTuvungItemBinding
import com.example.myjfshowkotlin.model.Tuvung
import com.example.myjfshowkotlin.myInterface.clearTuvung

class LocalTuvungAdapter: RecyclerView.Adapter<LocalTuvungAdapter.LocalTuvungViewHolder>() {
    private var arrTuvung = ArrayList<Tuvung>()
    private lateinit var xoatuvung:clearTuvung
    fun setonclicktoclear(clearTuvung: clearTuvung){
        this.xoatuvung = clearTuvung
    }
    fun setData(arrayList: ArrayList<Tuvung>){
        this.arrTuvung = arrayList
        this.notifyDataSetChanged()
    }
    class LocalTuvungViewHolder(var binding: LocalTuvungItemBinding,var xoatuvung:clearTuvung) : RecyclerView.ViewHolder(binding.root){
        fun bind(tuvung: Tuvung){
            binding.tuvung = tuvung
            binding.imgCleartuvung.setOnClickListener {
                xoatuvung.clear(tuvung,adapterPosition)
            }
            binding.tuvungId.text = tuvung.id.toString()
            binding.tuvungNghia.text = tuvung.imi
            binding.tuvungMaucau.text = tuvung.maucau
            binding.tuvungName.text = tuvung.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalTuvungViewHolder {
        var binding:LocalTuvungItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.local_tuvung_item,parent,false)
        return LocalTuvungViewHolder(binding,xoatuvung)
    }

    override fun onBindViewHolder(holder: LocalTuvungViewHolder, position: Int) {
        var tuvung = arrTuvung[position]
        holder.bind(tuvung)
    }

    override fun getItemCount() = arrTuvung.size
}
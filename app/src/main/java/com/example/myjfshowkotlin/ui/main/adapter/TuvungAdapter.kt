package com.example.myjfshowkotlin.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myjfshowkotlin.R
import com.example.myjfshowkotlin.databinding.TuvungItemBinding
import com.example.myjfshowkotlin.model.Tuvung
import com.example.myjfshowkotlin.myInterface.getintoRoomdb

class TuvungAdapter:
    RecyclerView.Adapter<TuvungAdapter.TuvungViewHolder>() {
    private var arrTuvung = ArrayList<Tuvung>()
    private lateinit var getintoRoomDatabase:getintoRoomdb
   fun setData(arrTuvung: ArrayList<Tuvung>){
       this.arrTuvung = arrTuvung
       this.notifyDataSetChanged()
   }
    fun setOntoDB(myListener: getintoRoomdb){
        this.getintoRoomDatabase = myListener
    }
    class TuvungViewHolder(var binding:TuvungItemBinding,var getinfor:getintoRoomdb) : RecyclerView.ViewHolder(binding.root){
        fun bind(tuvung: Tuvung){
            binding.tuvung = tuvung
//            binding.checkBox.isChecked = tuvung.omoimasu
            binding.tvAddToDb.setOnClickListener {
                getinfor.onClicktoDB(tuvung)
            }
            binding.tuvungNghia.text = tuvung.imi
            binding.tuvungMaucau.text = tuvung.maucau
            binding.tuvungName.text = tuvung.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TuvungViewHolder {
        val binding:TuvungItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.tuvung_item,parent,false)
        return TuvungViewHolder(binding,getintoRoomDatabase)
    }

    override fun onBindViewHolder(holder: TuvungViewHolder, position: Int) {
        var tuvung = arrTuvung[position]
        holder.bind(tuvung)
    }

    override fun getItemCount() = arrTuvung.size

}
package com.example.myjfshowkotlin.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myjfshowkotlin.R
import com.example.myjfshowkotlin.databinding.BookItemBinding
import com.example.myjfshowkotlin.model.Book
import com.example.myjfshowkotlin.ui.getClick

class BookAdapter :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    private lateinit var onClick: getClick
    private var bookItemList = ArrayList<Book>()
    fun initBookData(arrayList: ArrayList<Book>){
        this.bookItemList = arrayList
        this.notifyDataSetChanged()
    }
    fun setOnItemClickListener(listener: getClick){
        onClick = listener
    }
    class BookViewHolder(val binding: BookItemBinding,var listener: getClick) : RecyclerView.ViewHolder(binding.root){

        fun bin(book:Book){
            binding.bookRating.rating = book.rating.toFloat()
            binding.bookName.text = book.name
            Glide.with(binding.imgBook.context)
                .load(book.url_Img)
                .error(R.drawable.loading_img)
                .into(binding.imgBook)
            binding.bookRating.rating = book.rating.toFloat()
            binding.cardviewBook.setOnClickListener {
                listener.onClick(book)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
       val binding:BookItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context)
           ,R.layout.book_item,parent,false)
        return BookViewHolder(binding,onClick)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        var book = bookItemList[position]
        holder.bin(book)
    }

    override fun getItemCount() = bookItemList.size
}
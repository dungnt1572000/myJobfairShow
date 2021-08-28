package com.example.myjfshowkotlin.ui.main.love

import android.os.Bundle
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
import com.example.myjfshowkotlin.databinding.FragmentHanhdongBinding
import com.example.myjfshowkotlin.databinding.FragmentLoveBinding
import com.example.myjfshowkotlin.model.Book
import com.example.myjfshowkotlin.ui.getClick
import com.example.myjfshowkotlin.ui.main.adapter.BookAdapter
import com.example.myjfshowkotlin.ui.main.getData.getDataBook
import com.example.myjfshowkotlin.ui.main.tuvungFragment.Fragment_Tuvung
import com.example.myjfshowkotlin.ui.main.viewmodel.Book_ViewModel
import com.example.myjfshowkotlin.ui.main.viewmodel.Tuvung_ViewModel
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment_Love.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment_Love : Fragment(),getClick {
    private lateinit var binding: FragmentLoveBinding
    private lateinit var recycleView:RecyclerView
    private lateinit var viewmodel: Book_ViewModel
    lateinit var viewModel2: Tuvung_ViewModel
    private var bookAdapter = BookAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        Toast.makeText(context,"Welcome",Toast.LENGTH_SHORT).show()

        viewmodel = ViewModelProvider(requireActivity()).get(Book_ViewModel::class.java)
        viewModel2 = ViewModelProvider(requireActivity()).get(Tuvung_ViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment__love,container,false)

        recycleView = binding.rycleView
        viewmodel.getMutableSoluongBook().observe(viewLifecycleOwner, Observer {
            Log.e("it", it.toString())
            if (it>0){
                binding.waitWhenLoading.visibility = View.GONE
            }
            bookAdapter = getDataBook().getdata(it,"tinhyeu")
            bookAdapter.setOnItemClickListener(this)
                recycleView.adapter = bookAdapter
                binding.waitWhenLoading.visibility = View.GONE
        })
        return binding.root
    }

    override fun onClick(book: Book) {
        viewModel2.setBook(book)
        viewModel2.inittoFindSoluongtuvunginBook(book)
        Log.e("Book la: ",book.name)
        parentFragmentManager.beginTransaction().replace(R.id.layout_for_fragment,
            Fragment_Tuvung())
            .addToBackStack("fragment_tuvung")
            .commit()
    }

//    fun getData(n:Int, string:String): ArrayList<Book>{
//        var arrayList = ArrayList<Book>()
//        for (i in 0 until  n-1){
//
//        }
//    }
}
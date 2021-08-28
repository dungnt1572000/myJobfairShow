package com.example.myjfshowkotlin.ui.main.profile

import android.app.Activity
import android.content.Intent
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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myjfshowkotlin.R
import com.example.myjfshowkotlin.databinding.FragmentProfileBinding
import com.example.myjfshowkotlin.model.User
import com.example.myjfshowkotlin.ui.main.MainActivityViewModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class Fragment_Profile : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    lateinit var viewModel: MainActivityViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        viewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment__profile, container, false)
        // Inflate the layout for this fragment
        viewModel.getMutableDataUser().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Glide.with(this).load(it.user_url)
                    .error(R.drawable.background_changename)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(binding.studentImg)
            }
            binding.user = it
        })
        binding.btnChangeProfile.setOnClickListener {
            if (err_valid(binding.tvTuoi.text.toString())==0 || err_valid(binding.editEmail.text.toString())==0) {
                var user_name = binding.user?.userName
                var user_pass = binding.user?.userPass
                var user_email = binding.editEmail.text.toString()
                var user_Age = binding.tvTuoi.text.toString().toInt()
                var user_url = binding.user?.user_url
                var user = User(user_url, user_name, user_pass, user_Age, user_email)
                viewModel.setMutableUserbyUser(user)
                Toast.makeText(activity, "OKE", Toast.LENGTH_LONG).show()
                updatetoDB(user)
                var  intent = Intent()
                activity?.setResult(Activity.RESULT_OK,intent)
                activity?.finish()
            }
        }
        binding.tvChangePass.setOnClickListener {
            binding.btnChangeProfile.visibility = View.GONE
            binding.passChangeLayout.visibility = View.VISIBLE
            binding.myBtnUpdateAll.visibility = View.VISIBLE
            binding.myBtnUpdateAll.setOnClickListener {
                if (err_valid(binding.tvTuoi.text.toString())==0 || err_valid(binding.editEmail.text.toString())==0) {
                    var user_name = binding.user?.userName
                    var user_pass = binding.editNewpass.text.toString()
                    var user_email = binding.editEmail.text.toString()
                    var user_Age = binding.tvTuoi.text.toString().toInt()
                    var user_url = binding.user?.user_url
                    var user = User(user_url, user_name, user_pass, user_Age, user_email)
                    viewModel.setMutableUserbyUser(user)
                    updatetoDB(user)
                    var intent = Intent()
                    activity?.setResult(Activity.RESULT_OK,intent)
                    activity?.finish()
                }else
                    Toast.makeText(activity,"Edit full pls",Toast.LENGTH_LONG).show()
            }
        }
        return binding.root
    }

    fun err_valid(string: String):Int{
        if (string.isEmpty())
            return 1
        return 0
    }

    fun updatetoDB(user: User){
        val database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(user.userName!!).setValue(user).addOnSuccessListener {
            Toast.makeText(activity, "OKE", Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            Toast.makeText(activity,"Edit full pls",Toast.LENGTH_LONG).show()
        }
    }

}
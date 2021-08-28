package com.example.myjfshowkotlin.ui.Login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.myjfshowkotlin.R
import com.example.myjfshowkotlin.databinding.FragmentGetBackPassBinding
import com.google.firebase.database.FirebaseDatabase


class FragmentGetBackPass : Fragment() {
    private val database = FirebaseDatabase.getInstance().getReference("Users")
    lateinit var binding: FragmentGetBackPassBinding
    var kcheckbtn: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_get_back_pass, container, false)
        val view: View = binding.root

        binding.btnFindPass.setOnClickListener {
            var username = binding.editTextTextPersonName.text.toString()
            var useremail = binding.editTextTextPersonName2.text.toString()
            getCheck(username,useremail)
        }
        // Inflate the layout for this fragment
        binding.btnChangePass.setOnClickListener {

            var username = binding.editTextTextPersonName.text.toString()
            var userPass = binding.editNewpass.text.toString()
            var userrepass = binding.editRepass.text.toString()
            if (userPass == userrepass){
                changePass(username,userPass)
            }else{
                binding.tvError.text = "2 Password not same"
                binding.imgCheck.setImageResource(R.drawable.ic_err)
            }
        }

        return view
    }

    private fun changePass(username: String,userPass: String) {
        database.child(username).child("userPass").setValue(userPass)
            .addOnSuccessListener {
                Toast.makeText(activity,"Changes Succed",Toast.LENGTH_LONG).show()
                parentFragmentManager.popBackStack()
            }
            .addOnFailureListener {
                Toast.makeText(activity,"Some thing went Wrong",Toast.LENGTH_LONG).show()
            }

    }

    private fun getCheck(username: String, email: String) {
        database.child(username).get().addOnSuccessListener {
            if (it.exists()) {
                if (email == it.child("userEmail").value) {
                    binding.tvError.visibility = View.VISIBLE
                    binding.tvError.text = "Success! Now u can change Password"
                    binding.imgCheck.setImageResource(R.drawable.ic_check_right)
                    binding.passChangeLayout.visibility = View.VISIBLE
                    binding.btnChangePass.visibility = View.VISIBLE
                    binding.btnFindPass.visibility = View.GONE
                } else {
                    binding.tvError.visibility = View.VISIBLE
                    binding.tvError.text = "Your Email maybe wrong"
                    binding.imgCheck.setImageResource(R.drawable.ic_err)
                }
            } else {
                binding.tvError.visibility = View.VISIBLE
                binding.tvError.text = "Your User Name maybe wrong"
                binding.imgCheck.setImageResource(R.drawable.ic_err)
            }
        }
    }

}
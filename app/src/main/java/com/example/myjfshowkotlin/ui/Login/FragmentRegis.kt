package com.example.myjfshowkotlin.ui.Login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myjfshowkotlin.R
import com.example.myjfshowkotlin.databinding.FragmentRegisBinding
import com.example.myjfshowkotlin.model.User
import com.google.firebase.database.FirebaseDatabase


class FragmentRegis : Fragment() {
    private val regexPatterns = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()

    private lateinit var binding: FragmentRegisBinding
    private var viewModel: LoginViewModel? = null
    private val database = FirebaseDatabase.getInstance().getReference("Users")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        viewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_regis, container, false)
        val view: View = binding.root
        binding.btnRegis.setOnClickListener {
            var user = getUser()
            database.child(user.userName.toString()).get().addOnSuccessListener {
                if (it.exists()) {
                    Toast.makeText(activity, "Tai Khoan nay da ton tai", Toast.LENGTH_LONG).show()
                } else {
                    addUsertoDB(user)
                }
            }
        }
        binding.toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return view
    }

    private fun addUsertoDB(user: User) {
        if (!error_passnotsame(
                binding.password.text.toString(),
                binding.editRepass.text.toString()
            )
            && setTextonchange() == 0
        ) {
            database.child(user.userName.toString()).setValue(user)
                .addOnSuccessListener {

                    viewModel!!.setMutableUser(user)
                    Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
                    parentFragmentManager.beginTransaction().replace(R.id.content_layout,
                        Fragment_Login()
                    )
                        .commit()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failure", Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun getUser(): User {
        val user_name = binding.userName.text.toString()
        val user_pass = binding.password.text.toString()
        val user_age = binding.userAge.text.toString().toInt()
        val user_email = binding.userEmail.text.toString()
        val user = User("", user_name, user_pass, user_age, user_email)
        return user
    }

    fun error_passnotsame(str: String, str1: String): Boolean {
        if (!str1.equals(str)) {
            binding.repass.error = "2 Pass Not Same"
            return true
        } else
            binding.repass.helperText = "OK"
        return false
    }

    fun setTextonchange(): Int {


        if (!binding.userAge.text!!.isDigitsOnly()) {
            binding.textInputLayout5.error = "Only Digital"
            return 1
        }
        if (!binding.userEmail.text!!.matches(regexPatterns)) {
            binding.textInputLayout6.error = "Email Wrong"
            return 2
        }
        return 0
    }


}



package com.example.myjfshowkotlin.ui.Login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myjfshowkotlin.R
import com.example.myjfshowkotlin.databinding.FragmentLoginBinding
import com.example.myjfshowkotlin.ui.main.MainActivity
class Fragment_Login : Fragment() {
    lateinit var binding: FragmentLoginBinding
    private var viewModel: LoginViewModel? = null
    lateinit var sharePreference: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        sharePreference = context?.getSharedPreferences(myfile, Context.MODE_PRIVATE)!!
        editor = sharePreference.edit()
        viewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment__login, container, false)
        val view: View = binding.root
        // Inflate the layout for this fragment
        binding.tvRegis.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.content_layout, FragmentRegis())
                .addToBackStack("regis_fragment")
                .commit()
        }

        binding.button.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            Handler().postDelayed(Runnable {
                getData(binding.MyuserName.text.toString(), binding.MyuserPass.text.toString())
                binding.progressBar.visibility = View.GONE
            },2000)
        }

        binding.tvTakePasswordBack.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.content_layout, FragmentGetBackPass())
                .addToBackStack("getdata_fragment")
                .commit()
        }
        return view
    }

    private fun getData(str: String, str2: String) {
        viewModel!!.getUserfromDb(str)
        viewModel!!.mutableUser.observe(viewLifecycleOwner, Observer {
            Log.e("Pass","My pass: "+str2+"  "+it.userPass)
            if (str2 == it.userPass) {
                if (binding.checkboxSave.isChecked){
                    editor.putString(PREF_USER_NAME, binding.MyuserName.text.toString())
                    editor.putString(PREF_USER_PASS, binding.MyuserPass.text.toString())
                    editor.putBoolean(PREF_BOOLEAN_SAVE,true)
                }else{
                    editor.putString(PREF_USER_PASS, "")
                    editor.putBoolean(PREF_BOOLEAN_SAVE, false)
                    Toast.makeText(activity, "Wrong Password", Toast.LENGTH_LONG).show()
                }
                Toast.makeText(context,"Login Success",Toast.LENGTH_LONG).show()
                var intent = Intent(activity, MainActivity::class.java)
                intent.putExtra("user_name", binding.MyuserName.text.toString())
                startActivity(intent)
            } else {
                editor.putString(PREF_USER_NAME,"")
                editor.putString(PREF_USER_PASS, "")
                editor.putBoolean(PREF_BOOLEAN_SAVE, false)
            }
            editor.apply()
        })
    }

}
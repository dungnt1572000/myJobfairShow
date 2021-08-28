package com.example.myjfshowkotlin.ui.main

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.myjfshowkotlin.ui.main.profile.Fragment_Profile
import com.example.myjfshowkotlin.R
import com.example.myjfshowkotlin.databinding.ActivityMainBinding
import com.example.myjfshowkotlin.loadingdialog.LoadingDialog
import com.example.myjfshowkotlin.model.User
import com.example.myjfshowkotlin.ui.main.hanhdong.Fragment_Hanhdong
import com.example.myjfshowkotlin.ui.main.love.Fragment_Love
import com.example.myjfshowkotlin.ui.main.phepthuat.Fragment_PhepThuat
import com.example.myjfshowkotlin.ui.main.savedata.Fragment_SavedData
import com.example.myjfshowkotlin.ui.main.viewmodel.Book_ViewModel
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var viewmodelBook: Book_ViewModel
    private lateinit var headerView:View
    private lateinit var myUser: User
    private val FRAGMENT_SAVEDDATA = 5
    private val FRAGEMNT_HANHDONG =1
    private val FRAGMENT_PHEPTHUAT = 2
    private val FRAGMENT_LOVE = 3
    private val FRAGMENT_PROFILE = 4
    private var FRAGMENT_CURRENT = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodelBook = ViewModelProvider(this).get(Book_ViewModel::class.java)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        var user_name: String? = intent.getStringExtra("user_name")

        if (user_name != null) {
            Log.e("UserName",user_name)
            viewModel.setMutableDataUser(user_name)
        }

        binding =  DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        headerView = binding.navigationView.getHeaderView(0)
        initHeaderView()
        binding.navigationView.setNavigationItemSelectedListener(this)
        val actionBarDrawerToggle = ActionBarDrawerToggle(this,binding.drawerLayout,binding.toolbar,
            R.string.drawer_open,
            R.string.drawer_close)
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        setTitleforToolbar()
    }

    private fun initHeaderView() {
        var img:ImageView = headerView.findViewById(R.id.imgavatar)
        var textName: TextView = headerView.findViewById(R.id.textView)
        var textEmail:TextView = headerView.findViewById(R.id.textView11)
        viewModel.getMutableDataUser().observe(this, Observer {
            Glide.with(this).load(it?.user_url).error(R.drawable.background_changename).into(img)
            textName.text = it?.userName
            textEmail.text = it?.userEmail
            if (it!=null){
                viewmodelBook.setMutableSoluongBook("hanhdong")
                replace_fragment(Fragment_Hanhdong())
            }
            else{
               val intent = intent
                finish()
                startActivity(intent)
            }
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_hanhdong ->{
                if (FRAGMENT_CURRENT != FRAGEMNT_HANHDONG){
                    viewmodelBook.setMutableSoluongBook("hanhdong")
                    replace_fragment(Fragment_Hanhdong())
                    FRAGMENT_CURRENT = FRAGEMNT_HANHDONG
                }
            }

            R.id.nav_phepthuat ->{
                if (FRAGMENT_CURRENT!=FRAGMENT_PHEPTHUAT){
                    viewmodelBook.setMutableSoluongBook("phepthuat")
                    replace_fragment(Fragment_PhepThuat())

                    FRAGMENT_CURRENT = FRAGMENT_PHEPTHUAT
                }
            }

            R.id.nav_tinhyeu ->{
                if (FRAGMENT_CURRENT!=FRAGMENT_LOVE){
                    viewmodelBook.setMutableSoluongBook("tinhyeu")
                    replace_fragment(Fragment_Love())

                    FRAGMENT_CURRENT = FRAGMENT_LOVE
                }
            }

            R.id.nav_profile -> {
                if (FRAGMENT_CURRENT!=FRAGMENT_PROFILE){
                    replace_fragment(Fragment_Profile())
                    FRAGMENT_CURRENT = FRAGMENT_PROFILE
                }
            }

            R.id.nav_luutru ->{
                if (FRAGMENT_CURRENT!=FRAGMENT_SAVEDDATA){
                    replace_fragment(Fragment_SavedData())
                    FRAGMENT_CURRENT = FRAGMENT_SAVEDDATA
                }
            }

            R.id.nav_logout->{
                var intent = Intent()
                setResult(Activity.RESULT_OK,intent)
                finish()
            }

        }
        setTitleforToolbar()
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }else
            super.onBackPressed()
    }

    private fun replace_fragment(fragment:Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.layout_for_fragment,fragment)
            .addToBackStack(fragment.toString())
            .commit()
    }

    private fun setTitleforToolbar(){
        var title = ""
        when(FRAGMENT_CURRENT){
            1-> title = "Hanh Dong"
            2->title = "Phap thuat"
            3-> title = "Love"
            4-> title = "Profile"
            5-> title = "Saved Data"
        }
        if (supportActionBar!=null){
            supportActionBar?.title = title
        }
    }
}



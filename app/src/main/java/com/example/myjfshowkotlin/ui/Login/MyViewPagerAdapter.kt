package com.example.myjfshowkotlin.ui.Login

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyViewPagerAdapter(activity: LoginActivity, val itemsCount: Int): FragmentStateAdapter(activity) {
    override fun getItemCount() = itemsCount

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> Fragment_Login()
            1 -> FragmentRegis()
            else -> Fragment_Login()
        }
    }
}
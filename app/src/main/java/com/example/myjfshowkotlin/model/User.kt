package com.example.myjfshowkotlin.model
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.myjfshowkotlin.R

import java.io.Serializable


class User : Serializable {
    private var user_img_url: String? = null
    private var user_name: String? = null
    private var user_pass: String? = null
    private var user_age: Int = 1
    private var user_email: String? = null
     var userName: String? = ""
        get() = user_name
     var userPass: String? = ""
        get() = user_pass
     var userAge: Int = 1
        get() = user_age
     var userEmail: String? = ""
        get() = user_email
     var user_url: String? = ""
        get() = user_img_url

    //    constructor(user_name: String?, user_pass: String?, user_age: Int, user_email: String?) {
//        this.user_name = user_name
//        this.user_pass = user_pass
//        this.user_age = user_age
//        this.user_email = user_email
//    }
    constructor()

    override fun toString(): String {
        return super.toString()
    }

    constructor(
        user_img_url: String?,
        user_name: String?,
        user_pass: String?,
        user_age: Int,
        user_email: String?,
    ) {
        this.user_img_url = user_img_url
        this.user_name = user_name
        this.user_pass = user_pass
        this.user_age = user_age
        this.user_email = user_email
    }

    companion object{
        @BindingAdapter("getImg")
        fun getImg(img:ImageView,string: String){
            if (string==""){
                img.setImageResource(R.drawable.background_changename)
            }else{
                Glide.with(img.context).load(string).into(img)
            }
        }
    }
}
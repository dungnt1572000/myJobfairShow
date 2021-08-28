package com.example.myjfshowkotlin.ui.Login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myjfshowkotlin.ui.main.MainActivity
import com.example.myjfshowkotlin.R
import kotlin.system.exitProcess
const val myfile = "pref_file"
const val PREF_USER_NAME = "myUserName"
const val PREF_USER_PASS = "myUserPass"
const val PREF_BOOLEAN_SAVE = "mySaved"
class LoginActivity : AppCompatActivity() {
    val REQUEST_CODE = 1
    private  var isLogin :Boolean = true
    private lateinit var sharePreference: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    var checkLogin:Int = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Log.e("OnCreate","Oncraet")
//        SharePrefQuery().init(applicationContext)
         sharePreference = applicationContext.getSharedPreferences(myfile,Context.MODE_PRIVATE)
        editor = sharePreference.edit()
    }

    override fun onResume() {
        super.onResume()
        Log.e("onResume","onResume")
        Log.e("checkLogin",checkLogin.toString())
        if (checkLogin==1){
            isLogin = sharePreference.getBoolean(PREF_BOOLEAN_SAVE,false)
            Log.e("LoginActivity",isLogin.toString())
            if (isLogin) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("user_name",sharePreference.getString(PREF_USER_NAME,""))
                startActivityForResult(intent,REQUEST_CODE)
            } else {
                Log.e("isLogin","False")
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.content_layout, Fragment_Login())
                    .addToBackStack("login_fragment")
                    .commit()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("onActiviResult", "onActivity for result")
        editor.putBoolean(PREF_BOOLEAN_SAVE,false)
        editor.apply()
    }

    override fun onBackPressed() {
        var count: Int = supportFragmentManager.backStackEntryCount
        if (count == 1) {
            finish()
            exitProcess(0)
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}
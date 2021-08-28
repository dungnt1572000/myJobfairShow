package com.example.jfshowkotlin.ui.Shash


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.myjfshowkotlin.ui.Login.LoginActivity
import com.example.myjfshowkotlin.R


class SlashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slash)
        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        progressBar.progress = 0
        Handler().postDelayed(Runnable {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }, 2000)
    }

}
package com.example.ishaqfakhrozi.lololonomic

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.widget.TextView

class SplashActivity:AppCompatActivity() {
    lateinit var tvSplash: TextView

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash)
        tvSplash = findViewById(R.id.tvSplash) as TextView
        val handler = Handler()
        handler.postDelayed(object:Runnable {
            public override fun run() {
                startActivity(Intent(getApplicationContext(), LoginActivity::class.java))
                finish()
            }
        }, 3000L) //3000 L = 3 detik
    }
}

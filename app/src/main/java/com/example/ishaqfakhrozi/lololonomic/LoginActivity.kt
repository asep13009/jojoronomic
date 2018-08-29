package com.example.ishaqfakhrozi.lololonomic


import android.support.v7.app.AppCompatActivity

import android.os.Bundle
import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import android.widget.*

/**
 * A login screen that offers login via email/password.
 */
    class LoginActivity : AppCompatActivity(){
        lateinit var input_email : EditText
        lateinit var  input_password : EditText
        lateinit var  btn_reset : Button
        lateinit var sharedPreferences:SharedPreferences


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_login)
            var session = SessionManager(applicationContext)
            if (session.isLoggedIn()){
                var i : Intent = Intent(applicationContext,MainActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(i)
                finish()
            }
            // get reference to all views
            var et_user_name = findViewById(R.id.input_email) as EditText
            var et_password = findViewById(R.id.input_password) as EditText
            var btn_reset = findViewById(R.id.link_signup) as TextView
            var btn_submit = findViewById(R.id.btn_login) as Button

//            btn_reset.setOnClickListener {
//                // clearing user_name and password edit text views on reset button click
//                et_user_name.setText("ojik")
//                et_password.setText("ojik")
//            }

            // set on-click listener
            btn_submit.setOnClickListener(object : View.OnClickListener{
                override fun onClick (v:View?){
                    var username: String= et_user_name.text.toString()
                    var password: String =et_password.text.toString()
                    if(username.trim().length>0 && password.trim().length>0)
                    {
                        session.createLoginSession(username,username)
                        if(username.equals("admin")&&password.equals("admin"))
                        {
                            var i :Intent = Intent(applicationContext,MainActivity::class.java)
                            startActivity(i)
                            finish()
                        }else
                        {
                            Toast.makeText(this@LoginActivity,"Login Failed",Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(this@LoginActivity,"Harus Diisi",Toast.LENGTH_SHORT).show()
                    }
                }

                // your code to validate the user_name and password combination
                // and verify the same

            })
        }
    }



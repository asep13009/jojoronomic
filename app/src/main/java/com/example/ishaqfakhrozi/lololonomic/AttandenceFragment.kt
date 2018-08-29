package com.example.ishaqfakhrozi.lololonomic

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

class AttandenceFragment : Fragment() {
    val button: Button? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_attandence, container, false)
        val btn_absen = v.findViewById(R.id.btn_absen) as Button
        btn_absen.setOnClickListener() {

               val intent = Intent(getActivity(), MapsActivity::class.java)
                       startActivity(intent)

        }

        return v

        }
    }


package com.example.ishaqfakhrozi.lololonomic

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var btnLogout :Button
    lateinit var session : SessionManager
    private var drawer: DrawerLayout? = null
//Session
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        session = SessionManager(applicationContext)
        session.checkLogin()

        var user : HashMap<String,String> =session.getUserDetails()
        var name : String =user.get(SessionManager.KEY_NAME)!!
        var email : String= user.get(SessionManager.KEY_EMAIL)!!
//Session
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawer = findViewById(R.id.drawer_layout)


        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        var header:View =navigationView.getHeaderView(0)
        var lblName : TextView = header.findViewById(R.id.lblName)as TextView
        var lblEmail: TextView = header.findViewById(R.id.lblEmail) as TextView
        lblName.setText(name)
        lblEmail.setText(email)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawer!!.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_contrainer, HomeFragment()).commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }
    }
    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.nav_home -> supportFragmentManager.beginTransaction().replace(R.id.fragment_contrainer,HomeFragment()).commit()
            R.id.nav_attendance ->  {val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent) }
                    R.id.nav_cash_advance -> supportFragmentManager.beginTransaction().replace(R.id.fragment_contrainer, CashAdvanceFragment()).commit()
            R.id.nav_reimbursement-> supportFragmentManager.beginTransaction().replace(R.id.fragment_contrainer, RembrushFragment()).commit()
//            R.id.nav_report -> supportFragmentManager.beginTransaction().replace(R.id.fragment_contrainer, ReportFragment()).commit()
            R.id.nav_logout -> session.LogoutUser()



        }
        drawer!!.closeDrawer(GravityCompat.START)
        return true
    }
}

package com.quick.asthkt.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationMenu
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.quick.asthkt.R

class DashboardActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        supportActionBar?.hide()
        auth = FirebaseAuth.getInstance()
        val mainFrame : FrameLayout = findViewById(R.id.mainframe)
        val navbar : BottomNavigationView = findViewById(R.id.navbar)
        navbar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val fragment = DiscoveryFragment()
        addFragment(fragment)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.discovery -> {
                val fragment = DiscoveryFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.profile -> {
                val fragment = ProfileFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.setting -> {
                val fragment = SettingFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            else -> false
        }

    }

    private fun addFragment(fragment : Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainframe, fragment, fragment.javaClass.simpleName)
            .commit()
    }

}
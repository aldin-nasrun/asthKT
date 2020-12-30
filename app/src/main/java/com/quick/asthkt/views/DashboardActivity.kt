package com.quick.asthkt.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.quick.asthkt.R

class DashboardActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        supportActionBar?.hide()
        auth = FirebaseAuth.getInstance()
        val btn_logout : Button = findViewById(R.id.btn_logout)

        btn_logout.setOnClickListener {
            logoutUser()
        }
    }

    fun logoutUser(){
        auth.signOut()
        startActivity(Intent(this@DashboardActivity, LoginActivity::class.java))
    }
}
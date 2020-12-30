package com.quick.asthkt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.quick.asthkt.views.DashboardActivity
import com.quick.asthkt.views.LoginActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
//        startActivity(Intent(this@MainActivity, AddDataActivity::class.java))
        GlobalScope.launch {
            delay(2000)
            if (this@MainActivity.auth.currentUser !== null) {
                startActivity(Intent(this@MainActivity, DashboardActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            }
        }
    }

}
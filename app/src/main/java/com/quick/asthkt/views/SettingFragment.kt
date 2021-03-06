package com.quick.asthkt.views

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.quick.asthkt.R


class SettingFragment : Fragment() {
    lateinit var btn_logout : Button
    val auth = FirebaseAuth.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v : View = inflater.inflate(R.layout.fragment_setting, container, false)
        btn_logout = v.findViewById(R.id.btn_logout)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_logout.setOnClickListener {
            logoutUser()
        }
    }
    private fun logoutUser(){
        auth.signOut().also {
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }
    }
}
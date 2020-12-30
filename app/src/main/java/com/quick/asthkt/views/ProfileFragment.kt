package com.quick.asthkt.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.quick.asthkt.R


class ProfileFragment : Fragment() {
    private lateinit var auth : FirebaseAuth
    lateinit var usernameText : TextView
    lateinit var usermailText : TextView
    lateinit var sp_mood : Spinner
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v : View = inflater.inflate(R.layout.fragment_profile, container, false)
        usernameText = v.findViewById(R.id.tv_username)
        usermailText = v.findViewById(R.id.tv_userEmail)
        sp_mood = v.findViewById(R.id.sp_mood)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        var moods = arrayOf("Senang", "Sedih", "Bingung", "Kasmaran", "Lelah", "Sakit")
        val NEW_SPINNER_ID = 1

        usernameText.text = auth.currentUser?.displayName.toString()
        usermailText.text = auth.currentUser?.email.toString()

        ArrayAdapter.createFromResource(context!!, R.array.moods, R.layout.layout_spinner).also {
            arrayAdapter ->
            arrayAdapter.setDropDownViewResource(R.layout.layout_spinner)
            sp_mood.adapter = arrayAdapter;
        }

    }
}
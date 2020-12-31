package com.quick.asthkt.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.quick.asthkt.R
import com.quick.asthkt.models.TwitData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class ProfileFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var auth : FirebaseAuth
    private val TAG = "ProfileFragment"
    lateinit var usernameText : TextView
    lateinit var usermailText : TextView
    lateinit var sp_mood : Spinner
    lateinit var btn_send : Button
    lateinit var et_twit : EditText
    var db = FirebaseFirestore.getInstance()
//    private val personCollectionRef = Firebase.firestore.collection("twit")
    private val asthCollectionRef = Firebase.firestore
    var moodSelected : String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v : View = inflater.inflate(R.layout.fragment_profile, container, false)
        usernameText = v.findViewById(R.id.tv_username)
        usermailText = v.findViewById(R.id.tv_userEmail)
        sp_mood = v.findViewById(R.id.sp_mood)
        btn_send = v.findViewById(R.id.btn_send)
        et_twit = v.findViewById(R.id.et_twit)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        usernameText.text = auth.currentUser?.displayName.toString()
        usermailText.text = auth.currentUser?.email.toString()

        ArrayAdapter.createFromResource(context!!, R.array.moods, R.layout.layout_spinner).also {
            arrayAdapter ->
            arrayAdapter.setDropDownViewResource(R.layout.layout_spinner)
            sp_mood.adapter = arrayAdapter
            sp_mood.onItemSelectedListener = this
        }

        btn_send.setOnClickListener {
            Toast.makeText(context, moodSelected, Toast.LENGTH_SHORT).show()
            getTwit()
        }

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
       moodSelected = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        moodSelected = "normal"
    }

    fun getTwit(){
        if (et_twit.getText().toString().equals("")){
            Toast.makeText(context, " katakan sesuatu ", Toast.LENGTH_SHORT).show()
        }else {


            val sdf = SimpleDateFormat("dd/MM/yy - hh.mm aa")
            val realTime = SimpleDateFormat("yyyyMMddhhmmss")
            val currentDate = sdf.format(Date())
            val realDate = realTime.format(Date())

            var twit = TwitData(
                auth.currentUser?.displayName.toString(),
                et_twit.getText().toString(),
                moodSelected,
                currentDate,
                realDate
            )
            sendTwit(twit)
        }
    }

    private fun sendTwit(twit: TwitData) = CoroutineScope(Dispatchers.IO).launch{
        try {
            asthCollectionRef.collection("twit")
                .add(twit).await()
            withContext(Dispatchers.Main){
                Toast.makeText(context, "Success add twits", Toast.LENGTH_SHORT).show()
            }

        }catch (e:Exception){
            Log.d(TAG, "sendTwit: "+e.localizedMessage)
            Toast.makeText(context, "something wrong", Toast.LENGTH_SHORT).show()
        }
    }
}
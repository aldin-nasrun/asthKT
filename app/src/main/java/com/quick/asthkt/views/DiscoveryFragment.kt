package com.quick.asthkt.views

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.quick.asthkt.R
import com.quick.asthkt.models.TwitDatabaseOffline
import com.quick.asthkt.viewmodels.TwitAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class DiscoveryFragment : Fragment() {
    private val db = FirebaseFirestore.getInstance()
    lateinit var rv_list: RecyclerView
    lateinit var mAdapter: TwitAdapter
    lateinit var helperTwit : TwitDatabaseOffline
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_discovery, container, false)
        rv_list = v.findViewById(R.id.rv_listTwit)
        return v

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        initView()
        helperTwit = TwitDatabaseOffline(context)
        helperTwit.delete()
//        helperTwit.makeTable()
        getData()
    }

    private fun getData() = CoroutineScope(Dispatchers.IO).launch {
        db.collection("twit")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        val value = ContentValues()
                        value.put("documentID", document.id)
                        value.put("username", document.data["username"].toString())
                        value.put("mood", document.data["mood"].toString())
                        value.put("twit", document.data["twit"].toString())
                        value.put("date", document.data["date"].toString())
                        value.put("real_date", document.data["realdate"].toString())
                        helperTwit.insertData(value)
                    }
                }
//                initView()
            }.await()
        withContext(Dispatchers.Main){
            initView()
        }
    }

    fun popUpEdit(docID : String , uname : String, twits : String, ctx : Context){
        AlertDialog.Builder(ctx,  R.style.CustomAlertDialog)
            .setTitle("DELETE TWITS?")
            .setMessage("are you sure ?")
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                deleteTwits(docID)
            })
            .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            }).show()
    }

    private fun deleteTwits(docID: String) = CoroutineScope(Dispatchers.IO).launch {
        db.collection("twit").document(docID)
            .delete()
            .addOnCompleteListener {

            }

    }


    private fun initView() {
        mAdapter = TwitAdapter(helperTwit.select(), this, context!!)
        rv_list.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}
package com.quick.asthkt.viewmodels

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.quick.asthkt.R
import com.quick.asthkt.views.DiscoveryFragment

class TwitAdapter(
    c: Cursor?,
    discoveryFragment : DiscoveryFragment?,
    ctc : Context
) : RecyclerView.Adapter<TwitAdapter.TwitHolder>() {
    private lateinit var auth : FirebaseAuth
    private val cursor: Cursor = c!!
    private var parentClass = discoveryFragment
    private var context = ctc

    class TwitHolder(v: View) : RecyclerView.ViewHolder(v) {
        val nUsername: TextView = v.findViewById(R.id.tv_twitUsername)
        val nMood: TextView = v.findViewById(R.id.tv_twitMood)
        val nTwit: TextView = v.findViewById(R.id.tv_twitListText)
        val nDate: TextView = v.findViewById(R.id.tv_twitDate)
        val nEdit: ImageButton = v.findViewById(R.id.btn_edit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TwitHolder {
        return TwitHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_list_twit, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TwitHolder, position: Int) {
        auth = FirebaseAuth.getInstance()
        parentClass = DiscoveryFragment()
        cursor.moveToPosition(position)
        val docID: String =
            cursor.getString(cursor.getColumnIndex("documentID"))
        val uname: String =
            cursor.getString(cursor.getColumnIndex("username"))
        val twit: String =
            cursor.getString(cursor.getColumnIndex("twit"))
        val mood: String =
            cursor.getString(cursor.getColumnIndex("mood"))
        val date: String =
            cursor.getString(cursor.getColumnIndex("date"))
        holder.nUsername.text = uname
        holder.nMood.text = mood
        holder.nTwit.text = twit
        holder.nDate.text = date
        if (auth.currentUser?.displayName.toString().equals(uname)){
            holder.nEdit.setOnClickListener {
                parentClass!!.popUpEdit(docID,uname,twit, context)
                Toast.makeText(holder.nEdit.context, "duas", Toast.LENGTH_SHORT).show()

            }
        }else{
            holder.nEdit.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return cursor.count
    }
}
package com.quick.asthkt.viewmodels

import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.quick.asthkt.R

class TwitAdapter(
    c: Cursor?
) : RecyclerView.Adapter<TwitAdapter.TwitHolder>() {

    private val cursor: Cursor

    class TwitHolder(v: View) : RecyclerView.ViewHolder(v) {
        val nUsername: TextView = v.findViewById(R.id.tv_twitUsername)
        val nMood: TextView = v.findViewById(R.id.tv_twitMood)
        val nTwit: TextView = v.findViewById(R.id.tv_twitListText)
        val nDate: TextView = v.findViewById(R.id.tv_twitDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TwitHolder {
        return TwitHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_list_twit, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TwitHolder, position: Int) {
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
    }

    override fun getItemCount(): Int {
        return cursor.count
    }


    init {
        cursor = c!!
    }
}
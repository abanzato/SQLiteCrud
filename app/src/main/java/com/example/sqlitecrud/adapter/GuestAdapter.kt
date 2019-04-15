package com.example.sqlitecrud.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sqlitecrud.view.FormGuestActivity
import com.example.sqlitecrud.R
import com.example.sqlitecrud.model.Guest
import kotlinx.android.synthetic.main.item_guest.view.*

class GuestAdapter(val context: Context, val guest: ArrayList<Guest>) : RecyclerView.Adapter<GuestAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_guest, parent, false)
        return ViewHolder(context, view)
    }

    override fun getItemCount(): Int {
        return guest.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindGuest(guest[position])
    }

    class ViewHolder(context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mContext = context

        fun bindGuest(guest: Guest){
            itemView.tvGuestName.text = guest.name
            itemView.tvGuestAddress.text = guest.address

            itemView.rowGuest.setOnClickListener {
                val mActivity: Activity = mContext as Activity

                mActivity.startActivityForResult(Intent(mContext, FormGuestActivity::class.java).apply {

                    putExtra("necessity" , FormGuestActivity.UPDATE_GUEST)
                    putExtra("id", guest.id)
                    putExtra("name", guest.name)
                    putExtra("address", guest.address)

                }, 2)
            }
        }
    }
}


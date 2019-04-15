package com.example.sqlitecrud.view

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.example.sqlitecrud.R
import com.example.sqlitecrud.dbhelper.DBHelper
import com.example.sqlitecrud.model.Guest
import kotlinx.android.synthetic.main.activity_form_guest.*

class FormGuestActivity : AppCompatActivity() {

    private lateinit var necessity: String
    private lateinit var db: DBHelper

    companion object {
        val CREATE_GUEST = "CREATE GUEST"
        val UPDATE_GUEST = "UPDATE GUEST"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_guest)

        db = DBHelper(this)

        necessity = intent.getStringExtra("necessity")
        val name: String? = intent.getStringExtra("name")
        val address = intent.getStringExtra("address")

        if(necessity == UPDATE_GUEST) {
            etName.setText(name)
            etAddress.setText(address)
            btnDelete.visibility = View.VISIBLE
        }

        btnDelete.setOnClickListener { deleteGuest() }
        btnSubmit.setOnClickListener { submitGuest() }

    }

    fun deleteGuest() {
        // Delete Guest
        val guest = Guest(
            intent.getIntExtra("id",0),
            etName.text.toString(),
            etAddress.text.toString()
        )
        db.deleteGuest(guest)

        setResult(Activity.RESULT_OK, Intent())
        finish()
    }

    fun submitGuest() {
        if(necessity == CREATE_GUEST){
            // Add Guest
            val guest = Guest(
                0,
                etName.text.toString(),
                etAddress.text.toString()
            )
            db.addGuest(guest)
        }else {
            // Update Guest
            val guest = Guest(
                intent.getIntExtra("id",0),
                etName.text.toString(),
                etAddress.text.toString()
            )
            db.updateGuest(guest)
        }

        setResult(Activity.RESULT_OK, Intent())
        finish()
    }
}

package com.example.sqlitecrud.view

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.example.sqlitecrud.R
import com.example.sqlitecrud.adapter.GuestAdapter
import com.example.sqlitecrud.dbhelper.DBHelper
import com.example.sqlitecrud.model.Guest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var db: DBHelper
    private lateinit var listGuest: ArrayList<Guest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Setting Toolbar
        setSupportActionBar(findViewById(R.id.tb_toolbar))

        db = DBHelper(this)
        refreshData()
    }

    private fun refreshData() {
        listGuest = db.allGuests
        rvGuest.layoutManager = LinearLayoutManager(this)
        rvGuest.adapter = GuestAdapter(this, listGuest)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_add -> {
            startActivityForResult(Intent(this, FormGuestActivity::class.java).apply {
                putExtra("necessity" , FormGuestActivity.CREATE_GUEST)
            },2)
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 2 && resultCode == Activity.RESULT_OK) {
            refreshData()
        }
    }
}

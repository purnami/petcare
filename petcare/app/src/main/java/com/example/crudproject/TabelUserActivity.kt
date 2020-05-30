package com.example.crudproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.marginLeft
import kotlinx.android.synthetic.main.activity_tabel_product.*
import kotlinx.android.synthetic.main.activity_tabel_user.*
import kotlinx.android.synthetic.main.activity_tabel_user.linearLayout

class TabelUserActivity : AppCompatActivity() {

    private var userList : ListView?=null
    private var databaseHandler: DataBaseHandler? = null
    private var userModelArrayList: ArrayList<UserModel>?=null
    private var customAdapter: CustomAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabel_user)

        userList=findViewById(R.id.user_list) as ListView

        databaseHandler = DataBaseHandler(this)

        userModelArrayList = databaseHandler!!.allUsers

        customAdapter = CustomAdapter(this, userModelArrayList!!)
        userList!!.adapter=customAdapter

    }
    override fun onBackPressed() {
        val intent = Intent(applicationContext, AdminActivity::class.java)
        startActivity(intent)
    }
}

package com.example.crudproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class MailActivity : AppCompatActivity() {
    private var mailList : ListView?=null
    private var databaseHandler: DataBaseHandler? = null
    private var mailArrayList: ArrayList<MailModel>?=null
    private var customAdapter: MailAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mail)

        mailList= findViewById(R.id.mail_list) as ListView
        var userId = intent.getStringExtra("userId")

        databaseHandler = DataBaseHandler(this)

        mailArrayList = databaseHandler!!.getUserMail(userId.toInt())

        customAdapter = MailAdapter(this, mailArrayList!!)
        mailList!!.adapter=customAdapter
    }

    override fun onBackPressed() {
        var userId = intent.getStringExtra("userId")
        val intent = Intent(applicationContext, UserActivity::class.java)
        intent.putExtra("userId", userId)
        startActivity(intent)
    }
}

package com.example.crudproject

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.back_toolbar.*


class ProfileActivity : AppCompatActivity() {

    private var profilename : TextView?=null
    private var btneditprofile: ImageButton?=null
    private var profileemail: TextView?=null
    private var tostatuspay: LinearLayout?=null
    private var toallpay: LinearLayout?=null
    private var logout: LinearLayout?=null

    private var databaseHandler: DataBaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        var userId = intent.getStringExtra("userId")
        databaseHandler = DataBaseHandler(this)

        profilename=findViewById(R.id.profilename) as TextView
        btneditprofile=findViewById(R.id.btneditprofile) as ImageButton
        profileemail=findViewById(R.id.profileemail) as TextView
        tostatuspay=findViewById(R.id.tostatuspay) as LinearLayout
        toallpay=findViewById(R.id.toallpay) as LinearLayout
        logout=findViewById(R.id.logout) as LinearLayout

        var user=databaseHandler!!.getprofileuser(userId.toInt())
        profilename!!.text=""+user.get(0).name
        profileemail!!.text=""+user.get(0).email

        tostatuspay!!.setOnClickListener {
            val intent = Intent(applicationContext, StatusPaymentActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        toallpay!!.setOnClickListener {
            val intent = Intent(applicationContext, AllPaymentActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }
        logout!!.setOnClickListener {
            val intent = Intent(applicationContext, LogInActivity::class.java)
            startActivity(intent)
        }
        btneditprofile!!.setOnClickListener {
            val intent = Intent(applicationContext, EditProfileActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

    }
    override fun onBackPressed() {
        var userId = intent.getStringExtra("userId")
        val intent = Intent(applicationContext, UserActivity::class.java)
        intent.putExtra("userId", userId)
        startActivity(intent)
    }
}

package com.example.crudproject

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_user.*
import java.util.*
import kotlin.collections.ArrayList

class UserActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView?=null
    private var gridLayoutManager: GridLayoutManager?=null
    private var databaseHandler: DataBaseHandler? = null
    private var productModelArrayList: ArrayList<ProductModel>?=null
    private var customAdapter: UserProductAdapter? = null

    private var backPressedTime : Long =0
    private var backToast: Toast? = null
    private var textsearch: String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        var userId = intent.getStringExtra("userId")

        recyclerView= findViewById(R.id.my_recycler_view) as RecyclerView
        gridLayoutManager= GridLayoutManager(applicationContext, 2, LinearLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager=gridLayoutManager
        recyclerView?.setHasFixedSize(true)

        databaseHandler = DataBaseHandler(this)
        productModelArrayList = databaseHandler!!.allProducts
        customAdapter = UserProductAdapter(this, productModelArrayList!!, userId)
        recyclerView!!.adapter=customAdapter

        btmNavbar.setOnNavigationItemSelectedListener{
            item ->
            when(item.itemId){
                R.id.homes -> {
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.cart -> {
                    val intent = Intent(applicationContext, BasketActivity::class.java)
                    intent.putExtra("userId", userId)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.notif -> {
                    val intent = Intent(applicationContext, StatusPaymentActivity::class.java)
                    intent.putExtra("userId", userId)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.mail -> {
                    val intent = Intent(applicationContext, MailActivity::class.java)
                    intent.putExtra("userId", userId)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.profile -> {
                    val intent = Intent(applicationContext, ProfileActivity::class.java)
                    intent.putExtra("userId", userId)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val searchItem = menu?.findItem(R.id.menu_search)

        val searchView = searchItem?.actionView as androidx.appcompat.widget.SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                searchView.setQuery("", false)
                searchItem.collapseActionView()

                textsearch=query

                var userId = intent.getStringExtra("userId")

                recyclerView= findViewById(R.id.my_recycler_view) as RecyclerView
                gridLayoutManager= GridLayoutManager(applicationContext, 2, LinearLayoutManager.VERTICAL, false)
                recyclerView?.layoutManager=gridLayoutManager
                recyclerView?.setHasFixedSize(true)

                databaseHandler = DataBaseHandler(this@UserActivity)
                productModelArrayList = databaseHandler!!.allProducts(textsearch.toString())
                customAdapter = UserProductAdapter(this@UserActivity, productModelArrayList!!, userId)
                recyclerView!!.adapter=customAdapter

                if(productModelArrayList!!.isEmpty()){
                Toast.makeText(this@UserActivity, "Tidak ada product", Toast.LENGTH_SHORT).show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var userId = intent.getStringExtra("userId")
                recyclerView= findViewById(R.id.my_recycler_view) as RecyclerView
                gridLayoutManager= GridLayoutManager(applicationContext, 2, LinearLayoutManager.VERTICAL, false)
                recyclerView?.layoutManager=gridLayoutManager
                recyclerView?.setHasFixedSize(true)

                databaseHandler = DataBaseHandler(this@UserActivity)
                productModelArrayList = databaseHandler!!.allProducts
                customAdapter = UserProductAdapter(this@UserActivity, productModelArrayList!!, userId)
                recyclerView!!.adapter=customAdapter
                return false
            }

        })
        return true
    }

    override fun onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            backToast?.cancel()
            moveTaskToBack(true)
        }else{
            backToast=Toast.makeText(baseContext,"Press again to exit", Toast.LENGTH_SHORT)
            backToast?.show()
        }
        backPressedTime = System.currentTimeMillis()

    }
}

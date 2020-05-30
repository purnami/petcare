package com.example.crudproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast

class BasketActivity : AppCompatActivity() {

    private var basketList : ListView?=null
    private var databaseHandler: DataBaseHandler? = null
    private var basketModelArrayList: ArrayList<BasketModel>?=null
    private var customAdapter: BasketUserAdapter? = null
    private var totalPrices: TextView?=null
    private var btnBuy: Button?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)

        basketList= findViewById(R.id.basket_list) as ListView
        totalPrices=findViewById(R.id.totalprices) as TextView
        btnBuy=findViewById(R.id.btn_buy2) as Button

        databaseHandler = DataBaseHandler(this)

        var id = intent.getStringExtra("userId")
        var totalProduct=databaseHandler!!.getTotalProduct(id.toInt())

        basketModelArrayList = databaseHandler!!.getBaskets(id.toInt())
        customAdapter = BasketUserAdapter(this, basketModelArrayList!!, id)
        basketList!!.adapter=customAdapter

        totalPrices!!.setText("Rp. "+databaseHandler!!.getTotalPrices(id.toInt()))
        btnBuy!!.setText("Beli ("+databaseHandler!!.getTotalProduct(id.toInt())+")")
        btnBuy!!.setOnClickListener {
            if(totalProduct!!>0){
                val intent = Intent(applicationContext, BuyActivity::class.java)
                intent.putExtra("userId", id)
                intent.putExtra("totalPrices", databaseHandler!!.getTotalPrices(id.toInt()).toString())
                intent.putExtra("totalProduct", databaseHandler!!.getTotalProduct(id.toInt()).toString())
                startActivity(intent)
            }else{
                Toast.makeText(this, "There Are No Product Selected", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onBackPressed() {
        var id = intent.getStringExtra("userId")
        val intent = Intent(applicationContext, UserActivity::class.java)
        intent.putExtra("userId",id)
        startActivity(intent)
    }
}


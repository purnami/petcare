package com.example.crudproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class BasketListActivity : AppCompatActivity() {

    private var basketList : ListView?=null
    private var databaseHandler: DataBaseHandler? = null
    private var basketModelArrayList: ArrayList<BasketModel>?=null
    private var customAdapter: BasketListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket_list)

        basketList=findViewById(R.id.basketadmin_list) as ListView
        databaseHandler = DataBaseHandler(this)
        basketModelArrayList = databaseHandler!!.allBaskets
        customAdapter = BasketListAdapter(this, basketModelArrayList!!)
        basketList!!.adapter=customAdapter
    }
}

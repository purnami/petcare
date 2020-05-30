package com.example.crudproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class OrderListActivity : AppCompatActivity() {

    private var orderList : ListView?=null
    private var databaseHandler: DataBaseHandler? = null
    private var orderModelArrayList: ArrayList<OrderModel>?=null
    private var customAdapter: OrderAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)

        orderList= findViewById(R.id.order_list) as ListView

        databaseHandler = DataBaseHandler(this)

        orderModelArrayList = databaseHandler!!.allOrders
        customAdapter = OrderAdapter(this, orderModelArrayList!!)
        orderList!!.adapter=customAdapter
    }
}

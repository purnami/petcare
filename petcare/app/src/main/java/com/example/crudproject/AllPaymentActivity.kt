package com.example.crudproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class AllPaymentActivity : AppCompatActivity() {

    private var allPaymentList : ListView?=null
    private var databaseHandler: DataBaseHandler? = null
    private var allPaymentModelArrayList: ArrayList<OrderModel>?=null
    private var customAdapter: AllPaymentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_payment)

        var userId = intent.getStringExtra("userId")

        allPaymentList= findViewById(R.id.allpayment_list) as ListView
        databaseHandler = DataBaseHandler(this)
        allPaymentModelArrayList=databaseHandler!!.allOrders2(userId.toInt())
        customAdapter = AllPaymentAdapter(this, allPaymentModelArrayList!!, userId)
        allPaymentList!!.adapter=customAdapter
    }
}

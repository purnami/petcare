package com.example.crudproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class StatusPaymentActivity : AppCompatActivity() {

    private var statusPaymentList : ListView?=null
    private var databaseHandler: DataBaseHandler? = null
    private var statusPaymentModelArrayList: ArrayList<OrderModel>?=null
    private var customAdapter: StatusPaymentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status_payment)

        var userId = intent.getStringExtra("userId")
        var orderId=intent.getStringExtra("orderId")
        var totalPrices=intent.getStringExtra("totalPrices")

        statusPaymentList= findViewById(R.id.statuspayment_list) as ListView
        databaseHandler = DataBaseHandler(this)

        statusPaymentModelArrayList=databaseHandler!!.allOrders(userId.toInt())

        customAdapter = StatusPaymentAdapter(this, statusPaymentModelArrayList!!, userId)
        statusPaymentList!!.adapter=customAdapter
    }
    override fun onBackPressed() {
        var userId = intent.getStringExtra("userId")
        val intent = Intent(applicationContext, UserActivity::class.java)
        intent.putExtra("userId", userId)
        startActivity(intent)
    }
}

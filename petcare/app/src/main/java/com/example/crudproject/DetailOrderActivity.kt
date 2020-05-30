package com.example.crudproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class DetailOrderActivity : AppCompatActivity() {
    private var detailOrderList : ListView?=null
    private var databaseHandler: DataBaseHandler? = null
    private var detailOrderModelArrayList: ArrayList<DetailOrderModel>?=null
    private var customAdapter: DetailOrderAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_order)

        detailOrderList= findViewById(R.id.detailorder_list) as ListView

        databaseHandler = DataBaseHandler(this)

        detailOrderModelArrayList=databaseHandler!!.allDetailOrder

        customAdapter = DetailOrderAdapter(this, detailOrderModelArrayList!!)
        detailOrderList!!.adapter=customAdapter
    }
}

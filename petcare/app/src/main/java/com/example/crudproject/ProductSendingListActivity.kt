package com.example.crudproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class ProductSendingListActivity : AppCompatActivity() {
    private var productSendingList : ListView?=null
    private var databaseHandler: DataBaseHandler? = null
    private var productSendingModelArrayList: ArrayList<ProductSendingModel>?=null
    private var customAdapter: ProductSendingAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_sending_list)

        productSendingList= findViewById(R.id.productsending_list) as ListView

        databaseHandler = DataBaseHandler(this)

        productSendingModelArrayList = databaseHandler!!.allProductSending

        customAdapter = ProductSendingAdapter(this, productSendingModelArrayList!!)
        productSendingList!!.adapter=customAdapter
    }
    override fun onBackPressed() {
        val intent = Intent(applicationContext, AdminActivity::class.java)
        startActivity(intent)
    }
}

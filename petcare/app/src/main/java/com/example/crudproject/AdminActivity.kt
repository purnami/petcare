package com.example.crudproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_admin.*

class AdminActivity : AppCompatActivity() {
    private var backPressedTime : Long =0
    private var backToast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        btn_user.setOnClickListener({
            val intent = Intent(applicationContext, TabelUserActivity::class.java)
            startActivity(intent)
        })
        btn_product.setOnClickListener({
            val intent = Intent(applicationContext, ProductActivity::class.java)
            startActivity(intent)
        })
        btn_basket.setOnClickListener {
            val intent = Intent(applicationContext, BasketListActivity::class.java)
            startActivity(intent)
        }
        btn_order.setOnClickListener({
            val intent = Intent(applicationContext, OrderListActivity::class.java)
            startActivity(intent)
        })
        btn_detailorder.setOnClickListener({
            val intent = Intent(applicationContext, DetailOrderActivity::class.java)
            startActivity(intent)
        })
        btn_productsending.setOnClickListener({
            val intent = Intent(applicationContext, ProductSendingListActivity::class.java)
            startActivity(intent)
        })
        btn_logout.setOnClickListener({
            val intent = Intent(applicationContext, LogInActivity::class.java)
            startActivity(intent)
        })
    }

    override fun onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            backToast?.cancel()
            moveTaskToBack(true)
        }else{
            backToast= Toast.makeText(baseContext,"Press again to exit", Toast.LENGTH_SHORT)
            backToast?.show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}

package com.example.crudproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class DetailProductActivity : AppCompatActivity() {
    private var image: ImageView?=null
    private var name: TextView?=null
    private var prices: TextView?=null
    private var btnBuy: Button?=null
    private var btnBasket: Button?=null
    private var databaseHandler: DataBaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)

        image=findViewById(R.id.img_detail) as ImageView
        name=findViewById(R.id.name_detail) as TextView
        prices=findViewById(R.id.price_detail) as TextView
        btnBuy=findViewById(R.id.btn_buy) as Button
        btnBasket=findViewById(R.id.btn_addBasket) as Button

        databaseHandler=DataBaseHandler(this)

        var userId = intent.getStringExtra("userId")
        var productId = intent.getStringExtra("productId")
        var productImage = databaseHandler!!.getImageProduct(productId.toInt())

        name!!.text=""+databaseHandler!!.getNameProduct(productId.toInt())
        prices!!.text="Rp. "+databaseHandler!!.getPricesProduct(productId.toInt()).toString()
        image!!.setImageBitmap(Utils.getImage(productImage!!))

        btnBuy!!.setOnClickListener {
            databaseHandler!!.addBasket(userId.toInt(), productId.toInt(), 1, 1)
            val intent = Intent(applicationContext, BasketActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        btnBasket!!.setOnClickListener {
            databaseHandler!!.addBasket(userId.toInt(), productId.toInt(), 1, 1)
            Toast.makeText(this, "Produk berhasil ditambahkan ke keranjang!", Toast.LENGTH_LONG).show()
        }


    }
    override fun onBackPressed() {
        var userId = intent.getStringExtra("userId")
        val intent = Intent(applicationContext, UserActivity::class.java)
        intent.putExtra("userId",userId)
        startActivity(intent)
    }
}

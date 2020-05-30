package com.example.crudproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_tabel_product.*

class ProductActivity : AppCompatActivity() {
    private var productList : ListView?=null
    private var databaseHandler: DataBaseHandler? = null
    private var productModelArrayList: ArrayList<ProductModel>?=null
    private var customAdapter: CustomAdapterProduct? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabel_product)

        productList= findViewById(R.id.product_list) as ListView

        databaseHandler = DataBaseHandler(this)

        productModelArrayList = databaseHandler!!.allProducts

        customAdapter = CustomAdapterProduct(this, productModelArrayList!!)
        productList!!.adapter=customAdapter

        productList!!.onItemClickListener= AdapterView.OnItemClickListener{ parent, view, position, id ->
            val intent = Intent(applicationContext, ProductUpdateDeleteActivity::class.java)
            intent.putExtra("productId", productModelArrayList!![position].getIds().toString())
            intent.putExtra("productName", productModelArrayList!![position].getNames())
            intent.putExtra("productPrices", productModelArrayList!![position].getPrices().toString())
            intent.putExtra("productStock", productModelArrayList!![position].getStocks().toString())
            startActivity(intent)
        }

        btn_add_product.setOnClickListener({
            val intent = Intent(applicationContext, AddProductActivity::class.java)
            startActivity(intent)
        })
    }
    override fun onBackPressed() {
        val intent = Intent(applicationContext, AdminActivity::class.java)
        startActivity(intent)
    }
}

package com.example.crudproject

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.android.synthetic.main.activity_add_product.*

class AddProductActivity : AppCompatActivity() {
    private var btnAdd : Button? = null
    private var productname : EditText? = null
    private var productprices : EditText? = null
    private var productstock : EditText? = null
    private var productimage : ImageView?=null
    private var btnaddimage: Button? = null
    private var databaseHandler: DataBaseHandler? = null

    val SELECT_PHOTO=2222

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        val context = this
        databaseHandler= DataBaseHandler(this)

        btnAdd=findViewById(R.id.btn_addProduct) as Button
        productname= findViewById(R.id.product_name) as EditText
        productprices=findViewById(R.id.product_prices) as EditText
        productstock=findViewById(R.id.product_stock) as EditText
        productimage=findViewById(R.id.product_image) as ImageView
        btnaddimage=findViewById(R.id.btn_addImage) as Button

        btnaddimage!!.setOnClickListener {
            val photoPicker=Intent(Intent.ACTION_PICK)
            photoPicker.type="image/*"
            startActivityForResult(photoPicker,SELECT_PHOTO)
        }

        btnAdd!!.setOnClickListener{
            if(productname!!.text.toString().length>0 && productprices!!.text.toString().length>0 && productstock!!.text.toString().length>0){
                val bitmap=(productimage!!.drawable as BitmapDrawable).bitmap
                databaseHandler!!.addProduct(productname!!.text.toString(), productprices!!.text.toString().toInt(), productstock!!.text.toString().toInt(), Utils.getBytes(bitmap))
                Toast.makeText(context, "The Product Has Been Added", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, ProductActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(context, "Please fill all data's", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == SELECT_PHOTO && resultCode == Activity.RESULT_OK && data != null){
            val pickedImage=data.data
            productimage!!.setImageURI(pickedImage)
        }
    }
}

package com.example.crudproject

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_product_update_delete.*

class ProductUpdateDeleteActivity : AppCompatActivity() {
    private var pName: EditText?=null
    private var pPrices: EditText?=null
    private var pStock: EditText?=null
    private var pImage: ImageView?=null
    private var pbtnUpdate: Button?=null
    private var pbtnDelete: Button?=null
    private var databaseHandler: DataBaseHandler? = null

    val SELECT_PHOTO=2222
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_update_delete)

        pName=findViewById(R.id.pname) as EditText
        pPrices=findViewById(R.id.pprices) as EditText
        pStock=findViewById(R.id.pstock) as EditText
        pbtnUpdate=findViewById(R.id.pbtnupdate) as Button
        pbtnDelete=findViewById(R.id.pbtndelete) as Button
        pImage=findViewById(R.id.pimage) as ImageView

        databaseHandler = DataBaseHandler(this)

        var id = intent.getStringExtra("productId")
        pName!!.setText(intent.getStringExtra("productName"))
        pPrices!!.setText(intent.getStringExtra("productPrices"))
        pStock!!.setText(intent.getStringExtra("productStock"))

        var productImage = databaseHandler!!.getImageProduct(id.toInt())
        pImage!!.setImageBitmap(Utils.getImage(productImage!!))

        pbtnimage.setOnClickListener {
            val photoPicker=Intent(Intent.ACTION_PICK)
            photoPicker.type="image/*"
            startActivityForResult(photoPicker,SELECT_PHOTO)
        }

        pbtnUpdate!!.setOnClickListener {
            Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show()
            val bitmap=(pImage!!.drawable as BitmapDrawable).bitmap
            databaseHandler!!.updateProduct(
                id.toInt(),
                pName!!.text.toString(),
                pPrices!!.text.toString().toInt(),
                pStock!!.text.toString().toInt(),
                Utils.getBytes(bitmap)
            )
            Toast.makeText(this, "Updated Successfully!", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext, ProductActivity::class.java)
            startActivity(intent)
        }
        pbtnDelete!!.setOnClickListener {
            Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show()
            databaseHandler!!.deleteProduct(id.toInt())
            Toast.makeText(this, "Deleted Successfully!", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext, ProductActivity::class.java)
            startActivity(intent)

        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == SELECT_PHOTO && resultCode == Activity.RESULT_OK && data != null){
            val pickedImage=data.data
            pImage!!.setImageURI(pickedImage)
        }
    }
    override fun onBackPressed() {
        val intent = Intent(applicationContext, ProductActivity::class.java)
        startActivity(intent)
    }
}

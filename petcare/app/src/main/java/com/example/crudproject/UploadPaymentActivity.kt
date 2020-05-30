package com.example.crudproject

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class UploadPaymentActivity : AppCompatActivity() {

    private var imgpay: ImageView?=null
    private var addPay: Button?=null
    private var databaseHandler: DataBaseHandler? = null
    val SELECT_PHOTO=2222

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_payment)

        imgpay=findViewById(R.id.imgPay) as ImageView
        addPay=findViewById(R.id.btn_addPay) as Button
        databaseHandler = DataBaseHandler(this)

        var userId = intent.getStringExtra("userId")
        var orderId = intent.getStringExtra("orderId")

        addPay!!.setOnClickListener {
            val photoPicker= Intent(Intent.ACTION_PICK)
            photoPicker.type="image/*"
            startActivityForResult(photoPicker,SELECT_PHOTO)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var userId = intent.getStringExtra("userId")
        var orderId = intent.getStringExtra("orderId")
        databaseHandler = DataBaseHandler(this)
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == SELECT_PHOTO && resultCode == Activity.RESULT_OK && data != null){
            val pickedImage=data.data
            imgpay!!.setImageURI(pickedImage)
            addPay!!.text="Simpan Gambar"
            addPay!!.setOnClickListener {
                val bitmap=(imgpay!!.drawable as BitmapDrawable).bitmap

                var date= LocalDateTime.now()
                val format = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm")
                val formated=date.format(format)

                databaseHandler!!.addProductSending(orderId.toInt(), Utils.getBytes(bitmap),"0",0)
                Toast.makeText(this, "Pembayaran Anda Akan Diverifikasi Terlebih Dahulu", Toast.LENGTH_LONG).show()

                val intent = Intent(applicationContext, StatusPaymentActivity::class.java)
                intent.putExtra("userId", userId)
                startActivity(intent)
            }
        }
    }
    override fun onBackPressed() {
        var userId = intent.getStringExtra("userId")
        val intent = Intent(applicationContext, StatusPaymentActivity::class.java)
        intent.putExtra("userId", userId)
        startActivity(intent)
    }
}

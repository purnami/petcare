package com.example.crudproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class ViewImagePayActivity : AppCompatActivity() {

    private var imagepay: ImageView?=null
    private var databaseHandler: DataBaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_image_pay)

        imagepay=findViewById(R.id.imagepay) as ImageView
        databaseHandler=DataBaseHandler(this)

        var productSendId = intent.getStringExtra("productSendId")

        var payimage = databaseHandler!!.getImagePay(productSendId.toInt())

        imagepay!!.setImageBitmap(Utils.getImage(payimage!!))
    }
}

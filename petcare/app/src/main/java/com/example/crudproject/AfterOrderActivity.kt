package com.example.crudproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.lv_orderlist.*

class AfterOrderActivity : AppCompatActivity() {
    private var limitdate: TextView?=null
    private var totprices: TextView?=null
    private var detail: TextView?=null
    private var totview: TextView?=null
    private var totview2: TextView?=null
    private var sendview: TextView?=null
    private var sendview2: TextView?=null
    private var totpricess: TextView?=null
    private var totpricess2: TextView?=null
    private var statuspayment: Button?=null

    private var databaseHandler: DataBaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_order)

        databaseHandler = DataBaseHandler(this)

        var userId = intent.getStringExtra("userId")
        var orderId=intent.getStringExtra("orderId")
        var totalPrices=intent.getStringExtra("totalPrices")

        limitdate=findViewById(R.id.limitdateview) as TextView
        totprices=findViewById(R.id.totalpricesview) as TextView
        detail=findViewById(R.id.detailprices) as TextView
        totview=findViewById(R.id.totview) as TextView
        totview2=findViewById(R.id.totview2) as TextView
        sendview=findViewById(R.id.sendview) as TextView
        sendview2=findViewById(R.id.sendview2) as TextView
        totpricess=findViewById(R.id.totpricess) as TextView
        totpricess2=findViewById(R.id.totpricess2) as TextView
        statuspayment=findViewById(R.id.statusPayment) as Button

        var data=databaseHandler!!.getUserOrder(orderId.toInt())

        limitdate!!.text="Sebelum "+data.get(0).limitdate
        totprices!!.text="Rp. "+data.get(0).totalPrices

        detail!!.setOnClickListener {
            totview!!.text="Total Harga ("+data.get(0).totalProduct+" Barang)"
            totview2!!.text="Rp. "+totalPrices
            sendview!!.text="Total Ongkos Kirim"
            sendview2!!.text="Rp. "+(data.get(0).send+data.get(0).duration)
            totpricess!!.text="Total Tagihan"
            totpricess2!!.text="Rp. "+data.get(0).totalPrices
        }
        statuspayment!!.setOnClickListener {
            val intent = Intent(applicationContext, StatusPaymentActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("orderId", orderId)
            intent.putExtra("totalPrices", totalPrices)
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
        var userId = intent.getStringExtra("userId")
        val intent = Intent(applicationContext, UserActivity::class.java)
        intent.putExtra("userId", userId)
        startActivity(intent)
    }
}

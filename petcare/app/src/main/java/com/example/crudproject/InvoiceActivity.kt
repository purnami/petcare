package com.example.crudproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView

class InvoiceActivity : AppCompatActivity() {
    private var invoiceList : ListView?=null
    private var databaseHandler: DataBaseHandler? = null
    private var send: TextView?=null
    private var pricessend: TextView?=null
    private var duration: TextView?=null
    private var durationprices: TextView?=null
    private var totprices: TextView?=null

    private var invoiceProductArrayList: ArrayList<DetailOrderModel>?=null
    private var customAdapter: InvoiceProductAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice)

        var userId = intent.getStringExtra("userId")
        var orderId = intent.getStringExtra("orderId")
        databaseHandler = DataBaseHandler(this)

        invoiceList=findViewById(R.id.invoice_list) as ListView
        send=findViewById(R.id.send2) as TextView
        pricessend=findViewById(R.id.pricessend) as TextView
        duration=findViewById(R.id.duration2) as TextView
        durationprices=findViewById(R.id.durationprices) as TextView
        totprices=findViewById(R.id.pricestot) as TextView

        var data=databaseHandler!!.getUserOrder(orderId.toInt())

        invoiceProductArrayList= databaseHandler!!.getDetailOrder(orderId.toInt())

        customAdapter = InvoiceProductAdapter(this, invoiceProductArrayList!!)
        invoiceList!!.adapter=customAdapter

        if(data.get(0).send==13000){
            send!!.text="Jasa Pengiriman (JNE)"
        }else if(data.get(0).send==11000){
            send!!.text="Jasa Pengiriman (JNT)"
        }else if(data.get(0).send==8000){
            send!!.text="Jasa Pengiriman (Post Indonesia)"
        }
        pricessend!!.text="Rp. "+data.get(0).send

        if(data.get(0).duration==9000){
            duration!!.text="Reguler (2-4 hari)"
        }else if(data.get(0).duration==6000){
            duration!!.text="Reguler (5-9 hari)"
        }else if(data.get(0).duration==11000){
            duration!!.text="Ekonomi (2-3 hari)"
        }
        durationprices!!.text="Rp. "+data.get(0).duration
        totprices!!.text="Rp. "+data.get(0).totalPrices
    }

}

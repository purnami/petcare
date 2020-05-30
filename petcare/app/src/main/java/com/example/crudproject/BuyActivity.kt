package com.example.crudproject

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class BuyActivity : AppCompatActivity() {
    private var buyName: EditText? = null
    private var buyAddress: EditText? = null
    private var buyTelp: EditText? = null
    private var buySend: Spinner? = null
    private var buyDuration:Spinner?=null
    private var buyBank: Spinner? = null
    private var buyRek: EditText? = null
    private var buyNameRek: EditText? = null
    private var btnOrder: Button? = null
    private var date: Button? = null
    private var databaseHandler: DataBaseHandler? = null
    private var selectedsend: String?=null
    private var selectedduration: String?=null
    private var selectedbank: String?=null
    private var send : Int?=0
    private var duration: Int?=0
    private var bank : String?=null
    private var totprices: Int?=0
    private var orderid: Int?=0
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy)

        databaseHandler = DataBaseHandler(this)

        buyName = findViewById(R.id.buy_name) as EditText
        buyAddress = findViewById(R.id.buy_address) as EditText
        buyTelp = findViewById(R.id.buy_telp) as EditText
        buySend = findViewById(R.id.buy_send) as Spinner
        buyDuration = findViewById(R.id.buy_duration) as Spinner
        buyBank = findViewById(R.id.buy_bank) as Spinner
        buyRek = findViewById(R.id.buy_rek) as EditText
        buyNameRek = findViewById(R.id.buy_nameRek) as EditText
        btnOrder = findViewById(R.id.btn_order) as Button

        var userId = intent.getStringExtra("userId")
        var totalProduct=intent.getStringExtra("totalProduct")
        var totalPrices=intent.getStringExtra("totalPrices")

        val sends=resources.getStringArray(R.array.send)
        val durations=resources.getStringArray(R.array.duration)
        val banks=resources.getStringArray(R.array.bank)
        if(buySend != null && buyDuration != null && buyBank != null){
            val sendadapter=ArrayAdapter(this, android.R.layout.simple_spinner_item, sends)
            buySend!!.adapter=sendadapter
            val durationadapter=ArrayAdapter(this, android.R.layout.simple_spinner_item, durations)
            buyDuration!!.adapter=durationadapter
            val bankadapter=ArrayAdapter(this, android.R.layout.simple_spinner_item, banks)
            buyBank!!.adapter=bankadapter

            buySend!!.onItemSelectedListener=object :
                AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedsend=sends[position]
                }
            }
            buyDuration!!.onItemSelectedListener=object :
                AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedduration=durations[position]
                }
            }
            buyBank!!.onItemSelectedListener=object :
                AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedbank=banks[position]
                }
            }
        }

        btnOrder!!.setOnClickListener {
            if(selectedsend!!.equals("JNE Rp13.000")){ send = 13000
            }else if(selectedsend!!.equals("JNT Rp11.000")){ send = 11000
            }else if(selectedsend!!.equals("Post Indonesia Rp8.000")){ send = 8000 }

            if(selectedduration!!.equals("Reguler (2-4 hari) Rp9.000")){ duration=9000
            }else if(selectedduration!!.equals("Reguler (5-9 hari) Rp6.000")){ duration=6000
            }else if(selectedduration!!.equals("Ekonomi (2-3 hari) Rp11.000")){ duration=11000 }

            if(selectedbank!!.equals("Transfer Bank BNI")){ bank="bni"
            }else if(selectedbank!!.equals("Transfer Bank BCA")){ bank="bca"
            }else if(selectedbank!!.equals("Transfer Bank BRI")){ bank="bri"
            }else if(selectedbank!!.equals("Transfer Bank Mandiri")){ bank="mandiri" }

            var date2=LocalDateTime.now()
            var date3=LocalDateTime.now().plusDays(1)
            val format =DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm")
            val formated=date2.format(format)
            val formated2=date3.format(format)

            totprices=totalPrices.toInt()+send!!+duration!!
            Toast.makeText(this, "totprice:"+totprices+"  nama:"+buyName!!.text.toString()+"   send : "+send+"  duration: "+duration+"    bank:"+bank, Toast.LENGTH_SHORT).show()
            if(buyName!!.text.toString().length>0 && buyAddress!!.text.toString().length>0 && buyTelp!!.text.toString().length>0 &&
                    buyRek!!.text.toString().length>0 && buyNameRek!!.text.toString().length>0){
                databaseHandler!!.addOrder(userId.toInt(),totalProduct.toInt(), totprices!!,buyName!!.text.toString(),buyAddress!!.text.toString(),
                    buyTelp!!.text.toString(), send!!, duration!!, bank!!, buyRek!!.text.toString(), buyNameRek!!.text.toString(),formated.toString(),formated2.toString(),0)
                orderid=databaseHandler!!.getorderid(userId.toInt(),formated.toString(),formated2.toString())
                var data=databaseHandler!!.getinfoproduct(userId.toInt())
                for(i in 0..data.size-1){
                    databaseHandler!!.addDetailOrder(orderid!!.toInt(),data.get(i).productId, data.get(i).totalProduct)
                    databaseHandler!!.deleteBasket(data.get(i).id)
                }

                val intent = Intent(applicationContext, AfterOrderActivity::class.java)
                intent.putExtra("userId", userId)
                intent.putExtra("orderId", orderid.toString())
                intent.putExtra("totalPrices", totalPrices)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Please fill all data's", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
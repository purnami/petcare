package com.example.crudproject

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.ArrayList

class ProductSendingAdapter(private val context: Context, private val productSendingModelArrayList: ArrayList<ProductSendingModel>) :
    BaseAdapter() {

    internal lateinit var dataBaseHandlerr: DataBaseHandler
    override fun getCount(): Int {
        return productSendingModelArrayList.size
    }

    override fun getItem(position: Int): Any {
        return productSendingModelArrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder
        var duration: String?=null

        dataBaseHandlerr= DataBaseHandler(context)

        if (convertView == null) {
            holder = ViewHolder()
            val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.lv_productsending, null, true)

            holder.tvid = convertView!!.findViewById(R.id.id_send) as TextView
            holder.tvnamerek = convertView!!.findViewById(R.id.namereksend) as TextView
            holder.tvrek = convertView!!.findViewById(R.id.reksend) as TextView
            holder.tvtotprices= convertView!!.findViewById(R.id.totalpricessend) as TextView
            holder.tvstatusverif=convertView!!.findViewById(R.id.statusverif) as TextView
            holder.tvstatussend=convertView!!.findViewById(R.id.statussend) as TextView
            holder.checkpay=convertView!!.findViewById(R.id.check) as Button
            holder.btnyesverif=convertView!!.findViewById(R.id.yesverif) as Button
            holder.btnnoverif=convertView!!.findViewById(R.id.noverif) as Button
            holder.btnyessend=convertView!!.findViewById(R.id.yessend) as Button
            holder.btnnosend=convertView!!.findViewById(R.id.nosend) as Button

            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        var data=dataBaseHandlerr!!.getUserOrder(productSendingModelArrayList[position].getOrderIds())

        holder.tvid!!.text = "ID : " + productSendingModelArrayList[position].getIds()
        holder.tvnamerek!!.text = "Account : " + data.get(0).namerek
        holder.tvrek!!.text= "Account Number : " + data.get(0).rek
        holder.tvtotprices!!.text="Total Prices : " + data.get(0).totalPrices
        if(data.get(0).statuspay==1){
            holder.tvstatusverif!!.text="Verification Status : Already"
        }else if(data.get(0).statuspay==-1){
            holder.tvstatusverif!!.text="Sending Status : Rejected"
        }
        else{
            holder.tvstatusverif!!.text="Verification Status : Not Yet"
        }

        if(productSendingModelArrayList[position].status==1){
            holder.tvstatussend!!.text="Sending Status : Already"
        } else{
            holder.tvstatussend!!.text="Sending Status : Not Yet"
        }

        holder.checkpay!!.setOnClickListener {
            val intent = Intent(context, ViewImagePayActivity::class.java)
            intent.putExtra("productSendId", productSendingModelArrayList[position].getIds().toString())
            context.startActivity(intent)
        }

        holder.btnyesverif!!.setOnClickListener {
            dataBaseHandlerr!!.updateStatusPay(productSendingModelArrayList[position].getOrderIds(),1)

            var date= LocalDateTime.now()
            val format = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm")
            val formated=date.format(format)
            val message="Pembayaran anda telah diverifikasi, silahkan tunggu notifikasi pengiriman barang"
            dataBaseHandlerr!!.addMail(data.get(0).userId,message, formated.toString())
            val intent = Intent(context, ProductSendingListActivity::class.java)
            context.startActivity(intent)
        }

        holder.btnnoverif!!.setOnClickListener {
            dataBaseHandlerr!!.updateStatusPay(productSendingModelArrayList[position].getOrderIds(),-1)
            var date= LocalDateTime.now()
            val format = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm")
            val formated=date.format(format)

            dataBaseHandlerr!!.addMail(data.get(0).userId,"Pesanan anda tidak dapat di proses, silahkan mengirim ulang bukti pembayaran", formated.toString())

            val intent = Intent(context, ProductSendingListActivity::class.java)
            context.startActivity(intent)
        }

        holder.btnyessend!!.setOnClickListener {
            if(data.get(0).duration==9000){
                duration="2-4 hari"
            }else if(data.get(0).duration==6000){
                duration="5-9 hari"
            }else if(data.get(0).duration==11000){
                duration="2-3 hari"
            }
            var date= LocalDateTime.now()
            val format = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm")
            val formated=date.format(format)
            val message="Pesanan anda telah dikirim dan akan sampai dalam "+duration
            dataBaseHandlerr!!.updateSend( productSendingModelArrayList[position].getIds(), formated.toString(),1)
            dataBaseHandlerr!!.addMail(data.get(0).userId,message, formated.toString())

            val intent = Intent(context, ProductSendingListActivity::class.java)
            context.startActivity(intent)
        }
        holder.btnnosend!!.setOnClickListener {
            dataBaseHandlerr!!.updateSend( productSendingModelArrayList[position].getIds(), "0",0)
            val intent = Intent(context, ProductSendingListActivity::class.java)
            context.startActivity(intent)
        }

        return convertView
    }

    private inner class ViewHolder {
        var tvid: TextView? = null
        var tvnamerek: TextView? = null
        var tvrek: TextView? = null
        var tvtotprices: TextView? = null
        var tvstatusverif: TextView? = null
        var tvstatussend: TextView? = null
        var checkpay: Button?=null
        var btnyesverif: Button?=null
        var btnnoverif: Button?=null
        var btnyessend: Button?=null
        var btnnosend: Button?=null
    }
}
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

class AllPaymentAdapter (
    private val context: Context,
    private val allPaymentModelArrayList: ArrayList<OrderModel>,
    var userId: String?
) :
    BaseAdapter() {
    internal lateinit var dataBaseHandlerr: DataBaseHandler
    @RequiresApi(Build.VERSION_CODES.O)
    private var invoiceProductArrayList: ArrayList<DetailOrderModel>?=null
    private var customAdapter: InvoiceProductAdapter? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        val holder: AllPaymentAdapter.ViewHolder

        dataBaseHandlerr= DataBaseHandler(context)
        if (convertView == null) {
            holder = ViewHolder()
            val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.lv_allpayment, null, true)

            holder.tvdate=convertView!!.findViewById(R.id.date) as TextView
            holder.tvsend=convertView!!.findViewById(R.id.send3) as TextView
            holder.tvsendprices=convertView!!.findViewById(R.id.pricessend3) as TextView
            holder.tvduration=convertView!!.findViewById(R.id.duration3) as TextView
            holder.tvdurationprices=convertView!!.findViewById(R.id.durationprices3) as TextView
            holder.tvtotprices=convertView!!.findViewById(R.id.pricestot3) as TextView
            holder.listproduct=convertView!!.findViewById(R.id.allproduct_list ) as ListView
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        var data=dataBaseHandlerr!!.getUserOrder(allPaymentModelArrayList[position].getIds())
        invoiceProductArrayList= dataBaseHandlerr!!.getDetailOrder(allPaymentModelArrayList[position].getIds())
        customAdapter = InvoiceProductAdapter(context, invoiceProductArrayList!!)

        holder.listproduct!!.adapter=customAdapter
        holder.tvdate!!.text=""+data.get(0).date
        holder.listproduct!!

        if(data.get(0).send==13000){
            holder.tvsend!!.text="Jasa Pengiriman (JNE)"
        }else if(data.get(0).send==11000){
            holder.tvsend!!.text="Jasa Pengiriman (JNT)"
        }else if(data.get(0).send==8000){
            holder.tvsend!!.text="Jasa Pengiriman (Post Indonesia)"
        }
        holder.tvsendprices!!.text="Rp. "+data.get(0).send

        if(data.get(0).duration==9000){
            holder.tvduration!!.text="Reguler (2-4 hari)"
        }else if(data.get(0).duration==6000){
            holder.tvduration!!.text="Reguler (5-9 hari)"
        }else if(data.get(0).duration==11000){
            holder.tvduration!!.text="Ekonomi (2-3 hari)"
        }
        holder.tvdurationprices!!.text="Rp. "+data.get(0).duration
        holder.tvtotprices!!.text="Rp. "+data.get(0).totalPrices

        return convertView
    }

    override fun getCount(): Int {
        return allPaymentModelArrayList.size
    }
    override fun getItem(position: Int): Any {
        return allPaymentModelArrayList[position]
    }
    override fun getItemId(position: Int): Long {
        return 0
    }
    private inner class ViewHolder {
        var tvdate: TextView?=null
        var tvsend: TextView?=null
        var tvsendprices: TextView?=null
        var tvduration: TextView?=null
        var tvdurationprices: TextView?=null
        var tvtotprices: TextView?=null
        var listproduct: ListView?=null
    }
}
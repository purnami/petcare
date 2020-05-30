package com.example.crudproject

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.ArrayList

class StatusPaymentAdapter(
    private val context: Context,
    private val statusPaymentModelArrayList: ArrayList<OrderModel>,
    var userId: String?
) :
    BaseAdapter() {
    internal lateinit var dataBaseHandlerr: DataBaseHandler
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        val holder: StatusPaymentAdapter.ViewHolder

        dataBaseHandlerr= DataBaseHandler(context)
        if (convertView == null) {
            holder = ViewHolder()
            val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.lv_statuspayment, null, true)

            holder.tvlimitdate=convertView!!.findViewById(R.id.limitdateview2) as TextView
            holder.tvtotalprices=convertView!!.findViewById(R.id.totalprices2) as TextView
            holder.invoice=convertView!!.findViewById(R.id.invoice) as TextView
            holder.tvrek=convertView!!.findViewById(R.id.rek2) as TextView
            holder.tvnamerek=convertView!!.findViewById(R.id.namerek2) as TextView
            holder.btnupload=convertView!!.findViewById(R.id.btn_uploadpay) as Button

            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        holder.tvlimitdate!!.text="Bayar sebelum "+statusPaymentModelArrayList[position].getLimitdates()
        holder.tvtotalprices!!.text="Rp. "+statusPaymentModelArrayList[position].getTotalPricess()
        holder.tvrek!!.text=""+statusPaymentModelArrayList[position].getReks()
        holder.tvnamerek!!.text=""+statusPaymentModelArrayList[position].getNamereks()
        holder.btnupload!!.setOnClickListener {
            val intent = Intent(context, UploadPaymentActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("orderId", statusPaymentModelArrayList[position].getIds().toString())
            context.startActivity(intent)
        }
        holder.invoice!!.setOnClickListener {
            val intent = Intent(context, InvoiceActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("orderId", statusPaymentModelArrayList[position].getIds().toString())
            context.startActivity(intent)
        }

        var date= LocalDateTime.now()
        val format = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm")
        val formated=date.format(format)

        if(formated.toString().equals(statusPaymentModelArrayList[position].getLimitdates())){
            dataBaseHandlerr!!.deleteOrderProduct(statusPaymentModelArrayList[position].getIds())
        }
        return convertView
    }

    override fun getCount(): Int {
        return statusPaymentModelArrayList.size
    }

    override fun getItem(position: Int): Any {
        return statusPaymentModelArrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    private inner class ViewHolder {
        var tvlimitdate: TextView?=null
        var tvtotalprices: TextView?=null
        var invoice: TextView?=null
        var tvrek: TextView?=null
        var tvnamerek: TextView?=null
        var btnupload: Button?=null
    }
}
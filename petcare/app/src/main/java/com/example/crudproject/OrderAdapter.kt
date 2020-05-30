package com.example.crudproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.util.ArrayList

class OrderAdapter(private val context: Context, private val orderModelArrayList: ArrayList<OrderModel>) :
    BaseAdapter() {
    override fun getCount(): Int {
        return orderModelArrayList.size
    }

    override fun getItem(position: Int): Any {
        return orderModelArrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder

        if (convertView == null) {
            holder = ViewHolder()
            val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.lv_orderlist, null, true)

            holder.tvid = convertView!!.findViewById(R.id.id) as TextView
            holder.tviduser = convertView!!.findViewById(R.id.userIds) as TextView
            holder.tvtotproduct = convertView!!.findViewById(R.id.totalproduct) as TextView
            holder.tvtotprices = convertView!!.findViewById(R.id.totalprices) as TextView
            holder.tvname= convertView!!.findViewById(R.id.name) as TextView
            holder.tvaddress= convertView!!.findViewById(R.id.address) as TextView
            holder.tvphone= convertView!!.findViewById(R.id.phone) as TextView
            holder.tvsend= convertView!!.findViewById(R.id.send) as TextView
            holder.tvduration= convertView!!.findViewById(R.id.duration) as TextView
            holder.tvbank= convertView!!.findViewById(R.id.bank) as TextView
            holder.tvrek= convertView!!.findViewById(R.id.rek) as TextView
            holder.tvnamerek= convertView!!.findViewById(R.id.namerek) as TextView
            holder.tvdate= convertView!!.findViewById(R.id.date) as TextView
            holder.tvlimitdate= convertView!!.findViewById(R.id.limitdate) as TextView
            holder.tvstatuspay= convertView!!.findViewById(R.id.statuspay) as TextView
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        holder.tvid!!.text = "ID : " + orderModelArrayList[position].getIds()
        holder.tviduser!!.text="User ID : "+ orderModelArrayList[position].getUserIds()
        holder.tvtotproduct!!.text="Total Product : "+ orderModelArrayList[position].getTotalProducts()
        holder.tvtotprices!!.text="Total Prices : " + orderModelArrayList[position].getTotalPricess()
        holder.tvname!!.text="Name : "+orderModelArrayList[position].getNames()
        holder.tvaddress!!.text="Address : "+orderModelArrayList[position].getaddress()
        holder.tvphone!!.text="Phone : "+orderModelArrayList[position].getPhones()
        holder.tvsend!!.text="Cost Send : "+orderModelArrayList[position].getSends()
        holder.tvduration!!.text="Cost Duration : "+orderModelArrayList[position].getDurations()
        holder.tvbank!!.text="Bank : "+orderModelArrayList[position].getBanks()
        holder.tvrek!!.text="Rekening : "+orderModelArrayList[position].getReks()
        holder.tvnamerek!!.text="Name Rek : "+ orderModelArrayList[position].getNamereks()
        holder.tvdate!!.text="Date : "+orderModelArrayList[position].getDates()
        holder.tvlimitdate!!.text="Limit date : "+orderModelArrayList[position].getLimitdates()
        holder.tvstatuspay!!.text="Status Pay : "+orderModelArrayList[position].getStatuspays()
        return convertView
    }

    private inner class ViewHolder {
        var tvid: TextView? = null
        var tviduser: TextView? = null
        var tvtotproduct: TextView? = null
        var tvtotprices: TextView? = null
        var tvname: TextView? = null
        var tvaddress: TextView? = null
        var tvphone: TextView? = null
        var tvsend: TextView? = null
        var tvduration: TextView? = null
        var tvbank: TextView? = null
        var tvrek: TextView? = null
        var tvnamerek: TextView? = null
        var tvdate: TextView? = null
        var tvlimitdate: TextView? = null
        var tvstatuspay: TextView?=null
    }
}
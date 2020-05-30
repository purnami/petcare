package com.example.crudproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.util.ArrayList

class DetailOrderAdapter(private val context: Context, private val detailOrderModelArrayList: ArrayList<DetailOrderModel>) :
    BaseAdapter() {
    override fun getCount(): Int {
        return detailOrderModelArrayList.size
    }

    override fun getItem(position: Int): Any {
        return detailOrderModelArrayList[position]
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
            convertView = inflater.inflate(R.layout.lv_detailorder, null, true)

            holder.tvid = convertView!!.findViewById(R.id.id) as TextView
            holder.tvidorder = convertView!!.findViewById(R.id.orderid) as TextView
            holder.tvidproduct = convertView!!.findViewById(R.id.productid) as TextView
            holder.tvtotalproduct = convertView!!.findViewById(R.id.totalproduct) as TextView

            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        holder.tvid!!.text="ID : "+detailOrderModelArrayList[position].getIds()
        holder.tvidorder!!.text="Order ID : "+detailOrderModelArrayList[position].getOrderIds()
        holder.tvidproduct!!.text="Product ID : "+detailOrderModelArrayList[position].getProductIds()
        holder.tvtotalproduct!!.text="Total Product : "+detailOrderModelArrayList[position].getTotalProducts()
        return convertView

    }
    private inner class ViewHolder {
        var tvid: TextView? = null
        var tvidorder: TextView? = null
        var tvidproduct: TextView? = null
        var tvtotalproduct: TextView? = null
    }
}
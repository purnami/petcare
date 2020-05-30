package com.example.crudproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.util.ArrayList

class BasketListAdapter(private val context: Context, private val basketModelArrayList: ArrayList<BasketModel>) :
    BaseAdapter() {
    override fun getCount(): Int {
        return basketModelArrayList.size
    }
    override fun getItem(position: Int): Any {
        return basketModelArrayList[position]
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
            convertView = inflater.inflate(R.layout.lv_basketlist, null, true)

            holder.tvid = convertView!!.findViewById(R.id.id) as TextView
            holder.tviduser = convertView!!.findViewById(R.id.userid) as TextView
            holder.tvidproduct = convertView!!.findViewById(R.id.productid) as TextView
            holder.tvtotproduct= convertView!!.findViewById(R.id.totproduct) as TextView
            holder.ischeck=convertView!!.findViewById(R.id.ischecked) as TextView
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        holder.tvid!!.text = "ID : " + basketModelArrayList[position].getid()
        holder.tviduser!!.text = "UserId : " + basketModelArrayList[position].getuserid()
        holder.tvidproduct!!.text = "ProductId : " + basketModelArrayList[position].getproductid()
        holder.tvtotproduct!!.text = "TotalProduct: " + basketModelArrayList[position].gettotalproduct()
        holder.ischeck!!.text="Ischecked : " +basketModelArrayList[position].getischecked()

        return convertView
    }

    private inner class ViewHolder {
        var tvid: TextView? = null
        var tviduser: TextView? = null
        var tvidproduct: TextView? = null
        var tvtotproduct: TextView? = null
        var ischeck: TextView? = null
    }
}

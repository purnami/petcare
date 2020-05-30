package com.example.crudproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import java.util.ArrayList

class InvoiceProductAdapter(private val context: Context, private val invoiceProductArrayList: ArrayList<DetailOrderModel>) :
    BaseAdapter() {

    internal lateinit var dataBaseHandlerr: DataBaseHandler
    override fun getCount(): Int {
        return invoiceProductArrayList.size
    }

    override fun getItem(position: Int): Any {
        return invoiceProductArrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder

        dataBaseHandlerr= DataBaseHandler(context)

        if (convertView == null) {
            holder = ViewHolder()
            val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.lv_productinvoice, null, true)

            holder.tvproduct = convertView!!.findViewById(R.id.invoiceproduct) as TextView
            holder.tvprices = convertView!!.findViewById(R.id.invoiceproductprices) as TextView

            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        var nameProduct=dataBaseHandlerr!!.getNameProduct(invoiceProductArrayList[position].getProductIds())
        var totalProduct = invoiceProductArrayList[position].getTotalProducts()
        var pricesProduct = dataBaseHandlerr!!.getPricesProduct(invoiceProductArrayList[position].getProductIds())
        holder.tvproduct!!.text = "" + nameProduct +" ("+ totalProduct +" x Rp. "+ pricesProduct +")"
        holder.tvprices!!.text = "Rp. " + (totalProduct* pricesProduct!!)

        return convertView
    }

    private inner class ViewHolder {
        var tvproduct: TextView? = null
        var tvprices: TextView? = null
    }

}
package com.example.crudproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import java.util.ArrayList

class CustomAdapterProduct(private val context: Context, private val productModelArrayList: ArrayList<ProductModel>) :
    BaseAdapter() {
    internal lateinit var dataBaseHandlerr: DataBaseHandler
    override fun getCount(): Int {
        return productModelArrayList.size
    }

    override fun getItem(position: Int): Any {
        return productModelArrayList[position]
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
            convertView = inflater.inflate(R.layout.lv_product, null, true)

            holder.tvid = convertView!!.findViewById(R.id.id_prod) as TextView
            holder.tvname = convertView!!.findViewById(R.id.name_prod) as TextView
            holder.tvprices = convertView!!.findViewById(R.id.prices) as TextView
            holder.tvstock = convertView!!.findViewById(R.id.stock) as TextView
            holder.tvimage=convertView!!.findViewById(R.id.img_product) as ImageView

            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        holder.tvid!!.text = "ID : " + productModelArrayList[position].getIds()
        holder.tvname!!.text = "Product Name: " + productModelArrayList[position].getNames()
        holder.tvprices!!.text = "Prices : " + productModelArrayList[position].getPrices()
        holder.tvstock!!.text = "Stock : " + productModelArrayList[position].getStocks()
        var productImage = dataBaseHandlerr.getImageProduct(productModelArrayList[position].getIds())
        holder.tvimage!!.setImageBitmap(productImage?.let { Utils.getImage(it) })

        return convertView
    }

    private inner class ViewHolder {
        var tvid: TextView? = null
        var tvname: TextView? = null
        var tvprices: TextView? = null
        var tvstock: TextView? = null
        var tvimage: ImageView?=null
    }
}

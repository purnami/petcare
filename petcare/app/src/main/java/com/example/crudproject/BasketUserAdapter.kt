package com.example.crudproject

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.ArrayList

class BasketUserAdapter(
    private val context: Context,
    private val basketModelArrayList: ArrayList<BasketModel>,
    var id: String?
) :
    BaseAdapter() {
    internal lateinit var dataBaseHandlerr: DataBaseHandler
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        val holder: BasketUserAdapter.ViewHolder

        dataBaseHandlerr= DataBaseHandler(context)
        if (convertView == null) {
            holder = ViewHolder()
            val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.lv_userbasket, null, true)

            holder.checkBox = convertView!!.findViewById(R.id.check) as CheckBox
            holder.imageView = convertView!!.findViewById(R.id.image_basket) as ImageView
            holder.tvname = convertView!!.findViewById(R.id.name_basket) as TextView
            holder.tvprices = convertView!!.findViewById(R.id.prices_basket) as TextView
            holder.delete=convertView!!.findViewById(R.id.delete) as ImageButton
            holder.min=convertView!!.findViewById(R.id.min) as ImageButton
            holder.plus=convertView!!.findViewById(R.id.plus) as ImageButton
            holder.totProduct=convertView!!.findViewById(R.id.totProduct) as TextView
            holder.subtotal=convertView!!.findViewById(R.id.subtotal) as TextView

            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        holder.tvname!!.text=""+dataBaseHandlerr.getNameProduct(basketModelArrayList[position].getproductid())
        holder.tvprices!!.text="Rp. "+dataBaseHandlerr.getPricesProduct(basketModelArrayList[position].getproductid())

        var productImage=dataBaseHandlerr.getImageProduct(basketModelArrayList[position].getproductid())
        holder.imageView!!.setImageBitmap(productImage?.let { Utils.getImage(it) })

        holder.totProduct!!.setText(""+basketModelArrayList[position].gettotalproduct())

        holder.min!!.setOnClickListener {
            if(holder.totProduct!!.text.toString().toInt()>1){
                dataBaseHandlerr!!.updateTotalProduct(basketModelArrayList[position].getid(), holder.totProduct!!.text.toString().toInt()-1)
                val intent = Intent(context, BasketActivity::class.java)
                intent.putExtra("userId", id)
                context.startActivity(intent)
            }
        }
        holder.plus!!.setOnClickListener {
            if(holder.totProduct!!.text.toString().toInt()< dataBaseHandlerr.getStockProduct(basketModelArrayList[position].getproductid())!!){
                dataBaseHandlerr!!.updateTotalProduct(basketModelArrayList[position].getid(), holder.totProduct!!.text.toString().toInt()+1)
                val intent = Intent(context, BasketActivity::class.java)
                intent.putExtra("userId", id)
                context.startActivity(intent)
            }
        }
        holder.delete!!.setOnClickListener {
            dataBaseHandlerr!!.deleteBasket(basketModelArrayList[position].getid())
            val intent = Intent(context, BasketActivity::class.java)
            intent.putExtra("userId", id)
            context.startActivity(intent)
        }
        holder.subtotal!!.text="Sub Total : Rp. "+
                (dataBaseHandlerr.getPricesProduct(basketModelArrayList[position].getproductid())!!
                        *basketModelArrayList[position].gettotalproduct())
        if (basketModelArrayList[position].getischecked()==1){
            holder.checkBox!!.isChecked=true
        }else{
            holder.checkBox!!.isChecked=false
        }
        holder.checkBox!!.setOnClickListener {
            if(holder.checkBox!!.isChecked==true){
                dataBaseHandlerr.updateIsChecked(basketModelArrayList[position].getid(), 1)
                val intent = Intent(context, BasketActivity::class.java)
                intent.putExtra("userId", id)
                context.startActivity(intent)
            }else{
                dataBaseHandlerr.updateIsChecked(basketModelArrayList[position].getid(), 0)
                val intent = Intent(context, BasketActivity::class.java)
                intent.putExtra("userId", id)
                context.startActivity(intent)
            }
        }
        return convertView
    }

    override fun getCount(): Int {
        return basketModelArrayList.size
    }

    override fun getItem(position: Int): Any {
        return basketModelArrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    private inner class ViewHolder {
        var checkBox: CheckBox? = null
        var imageView: ImageView? = null
        var tvname: TextView? = null
        var tvprices: TextView? = null
        var delete: ImageButton?=null
        var min: ImageButton?=null
        var plus: ImageButton?=null
        var totProduct: TextView?=null
        var subtotal: TextView?=null
    }
}
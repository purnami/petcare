package com.example.crudproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import java.util.ArrayList

class MailAdapter (private val context: Context, private val mailArrayList: ArrayList<MailModel>) :
    BaseAdapter() {

    internal lateinit var dataBaseHandlerr: DataBaseHandler
    override fun getCount(): Int {
        return mailArrayList.size
    }

    override fun getItem(position: Int): Any {
        return mailArrayList[position]
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
            convertView = inflater.inflate(R.layout.lv_mail, null, true)

            holder.tvdate = convertView!!.findViewById(R.id.datemail) as TextView
            holder.tvmessage = convertView!!.findViewById(R.id.messagemail) as TextView

            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        holder.tvdate!!.text = "" + mailArrayList[position].getDates()
        holder.tvmessage!!.text = "" + mailArrayList[position].getMessages()

        return convertView
    }

    private inner class ViewHolder {
        var tvdate: TextView? = null
        var tvmessage: TextView? = null

    }

}
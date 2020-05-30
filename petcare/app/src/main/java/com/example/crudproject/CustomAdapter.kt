package com.example.crudproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.util.ArrayList

class CustomAdapter(private val context: Context, private val userModelArrayList: ArrayList<UserModel>) :
    BaseAdapter() {
    override fun getCount(): Int {
        return userModelArrayList.size
    }

    override fun getItem(position: Int): Any {
        return userModelArrayList[position]
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
            convertView = inflater.inflate(R.layout.lv_item, null, true)

            holder.tvid = convertView!!.findViewById(R.id.id) as TextView
            holder.tvname = convertView!!.findViewById(R.id.name) as TextView
            holder.tvemail = convertView!!.findViewById(R.id.email) as TextView
            holder.tvpassword = convertView!!.findViewById(R.id.password) as TextView

            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        holder.tvid!!.text = "ID : " + userModelArrayList[position].getIds()
        holder.tvname!!.text = "Name : " + userModelArrayList[position].getNames()
        holder.tvemail!!.text = "Email : " + userModelArrayList[position].getEmails()
        holder.tvpassword!!.text = "Password : " + userModelArrayList[position].getPasswords()
        return convertView
    }
    private inner class ViewHolder {
        var tvid: TextView? = null
        var tvname: TextView? = null
        var tvemail: TextView? = null
        var tvpassword: TextView? = null
    }
}

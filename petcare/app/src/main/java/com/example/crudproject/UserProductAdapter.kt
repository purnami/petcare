package com.example.crudproject

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class UserProductAdapter(
    private val context: Context,
    private val productModelArrayList: ArrayList<ProductModel>,
    var userId: String?
) : RecyclerView.Adapter<UserProductAdapter.ItemHolder>() {
    internal lateinit var dataBaseHandlerr: DataBaseHandler
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder= LayoutInflater.from(parent.context).inflate(R.layout.grid_layout_list_user, parent, false)
        return ItemHolder(itemHolder)
    }

    override fun getItemCount(): Int {
        return  productModelArrayList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        dataBaseHandlerr= DataBaseHandler(context)
        var userproduct :ProductModel=productModelArrayList.get(position)

        var productImage = dataBaseHandlerr.getImageProduct(productModelArrayList[position].getIds())
        holder.image.setImageBitmap(productImage?.let { Utils.getImage(it) })
        holder.name.text=""+productModelArrayList[position].getNames()
        holder.prices.text="Rp. "+productModelArrayList[position].getPrices().toString()

        holder.cardview.setOnClickListener {
            val intent = Intent(context, DetailProductActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("productId", productModelArrayList[position].getIds().toString())
            context.startActivity(intent)
        }
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var image: ImageView = itemView.findViewById<ImageView>(R.id.prod_image)
        var name: TextView =itemView.findViewById<TextView>(R.id.prod_name)
        var prices: TextView =itemView.findViewById<TextView>(R.id.prod_prices)
        var cardview: RelativeLayout=itemView.findViewById(R.id.cardview)
    }
}
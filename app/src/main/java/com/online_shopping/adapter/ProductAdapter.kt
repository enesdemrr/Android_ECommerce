package com.online_shopping.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.online_shopping.R
import com.online_shopping.activities.ProductDetailActivity
import com.online_shopping.models.ProductBilgiler



class ProductAdapter(val context: Context, val allProductList: List<ProductBilgiler>?) : RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var productName = itemView.findViewById<TextView>(R.id.tvProductName)
        var productImage = itemView.findViewById<ImageView>(R.id.imgProduct)
        var productPrice = itemView.findViewById<TextView>(R.id.tvProductPrice)
        var itemcard = itemView.findViewById<CardView>(R.id.itemCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentProduct = allProductList?.get(position)
        holder.productName.text = currentProduct?.productName.toString()
        holder.productPrice.text = currentProduct?.price.toString() + "TL"
        Glide.with(context).load(currentProduct?.images!![0].normal.toString()).into(holder.productImage)
        holder.itemcard.setOnClickListener {
            val intent = Intent(context,ProductDetailActivity::class.java)
            intent.putExtra("Detail",currentProduct)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return allProductList!!.size
    }
}



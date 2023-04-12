package com.example.flipkart.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flipkart.activity.ProductDetailActivity
import com.example.flipkart.databinding.LayoutProductItemBinding
import com.example.flipkart.model.AddProductModel

class ProductAdapter(val context: Context, val list : ArrayList<AddProductModel>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding : LayoutProductItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = LayoutProductItemBinding.inflate(LayoutInflater.from(context) , parent , false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val data = list[position]

        Glide.with(context).load(data.productCoverImg).into(holder.binding.catRvImg)
        holder.binding.textViewp.text = data.productName
        holder.binding.textView2P.text = data.productCategory
        holder.binding.textView3P.text = data.productMrp

        holder.binding.buttonP.text = data.productSp

        holder.itemView.setOnClickListener {
            val intent = Intent(context , ProductDetailActivity::class.java)
            intent.putExtra("id" , list[position].productId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
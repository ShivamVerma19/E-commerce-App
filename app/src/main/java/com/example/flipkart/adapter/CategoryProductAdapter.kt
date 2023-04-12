package com.example.flipkart.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flipkart.activity.ProductDetailActivity
import com.example.flipkart.databinding.ItemCatActBinding
import com.example.flipkart.model.AddProductModel

class CategoryProductAdapter(val context: Context, val list : ArrayList<AddProductModel>) :
    RecyclerView.Adapter<CategoryProductAdapter.CategoryProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryProductViewHolder {
        val binding = ItemCatActBinding.inflate(LayoutInflater.from(context) , parent , false)
        return CategoryProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryProductViewHolder, position: Int) {
        Glide.with(context).load(list[position].productCoverImg).into(holder.binding.imageViewCatActRv)

        holder.binding.textViewCatActRv.text = list[position].productName
        holder.binding.textView2CatActRv.text = list[position].productSp

        holder.itemView.setOnClickListener {
            val intent = Intent(context , ProductDetailActivity::class.java)
            intent.putExtra("id" , list[position].productId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class CategoryProductViewHolder(val binding : ItemCatActBinding) : RecyclerView.ViewHolder(binding.root)
}
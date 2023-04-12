package com.example.flipkart.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flipkart.activity.ProductDetailActivity
import com.example.flipkart.databinding.LayoutItemCartBinding
import com.example.flipkart.roomDb.AppDatabase
import com.example.flipkart.roomDb.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartAdapter(val context : Context , val list : List<ProductModel>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>(){

    inner class CartViewHolder(val binding : LayoutItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = LayoutItemCartBinding.inflate(LayoutInflater.from(context) , parent , false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        Glide.with(context).load(list[position].productImage).into(holder.binding.cartIv)

        holder.binding.cartTv.text = list[position].productName
        holder.binding.cardTv2.text = list[position].productSp

        val dao = AppDatabase.getInstance(context).productDao()
        val obj = list[position]
        holder.binding.deleteImg.setOnClickListener {
        GlobalScope.launch(Dispatchers.IO){
            dao.deleteProduct(ProductModel(obj.prodId , obj.productName , obj.productImage , obj.productSp))
        }

        holder.itemView.setOnClickListener {
                val intent = Intent(context , ProductDetailActivity::class.java)
                intent.putExtra("id" , obj.prodId)
                context.startActivity(intent)
            }
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }
}
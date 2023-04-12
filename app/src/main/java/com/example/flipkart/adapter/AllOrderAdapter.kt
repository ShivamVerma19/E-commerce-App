package com.example.flipkart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.flipkart.databinding.AllOrderItemLayoutBinding
import com.example.flipkart.model.AllOrderModel

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AllOrderAdapter(val list : ArrayList<AllOrderModel>, val context: Context) : RecyclerView.Adapter<AllOrderAdapter.AllOrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllOrderViewHolder {
        val binding = AllOrderItemLayoutBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return AllOrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllOrderViewHolder, position: Int) {
        holder.binding.productTitle.text = list[position].name
        holder.binding.productPrice.text = list[position].price


        when(list[position].status){
            "Ordered" -> {
                holder.binding.productStatus.text = "Ordered"
            }
            "Dispatched" -> {
                holder.binding.productStatus.text = "Dispatched"
            }
            "Delivered" -> {
                holder.binding.productStatus.text = "Delivered"
            }

            "Cancelled" -> {
                holder.binding.productStatus.text = "Cancelled"
            }

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }



    inner class AllOrderViewHolder(val binding : AllOrderItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}
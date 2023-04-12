package com.example.flipkart.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.flipkart.R
import com.example.flipkart.activity.AddressActivity
import com.example.flipkart.activity.ProductDetailActivity
import com.example.flipkart.adapter.CartAdapter
import com.example.flipkart.databinding.FragmentCartBinding
import com.example.flipkart.roomDb.AppDatabase
import com.example.flipkart.roomDb.ProductModel


class CartFragment : Fragment() {
    private lateinit var binding : FragmentCartBinding
    private lateinit var list : ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(layoutInflater)

        val preference = requireContext().getSharedPreferences("info" , AppCompatActivity.MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean("isCart" , false)
        editor.apply()

        val dao = AppDatabase.getInstance(requireContext()).productDao()
        list = ArrayList()

        dao.getAllProducts().observe(requireActivity()){
            binding.cartRv.adapter = CartAdapter(requireContext() , it)
            fillData(it)

            list.clear()
            for(data in it){
                list.add(data.prodId)
            }

        }

        return binding.root
    }

    private fun fillData(data: List<ProductModel>?) {
        var total = 0

        for(item in data!!){
            total += item.productSp!!.toInt()
        }

        binding.totalCostTv.text = "Total Cost = $total"
        binding.totalItemTv.text = "Total item = ${data.size}"

        binding.checkOutBtn.setOnClickListener {
            val intent = Intent(context , AddressActivity::class.java)
            intent.putExtra("totalCost" , total.toString())

            //9
            val b = Bundle()
            b.putStringArrayList("productIds", list)
            b.putString("totalCost" , total.toString())
            intent.putExtras(b)

            startActivity(intent)
        }
    }

}
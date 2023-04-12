package com.example.flipkart.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.flipkart.R
import com.example.flipkart.adapter.CategoryAdapter
import com.example.flipkart.adapter.ProductAdapter
import com.example.flipkart.databinding.FragmentHomeBinding
import com.example.flipkart.model.AddProductModel
import com.example.flipkart.model.CategoryModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        val preference = requireContext().getSharedPreferences("info" , AppCompatActivity.MODE_PRIVATE)

        if(preference.getBoolean("isCart" , false))
            findNavController().navigate(R.id.action_homeFragment_to_cartFragment)

        getSlider()
        getCategories()
        getProducts()
        return binding.root
    }


    private fun getCategories() {
        val list = ArrayList<CategoryModel>()
        Firebase.firestore.collection("category").get()
            .addOnSuccessListener {
                list.clear()
                for(doc in it.documents){
                    val data = doc.toObject(CategoryModel::class.java)
                    list.add(data!!)
                }

                binding.categoryRv.adapter = CategoryAdapter(requireContext() , list)
            }
    }

    private fun getProducts() {
        val list = ArrayList<AddProductModel>()
        Firebase.firestore.collection("products").get()
            .addOnSuccessListener {
                list.clear()
                for(doc in it.documents){
                    val data = doc.toObject(AddProductModel::class.java)
                    list.add(data!!)
                }

               binding.productRv.adapter = ProductAdapter(requireContext() , list)
            }
    }


    private fun getSlider() {
        Firebase.firestore.collection("slider").document("items").get()
            .addOnSuccessListener {
                Glide.with(requireContext()).load(it.get("img")).into(binding.sliderImg)
            }
    }

}
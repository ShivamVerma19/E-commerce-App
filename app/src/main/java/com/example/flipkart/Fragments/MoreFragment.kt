package com.example.flipkart.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.flipkart.R
import com.example.flipkart.adapter.AllOrderAdapter
import com.example.flipkart.databinding.FragmentMoreBinding
import com.example.flipkart.model.AllOrderModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MoreFragment : Fragment() {

    private lateinit var binding: FragmentMoreBinding
    private lateinit var list : ArrayList<AllOrderModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoreBinding.inflate(layoutInflater)

        list = ArrayList()

        val preferences = requireContext().getSharedPreferences("user" , AppCompatActivity.MODE_PRIVATE)
        val userId = preferences.getString("number" , "")!!

        Firebase.firestore.collection("allOrders").whereEqualTo("userId" , userId)
            .get()
            .addOnSuccessListener {
                list.clear()

                for(doc in it){
                    val data = doc.toObject(AllOrderModel::class.java)
                    list.add(data)
                }

                binding.recyclerView.adapter = AllOrderAdapter(list,requireContext())
            }

        return binding.root
    }


}
package com.example.flipkart.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.flipkart.R
import com.example.flipkart.adapter.CategoryAdapter
import com.example.flipkart.adapter.CategoryProductAdapter
import com.example.flipkart.adapter.ProductAdapter
import com.example.flipkart.model.AddProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        val catName = intent.getStringExtra("cat")
        getCategoryProduct(catName)
    }

    private fun getCategoryProduct(catName: String?) {
        val list = ArrayList<AddProductModel>()
        Firebase.firestore.collection("products").whereEqualTo("productCategory" , catName)
            .get()
            .addOnSuccessListener {
                list.clear()
                for(doc in it.documents){
                    val data = doc.toObject(AddProductModel::class.java)
                    list.add(data!!)
                }

                val recyclerView = findViewById<RecyclerView>(R.id.catActRv)
                recyclerView.adapter = CategoryProductAdapter(this , list)
            }
    }


}
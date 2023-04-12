package com.example.flipkart.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.flipkart.MainActivity
import com.example.flipkart.R
import com.example.flipkart.databinding.ActivityProductDetailBinding
import com.example.flipkart.roomDb.AppDatabase
import com.example.flipkart.roomDb.ProductDao
import com.example.flipkart.roomDb.ProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProductDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)

        getProductDetails(intent.getStringExtra("id"))

        setContentView(binding.root)
    }

    private fun getProductDetails(prodId: String?) {

        Firebase.firestore.collection("products").document(prodId!!).get()
            .addOnSuccessListener {
                val list = it.get("productImages") as ArrayList<String>
                val prodName = it.getString("productName")
                val prodSp = it.getString("productSp")
                val desc  = it.getString("productDesc")

                binding.textViewPD.text = prodName
                binding.textView2PD.text = prodSp
                binding.textView4PD.text = desc

                val slideList = ArrayList<SlideModel>()

                for(data in list){
                    slideList.add(SlideModel(data , ScaleTypes.CENTER_CROP))
                }

                cartAction(prodId , prodName , prodSp , it.getString("productCoverImg"))
                binding.imageSlider.setImageList(slideList)
            }
            .addOnFailureListener{

            }
    }

    private fun cartAction(prodId: String, prodName: String?, prodSp: String?, coverImg: String?) {
        val db = AppDatabase.getInstance(this)
        val productDao = db.productDao()

        if(productDao.isExist(prodId) != null){
            binding.textView3PD.text = "Go To Cart"
        }

        binding.textView3PD.setOnClickListener {
            if(productDao.isExist(prodId) != null){
                openCart()
            }
            else{
                addToCart(productDao , prodId , prodName , prodSp , coverImg)
            }
        }
    }

    private fun addToCart(productDao: ProductDao, prodId: String, prodName: String?, prodSp: String?, coverImg: String?) {
        val data = ProductModel(prodId , prodName ,coverImg , prodSp)
        lifecycleScope.launch(Dispatchers.IO){
            productDao.insertProduct(data)
            Log.d("ABC" , "Added to database")
            binding.textView3PD.text = "Go To Cart"
        }
    }


    private fun openCart() {
        val preference = this.getSharedPreferences("info" , MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean("isCart" , true)
        editor.apply()

        startActivity(Intent(this , MainActivity::class.java))
        finish()
    }
}
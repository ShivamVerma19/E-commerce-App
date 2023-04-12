 package com.example.flipkart.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.flipkart.MainActivity
import com.example.flipkart.R
import com.example.flipkart.roomDb.AppDatabase
import com.example.flipkart.roomDb.ProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class CheckoutActivity : AppCompatActivity() , PaymentResultListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val checkout = Checkout()
        checkout.setKeyID("rzp_test_cQNioOI0piku4W")

        //7
        val price = intent.getStringExtra("totalCost")
        try {
            val options = JSONObject()
            options.put("name","SKART")
            //You can omit the image option  to fetch the image from the dashboard
            options.put("image","https://razorpay.com/assets/razorpay-glyph.svg")
            options.put("theme.color", "#7B3DB9");
            options.put("currency","INR");
            options.put("amount",(price!!.toInt()) *100)//pass amount in currency subunits


            val prefill = JSONObject()
            prefill.put("email","sv8719264@gmail.com")
            prefill.put("contact","6200084678")

            options.put("prefill",prefill)
            checkout.open(this,options)
        }catch (e: Exception){
            Toast.makeText(this ,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this , "Payment success" , Toast.LENGTH_SHORT).show ()

        //1
        uploadData()
    }

    //2
    private fun uploadData() {
        val id = intent.getStringArrayListExtra("productIds")

        for(currentId in id!!){
            fetchData(currentId)
        }
    }

     //3
    private fun fetchData(productId: String) {

        val dao = AppDatabase.getInstance(this).productDao()


        Firebase.firestore.collection("products").document(productId!!).get().addOnSuccessListener {

            lifecycleScope.launch(Dispatchers.IO) {
                dao.deleteProduct(ProductModel(productId))
            }
            saveData(it.getString("productName") , it.getString("productSp") , productId)
        }
            .addOnFailureListener {

            }
    }

    //4
    private fun saveData(name: String?, price: String?, productId: String) {
        //5
        val preference = this.getSharedPreferences("user" , MODE_PRIVATE)
        val data = hashMapOf<String , Any>()

        data["name"] = name!!
        data["price"] = price!!
        data["productId"] = productId
        data["status"] = "Ordered"
        data["userId"] = preference.getString("number" , "")!!

        //6
        val firestore = Firebase.firestore.collection("allOrders")
        val key = firestore.document().id
        data["orderId"] = key

        firestore.document(key).set(data).addOnSuccessListener {
            Toast.makeText(this , "Order Placed" , Toast.LENGTH_SHORT).show()
            startActivity(Intent(this , MainActivity::class.java))
            finish()
        }
            .addOnFailureListener {
                Toast.makeText(this , "Error in placing order" , Toast.LENGTH_SHORT).show()
            }
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this , "Payment Failed" , Toast.LENGTH_SHORT).show()
    }
}
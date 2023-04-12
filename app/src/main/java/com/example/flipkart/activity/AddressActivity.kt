package com.example.flipkart.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.flipkart.R
import com.example.flipkart.databinding.ActivityAddressBinding
import com.example.flipkart.model.userModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.util.prefs.Preferences

class AddressActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddressBinding
    private lateinit var preferences : SharedPreferences

    //8
    private lateinit var totalCost : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        preferences = this.getSharedPreferences("user" , MODE_PRIVATE)
        setContentView(binding.root)

        totalCost = intent.getStringExtra("totalCost")!!
        loadUserInfo()

        binding.checkOutProceed.setOnClickListener {
            validateData(
                binding.userNameA.text.toString() ,
                binding.userNumberA.text.toString() ,
                binding.userState.text.toString() ,
                binding.userCity.text.toString() ,
                binding.userStreet.text.toString() ,
                binding.userPinCode.text.toString() ,
            )
        }
    }


    private fun loadUserInfo() {


        Firebase.firestore.collection("users").document(preferences.getString("number" , "")!!).get()
            .addOnSuccessListener {
                val data = it.toObject<userModel>()

                binding.userNameA.setText(data!!.userName)
                binding.userNumberA.setText(data!!.userNumber)
                binding.userState.setText(data!!.state)
                binding.userCity.setText(data!!.city)
                binding.userStreet.setText(data!!.streetName)
                binding.userPinCode.setText(data!!.pinCode)
            }
            .addOnFailureListener {

            }
    }


    private fun validateData(name: String, number: String, state: String, city: String, street: String, pincode: String) {

        if(name.isEmpty() || number.isEmpty() || state.isEmpty() || city.isEmpty() || street.isEmpty() || pincode.isEmpty()){
            Toast.makeText(this , "Please fill all field" , Toast.LENGTH_SHORT).show()
        }
        else{
            storeData(state , city , street , pincode)
        }
    }

    private fun storeData(state: String, city: String, street: String, pincode: String) {

        val data = hashMapOf<String , Any>()
        data["state"] = state
        data["city"] = city
        data["streetName"] = street
        data["pinCode"] = pincode

        Firebase.firestore.collection("users").document(preferences.getString("number" , "")!!)
            .update(data)
            .addOnSuccessListener {
                  val b = Bundle()
                  b.putStringArrayList("productIds", intent.getStringArrayListExtra("productIds"))
                  b.putString("totalCost" , totalCost)

                  val intent = Intent(this , CheckoutActivity::class.java)
                  intent.putExtras(b)

                startActivity(intent)
                }
            .addOnFailureListener {
                Toast.makeText(this , "Error in updating data" , Toast.LENGTH_SHORT).show()
            }
    }


}
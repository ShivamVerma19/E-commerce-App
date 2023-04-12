package com.example.flipkart.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.flipkart.R
import com.example.flipkart.databinding.ActivityRegisterBinding
import com.example.flipkart.model.userModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginR.setOnClickListener {
            openLogin()
        }

        binding.signInR.setOnClickListener {
            validateUser()
        }
    }

    private fun validateUser() {
        if(binding.phoneNoLoginR.text!!.isEmpty() || binding.userName.text!!.isEmpty())
            Toast.makeText(this , "Please fill all fields" , Toast.LENGTH_SHORT).show()
        else
            storeData()
    }

    private fun storeData() {
        val builder = AlertDialog.Builder(this)
            .setTitle("Loading.......")
            .setMessage("Please wait")
            .setCancelable(false)
            .create()

        builder.show()

        val preferences = this.getSharedPreferences("user" , MODE_PRIVATE)
        val editor = preferences.edit()

        editor.putString("name" , binding.userName.text.toString())
        editor.putString("number" , binding.phoneNoLoginR.text.toString())
        editor.apply()

        val data = userModel(binding.userName.text.toString() , binding.phoneNoLoginR.text.toString())


        Firebase.firestore.collection("users").document(binding.phoneNoLoginR.text.toString()).set(data)
            .addOnSuccessListener {


                Toast.makeText(this , "User Registered" , Toast.LENGTH_SHORT).show()
                builder.dismiss()
                openLogin()
            }
            .addOnFailureListener {
                builder.dismiss()
                Toast.makeText(this , "Something went wrong" , Toast.LENGTH_SHORT).show()
            }
    }

    private fun openLogin() {
        startActivity(Intent(this , LoginActivity::class.java))
        finish()
    }
}
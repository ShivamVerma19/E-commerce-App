package com.example.flipkart.activity

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.flipkart.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpL.setOnClickListener {
            startActivity(Intent(this , RegisterActivity::class.java))
            finish()
        }

        binding.loginL.setOnClickListener {
            if(binding.phoneNoLogin.text!!.isEmpty())
                Toast.makeText(this , "Please provide phone number" , Toast.LENGTH_SHORT).show()
            else
                sendOTP(binding.phoneNoLogin.text.toString())
        }
    }

    private lateinit var builder : AlertDialog
    private fun sendOTP(number: String) {
        builder = AlertDialog.Builder(this)
            .setTitle("Loading.......")
            .setMessage("Please wait")
            .setCancelable(false)
            .create()

        builder.show()

        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber("+91$number")       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {

        }

        override fun onVerificationFailed(e: FirebaseException) {

        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            builder.dismiss()
            val intent = Intent(this@LoginActivity , OTPActivity::class.java)
            intent.putExtra("verificationId" , verificationId)
            intent.putExtra("number" , binding.phoneNoLogin.text.toString())
            startActivity(intent)
        }
    }
}
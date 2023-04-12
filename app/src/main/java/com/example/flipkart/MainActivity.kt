package com.example.flipkart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.flipkart.activity.LoginActivity
import com.example.flipkart.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    var i = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(FirebaseAuth.getInstance().currentUser == null){
            startActivity(Intent(this , LoginActivity::class.java))
            finish()
        }
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        navController = navHostFragment!!.findNavController()

        val popupMenu = PopupMenu(this,null)
        popupMenu.inflate(R.menu.bottom_nav)
        binding.bottomBar.setupWithNavController(popupMenu.menu , navController)

        navController.addOnDestinationChangedListener(object : NavController.OnDestinationChangedListener{
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                title = when(destination.id){
                    R.id.homeFragment -> "Home"
                    R.id.cartFragment -> "My Cart"
                    R.id.moreFragment -> "More"
                    else -> "Skart"
                }
            }

        })



        binding.bottomBar.onItemSelected = {
            when(it){
                0 ->{
                    i = 0
                    navController.navigate(R.id.homeFragment)
                }
                1 -> i = 1
                2 -> i = 2
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(i == 0) {
            finish()
        }
    }


}
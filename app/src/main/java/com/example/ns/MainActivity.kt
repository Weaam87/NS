package com.example.ns

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.ns.navigation.FragmentsNavigation
import com.example.ns.ui.HomeFragment
import com.example.ns.ui.LoginFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), FragmentsNavigation {

    private lateinit var fAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fAuth = Firebase.auth

        val currentUser = fAuth.currentUser
        // Open home screen directly if the user already logged in
        if (currentUser != null) {
            supportFragmentManager.beginTransaction().add(R.id.container, HomeFragment())
                .addToBackStack(null).commit()
        }else{
            // launch the login fragment
            supportFragmentManager.beginTransaction().add(R.id.container, LoginFragment()).commit()
        }
    }

    override fun navigateFragment(fragment: Fragment, addToStack: Boolean) {
        val transaction =
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment)

        if (addToStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
}
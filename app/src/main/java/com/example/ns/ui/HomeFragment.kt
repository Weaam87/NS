package com.example.ns.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.ns.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private lateinit var bottomNavigation : BottomNavigationView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        bottomNavigation = view.findViewById(R.id.bottom_navigation)

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home -> {
                    // Respond to home click
                    Toast.makeText(context, "Home", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.support -> {
                    // Respond to support click
                    Toast.makeText(context, "Support", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.settings -> {
                    // Respond to settings click
                    true
                }
                R.id.location -> {
                    // Respond to location click
                    true
                }
                else -> false
            }
        }


        view.findViewById<ImageView>(R.id.img_log_out).setOnClickListener {
            Firebase.auth.signOut()
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
        return view
    }

}
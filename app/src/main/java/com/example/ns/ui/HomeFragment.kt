package com.example.ns.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private lateinit var bottomNavigation: BottomNavigationView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        bottomNavigation = view.findViewById(R.id.bottom_navigation)

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    // Respond to home click
                    Toast.makeText(context, "Home", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.support -> {
                    // Respond to support click by open whatsApp
                    val url =
                        "https://api.whatsapp.com/send?phone=601121102794&text=Hello%20there"

                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        this.data = Uri.parse(url)
                        this.`package` = "com.whatsapp"
                    }

                    try {
                        context?.startActivity(intent)
                    } catch (ex: ActivityNotFoundException) {

                        Toast.makeText(context, "WhatsApp is not installed", Toast.LENGTH_SHORT)
                            .show()
                    }
                    true
                }
                R.id.settings -> {
                    // Respond to settings click
                    Toast.makeText(context, "Settings", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.location -> {
                    // Respond to location click
                    findNavController().navigate(R.id.action_homeFragment_to_locationFragment)
                    true
                }
                else -> false
            }
        }



        //sign out
        view.findViewById<ImageView>(R.id.img_log_out).setOnClickListener {
            //confirmation before sign out
            MaterialAlertDialogBuilder(requireContext())
                .setMessage(getString(R.string.sign_out))
                .setCancelable(true)
                .setNegativeButton(getString(R.string.no)) { _, _ -> }
                .setPositiveButton(getString(R.string.yes)) { _, _ ->
                    Firebase.auth.signOut()
                    findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
                }.show()
        }
        return view
    }

}
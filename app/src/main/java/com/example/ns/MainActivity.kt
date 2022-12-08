package com.example.ns

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.ns.navigation.FragmentsNavigation
import com.example.ns.ui.LoginFragment

class MainActivity : AppCompatActivity(), FragmentsNavigation {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // launch the login fragment
        supportFragmentManager.beginTransaction().add(R.id.container, LoginFragment()).commit()
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
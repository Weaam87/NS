package com.example.ns

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ns.ui.LoginFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // launch the login fragment
        supportFragmentManager.beginTransaction().add(R.id.container, LoginFragment()).commit()
    }
}
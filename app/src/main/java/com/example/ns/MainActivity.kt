package com.example.ns

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import com.example.ns.ui.HomeActivity
import com.example.ns.ui.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var fAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Disable screenshots and screen recording
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)

        fAuth = Firebase.auth
        val currentUser = fAuth.currentUser

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        fAuth = Firebase.auth

        findViewById<Button>(R.id.register).setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.login_btn).setOnClickListener {
            validateForm()
        }

        // Open home screen directly if the user already logged in
        if (currentUser != null) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    // Check the entries validations
    private fun validateForm() {
        val icon = AppCompatResources.getDrawable(this, R.drawable.warning)
        icon?.setBounds(0, 0, icon.intrinsicWidth, icon.intrinsicHeight)

        when {
            TextUtils.isEmpty(username.text.toString().trim()) -> {
                username.setError("Please Enter Username", icon)
            }

            TextUtils.isEmpty(password.text.toString().trim()) -> {
                password.setError("Please Enter Password", icon)
            }

            username.text.toString().isNotEmpty() && password.text.toString().isNotEmpty() -> {
                if (username.text.toString().matches(
                        Regex("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+")
                    )
                ) {
                    firebaseSignIn()
                } else {
                    username.setError("Please Enter valid Email", icon)
                }
            }
        }
    }

    private fun firebaseSignIn() {
        fAuth.signInWithEmailAndPassword(
            username.text.toString(), password.text.toString()
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
            }
        }
    }

}
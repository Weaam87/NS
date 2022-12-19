package com.example.ns.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import com.example.ns.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var confPassword: EditText
    private lateinit var fAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        username = findViewById(R.id.username_reg)
        password = findViewById(R.id.password_reg)
        confPassword = findViewById(R.id.confirm_password)
        fAuth = Firebase.auth

        findViewById<Button>(R.id.register_reg).setOnClickListener {
            validateEmptyForm()
        }
    }

    // Check the entries validations
    private fun validateEmptyForm() {
        val icon = AppCompatResources.getDrawable(this, R.drawable.warning)
        icon?.setBounds(0, 0, icon.intrinsicWidth, icon.intrinsicHeight)

        when {
            TextUtils.isEmpty(username.text.toString().trim()) -> {
                username.setError("Please Enter Username", icon)
            }

            TextUtils.isEmpty(password.text.toString().trim()) -> {
                password.setError("Please Enter Password", icon)
            }

            TextUtils.isEmpty(confPassword.text.toString().trim()) -> {
                confPassword.setError("Please Confirm Your Password", icon)
            }

            username.text.toString().isNotEmpty() && password.text.toString().isNotEmpty() &&
                    confPassword.text.toString().isNotEmpty() -> {
                if (username.text.toString().matches(
                        Regex("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+")
                    )
                ) {
                    if (password.text.toString().length >= 6) {
                        if (password.text.toString() == confPassword.text.toString()) {
                            firebaseSignUp()
                        } else {
                            confPassword.setError("Password Did Not Match", icon)
                        }
                    } else {
                        password.setError("Please Enter at least 6 characters or numbers", icon)
                    }
                } else {
                    username.setError("Please Enter valid Email", icon)
                }
            }
        }
    }

    private fun firebaseSignUp() {
        fAuth.createUserWithEmailAndPassword(
            username.text.toString(), password.text.toString()
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Register Successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}
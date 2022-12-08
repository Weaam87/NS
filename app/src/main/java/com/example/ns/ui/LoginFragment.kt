package com.example.ns.ui

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import com.example.ns.R
import com.example.ns.navigation.FragmentsNavigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {


    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var fAuth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        username = view.findViewById(R.id.username)
        password = view.findViewById(R.id.password)
        fAuth = Firebase.auth

        view.findViewById<Button>(R.id.register).setOnClickListener {
            val navRegister = activity as FragmentsNavigation
            navRegister.navigateFragment(RegisterFragment(), true)
        }

        view.findViewById<Button>(R.id.login_btn).setOnClickListener {
            validateForm()
        }
        return view
    }

    // Check the entries validations
    private fun validateForm() {
        val icon = AppCompatResources.getDrawable(requireContext(), R.drawable.warning)
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
            username.text.toString(), password.text.toString()).addOnCompleteListener {
                task ->
            if (task.isSuccessful) {
                val navHome = activity as FragmentsNavigation
                navHome.navigateFragment(HomeFragment(), true)
                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, task.exception?.message, Toast.LENGTH_LONG).show()
            }
        }
    }

}
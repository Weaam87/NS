package com.example.ns.ui

import android.app.ProgressDialog.show
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

class RegisterFragment : Fragment() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var confPassword: EditText
    private lateinit var fAuth : FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        username = view.findViewById(R.id.username_reg)
        password = view.findViewById(R.id.password_reg)
        confPassword = view.findViewById(R.id.confirm_password)
        fAuth = Firebase.auth

        view.findViewById<Button>(R.id.register_reg).setOnClickListener {
            validateEmptyForm()
        }
        return view
    }


    // Check the entries validations
    private fun validateEmptyForm() {
        val icon = AppCompatResources.getDrawable(requireContext(), R.drawable.warning)
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
                val navHome = activity as FragmentsNavigation
                navHome.navigateFragment(HomeFragment(), true)
                Toast.makeText(context, "Register Successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, task.exception?.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}
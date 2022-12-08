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


class LoginFragment : Fragment() {


    private lateinit var username: EditText
    private lateinit var password: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        username = view.findViewById(R.id.username)
        password = view.findViewById(R.id.password)

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
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    username.setError("Please Enter valid Email", icon)
                }
            }
        }
    }

}
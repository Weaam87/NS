package com.example.ns.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.ns.MainActivity
import com.example.ns.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        val changePasswordBtn: Button = findViewById(R.id.btn_change_password)
        val currentPasswordTextInputLayout: View =
            findViewById(R.id.current_password_textInputLayout)
        val currentPasswordEditText: EditText = findViewById(R.id.current_password)
        val newPasswordTextInputLayout: View = findViewById(R.id.new_password_textInputLayout)
        val newPasswordEditText: EditText = findViewById(R.id.new_password)
        val confirmPasswordTextInputLayout: View =
            findViewById(R.id.confirm_password_textInputLayout_setting)
        val confirmPasswordEditText: EditText = findViewById(R.id.confirm_password_setting)
        val confirmChangeBtn: Button = findViewById(R.id.btn_confirm_change)
        val deleteAccountBtn : Button = findViewById(R.id.btn_delete_account)

        currentPasswordTextInputLayout.visibility = View.GONE
        newPasswordTextInputLayout.visibility = View.GONE
        confirmPasswordTextInputLayout.visibility = View.GONE
        confirmChangeBtn.visibility = View.GONE

        changePasswordBtn.setOnClickListener {
            currentPasswordTextInputLayout.visibility = View.VISIBLE
            newPasswordTextInputLayout.visibility = View.VISIBLE
            confirmPasswordTextInputLayout.visibility = View.VISIBLE
            confirmChangeBtn.visibility = View.VISIBLE
        }

        fun changePassword() {

            if (currentPasswordEditText.text.isNotEmpty() &&
                newPasswordEditText.text.isNotEmpty() &&
                confirmPasswordEditText.text.isNotEmpty()
            ) {

                if (newPasswordEditText.text.toString() == confirmPasswordEditText.text.toString()) {

                    if (user != null && user.email != null) {
                        val credential = EmailAuthProvider
                            .getCredential(user.email!!, currentPasswordEditText.text.toString())

                        // Prompt the user to re-provide their sign-in credentials
                        user.reauthenticate(credential)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    Toast.makeText(
                                        this, "Re-Authentication success.", Toast.LENGTH_SHORT
                                    ).show()
                                    user.updatePassword(newPasswordEditText.text.toString())
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                Toast.makeText(
                                                    this,
                                                    "Password changed successfully.",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                auth.signOut()
                                                val intent = Intent(this, MainActivity::class.java)
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                                startActivity(intent)
                                                finish()
                                            }
                                        }

                                } else {
                                    Toast.makeText(
                                        this, "Re-Authentication failed.", Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    }
                } else {
                    Toast.makeText(this, "Password mismatching.", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "Please enter all the fields.", Toast.LENGTH_SHORT).show()
            }

        }

        confirmChangeBtn.setOnClickListener {
            changePassword()
        }

        fun deleteAccount() {
            user?.delete()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    Toast.makeText(this, "Your account has been deleted", Toast.LENGTH_SHORT).show()
                }
            }
        }
        deleteAccountBtn.setOnClickListener {
            //confirmation before delete account
            MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.delete_account_confirmation))
                .setMessage(getString(R.string.can_not_undo))
                .setCancelable(true)
                .setNegativeButton(getString(R.string.no)) { _, _ -> }
                .setPositiveButton(getString(R.string.yes)) { _, _ ->
                    deleteAccount()
                }.show()
        }
    }

}
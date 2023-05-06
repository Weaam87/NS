package com.example.ns.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ns.MainActivity
import com.example.ns.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var fAuth: FirebaseAuth

    // Declaring handler, runnable and time in milli seconds
    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable
    private var mTime: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        fAuth = Firebase.auth
        // Initializing the handler and the runnable
        mHandler = Handler(Looper.getMainLooper())

        bottomNavigation = findViewById(R.id.bottom_navigation)

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    // Respond to home click
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.support -> {
                    // Respond to support click by open whatsApp
                    val url =
                        "https://api.whatsapp.com/send?phone=601121102794&text=Hello%20there"

                    val intent = Intent(ACTION_VIEW).apply {
                        this.data = Uri.parse(url)
                        this.`package` = "com.whatsapp"
                    }

                    try {
                        this.startActivity(intent)
                    } catch (ex: ActivityNotFoundException) {

                        Toast.makeText(this, "WhatsApp is not installed", Toast.LENGTH_SHORT)
                            .show()
                    }
                    true
                }
                R.id.settings -> {
                    // Respond to settings click
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.location -> {
                    // Respond to location click
                    val intent = Intent(this, LocationActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
        mRunnable = Runnable {
            fAuth.signOut()
            Toast.makeText(applicationContext, "Auto sign out ", Toast.LENGTH_SHORT)
                .show()
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        //Start the handler
        startHandler()

        //sign out
        findViewById<ImageView>(R.id.img_log_out_a).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            Firebase.auth.signOut()
        }
    }

    // When the screen is touched or motion is detected
    override fun onTouchEvent(event: MotionEvent?): Boolean {

        // Removes the handler callbacks (if any)
        stopHandler()

        // Runs the handler (for the specified time)
        // If any touch or motion is detected before
        // the specified time, this override function is again called
        startHandler()

        return super.onTouchEvent(event)
    }

    // start handler function
    private fun startHandler() {
        mHandler.postDelayed(mRunnable, mTime)
    }

    // stop handler function
    private fun stopHandler() {
        mHandler.removeCallbacks(mRunnable)
    }

    override fun onStop() {
        stopHandler()
        super.onStop()
    }

    override fun onResume() {
        startHandler()
        super.onResume()
    }
}
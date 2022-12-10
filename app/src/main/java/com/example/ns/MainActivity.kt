package com.example.ns

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var fAuth: FirebaseAuth

    // Declaring handler, runnable and time in milli seconds
    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable
    private var mTime: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fAuth = Firebase.auth

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val currentUser = fAuth.currentUser
        // Open home screen directly if the user already logged in
        if (currentUser != null) {
            navController.navigate(R.id.homeFragment)
        } else {
            // launch the login fragment
            navController.navigate(R.id.loginFragment)
        }

        // Initializing the handler and the runnable
        mHandler = Handler(Looper.getMainLooper())
        mRunnable = Runnable {
            if (currentUser != null) {
                fAuth.signOut()
                navController.navigate(R.id.action_homeFragment_to_loginFragment)

            }
        }

        //Start the handler
        startHandler()


        //This ensures action bar (app bar) buttons, like the menu option are visible.
        setupActionBarWithNavController(navController)
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


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
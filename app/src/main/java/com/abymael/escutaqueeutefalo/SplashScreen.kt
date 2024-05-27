package com.abymael.escutaqueeutefalo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Set system UI visibility and status bar color
        window.decorView.systemUiVisibility = 0
        window.statusBarColor = ContextCompat.getColor(this, R.color.purple)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.purple)

        // Use a Handler with Looper.getMainLooper() for the delay
        Handler(Looper.getMainLooper()).postDelayed({
            // Start MainActivity after the delay
            startActivity(Intent(this, MainActivity::class.java))
            // Finish SplashScreen activity so the user cannot navigate back to it
            finish()
        }, 3500) // 2000 milliseconds delay (2 seconds)
    }
}

package com.saidooubella.auth

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.setSystemBarsLighting()

        val handler = Handler(Looper.getMainLooper())

        handler.postDelayed(2500) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}

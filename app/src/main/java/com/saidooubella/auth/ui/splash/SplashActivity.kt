package com.saidooubella.auth.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.saidooubella.auth.R
import com.saidooubella.auth.postDelayed
import com.saidooubella.auth.setSystemBarsLighting
import com.saidooubella.auth.ui.login.LoginActivity

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

package com.saidooubella.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import com.saidooubella.auth.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val binding by binding(ActivityLoginBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.setSystemBarsLighting()

        binding.loginButton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        binding.registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}

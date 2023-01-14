package com.saidooubella.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.saidooubella.auth.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private val binding by binding(ActivityRegisterBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.setSystemBarsLighting()

        binding.registerButton.setOnClickListener {
            navigateToLoginScreen()
        }

        binding.loginButton.setOnClickListener {
            navigateToLoginScreen()
        }
    }

    private fun navigateToLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
    }
}
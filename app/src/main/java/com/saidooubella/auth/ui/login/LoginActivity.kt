package com.saidooubella.auth.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.saidooubella.auth.ui.register.RegisterActivity
import com.saidooubella.auth.binding
import com.saidooubella.auth.databinding.ActivityLoginBinding
import com.saidooubella.auth.setSystemBarsLighting
import com.saidooubella.auth.ui.home.HomeActivity

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

package com.example.byahebuddy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        preferenceManager = PreferenceManager(this)

        // CHECK if already logged in - demonstrates RETRIEVE
        if (preferenceManager.isLoggedIn()) {
            // Already logged in, go directly to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnSignUp = findViewById<Button>(R.id.btnSignUp)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                // SAVE data to SharedPreferences
                val userName = email.split("@")[0] // Use email prefix as name
                preferenceManager.saveUserData(userName, email)

                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()

                // Pass data to MainActivity via Intent (still keep this for immediate use)
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("USER_EMAIL", email)
                intent.putExtra("USER_NAME", userName)
                startActivity(intent)
                finish()
            }
        }

        btnSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
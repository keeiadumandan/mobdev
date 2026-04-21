package com.example.byahebuddy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // READ - Check if already logged in, skip login screen
        val sharedPref = getSharedPreferences("ByaheBuddyPrefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("IS_LOGGED_IN", false)

        if (isLoggedIn) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        val etEmail    = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin   = findViewById<Button>(R.id.btnLogin)
        val btnSignUp  = findViewById<Button>(R.id.btnSignUp)

        btnLogin.setOnClickListener {
            val email    = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                val userName = email.split("@")[0]

                // WRITE - Save login data to SharedPreferences
                val editor = sharedPref.edit()
                editor.putBoolean("IS_LOGGED_IN", true)
                editor.putString("USER_NAME", userName)
                editor.putString("USER_EMAIL", email)
                editor.apply()

                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()

                // Intent - Navigate to MainActivity and pass data
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("USER_NAME", userName)
                intent.putExtra("USER_EMAIL", email)
                startActivity(intent)
                finish()
            }
        }

        btnSignUp.setOnClickListener {
            // Intent - Navigate to RegisterActivity (no data needed)
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
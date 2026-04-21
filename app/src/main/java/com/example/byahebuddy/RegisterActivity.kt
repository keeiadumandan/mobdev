package com.example.byahebuddy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etFullName = findViewById<EditText>(R.id.etFullName)
        val etEmail    = findViewById<EditText>(R.id.etRegEmail)
        val etPassword = findViewById<EditText>(R.id.etRegPassword)
        val etConfirm  = findViewById<EditText>(R.id.etConfirmPassword)
        val btnGetStarted = findViewById<Button>(R.id.btnGetStarted)
        val tvLogIn    = findViewById<TextView>(R.id.tvLogIn)

        tvLogIn.setOnClickListener {
            // Intent - Go back to LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        btnGetStarted.setOnClickListener {
            val name     = etFullName.text.toString()
            val email    = etEmail.text.toString()
            val password = etPassword.text.toString()
            val confirm  = etConfirm.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else if (password != confirm) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                // WRITE - Save new user data to SharedPreferences
                val sharedPref = getSharedPreferences("ByaheBuddyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putBoolean("IS_LOGGED_IN", true)
                editor.putString("USER_NAME", name)
                editor.putString("USER_EMAIL", email)
                editor.apply()

                Toast.makeText(this, "Account created! Welcome, $name!", Toast.LENGTH_SHORT).show()

                // Intent - Navigate to MainActivity and pass data
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("USER_NAME", name)
                intent.putExtra("USER_EMAIL", email)
                startActivity(intent)
                finish()
            }
        }
    }
}
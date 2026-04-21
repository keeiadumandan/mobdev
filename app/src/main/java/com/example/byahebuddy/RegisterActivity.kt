package com.example.byahebuddy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        preferenceManager = PreferenceManager(this)

        val etFullName = findViewById<EditText>(R.id.etFullName)
        val etEmail = findViewById<EditText>(R.id.etRegEmail)
        val etPassword = findViewById<EditText>(R.id.etRegPassword)
        val etConfirm = findViewById<EditText>(R.id.etConfirmPassword)
        val btnGetStarted = findViewById<Button>(R.id.btnGetStarted)
        val tvLogIn = findViewById<TextView>(R.id.tvLogIn)

        tvLogIn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        btnGetStarted.setOnClickListener {
            val name = etFullName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val confirm = etConfirm.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else if (password != confirm) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                // SAVE data to SharedPreferences
                preferenceManager.saveUserData(name, email)

                Toast.makeText(this, "Account created! Welcome, $name!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("USER_EMAIL", email)
                intent.putExtra("USER_NAME", name)
                startActivity(intent)
                finish()
            }
        }
    }
}
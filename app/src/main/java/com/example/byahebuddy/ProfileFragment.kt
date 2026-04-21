package com.example.byahebuddy

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    private lateinit var preferenceManager: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferenceManager = PreferenceManager(requireContext())

        // RETRIEVE and DISPLAY saved data
        val savedName = preferenceManager.getUserName()
        val savedEmail = preferenceManager.getUserEmail()

        val tvProfileName = view.findViewById<TextView>(R.id.tvProfileName)
        val tvProfileEmail = view.findViewById<TextView>(R.id.tvProfileEmail)

        // Display stored data (if exists) or show message
        if (savedName.isNotEmpty()) {
            tvProfileName.text = savedName
            tvProfileEmail.text = savedEmail
        } else {
            // Fallback to intent data if SharedPreferences is empty
            val intentName = activity?.intent?.getStringExtra("USER_NAME") ?: "Commuter"
            val intentEmail = activity?.intent?.getStringExtra("USER_EMAIL") ?: "user@email.com"
            tvProfileName.text = intentName
            tvProfileEmail.text = intentEmail
        }

        // EDIT button - demonstrates UPDATE functionality
        val btnEdit = view.findViewById<Button>(R.id.btnEdit)
        btnEdit.setOnClickListener {
            showEditDialog()
        }

        // LOGOUT button - demonstrates CLEAR functionality
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            showLogoutConfirmation()
        }
    }

    private fun showEditDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.edit_profile, null)
        val etNewName = dialogView.findViewById<EditText>(R.id.etNewName)
        val etNewEmail = dialogView.findViewById<EditText>(R.id.etNewEmail)

        // Pre-fill with current data
        val currentName = preferenceManager.getUserName()
        val currentEmail = preferenceManager.getUserEmail()

        if (currentName.isNotEmpty()) {
            etNewName.setText(currentName)
        }
        if (currentEmail.isNotEmpty()) {
            etNewEmail.setText(currentEmail)
        }

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Edit Profile")
        builder.setView(dialogView)
        builder.setPositiveButton("Save") { _, _ ->
            val newName = etNewName.text.toString().trim()
            val newEmail = etNewEmail.text.toString().trim()

            if (newName.isNotEmpty() || newEmail.isNotEmpty()) {
                // UPDATE data in SharedPreferences
                if (newName.isNotEmpty()) {
                    preferenceManager.updateUserName(newName)
                }
                if (newEmail.isNotEmpty()) {
                    preferenceManager.updateUserEmail(newEmail)
                }

                // Refresh display
                val tvProfileName = view?.findViewById<TextView>(R.id.tvProfileName)
                val tvProfileEmail = view?.findViewById<TextView>(R.id.tvProfileEmail)
                tvProfileName?.text = preferenceManager.getUserName()
                tvProfileEmail?.text = preferenceManager.getUserEmail()

                Toast.makeText(requireContext(), "Profile Updated!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "No changes made", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun showLogoutConfirmation() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you want to logout? This will clear your saved session.")
        builder.setPositiveButton("Logout") { _, _ ->
            // CLEAR all SharedPreferences data
            preferenceManager.clearUserData()

            Toast.makeText(requireContext(), "Logged out successfully!", Toast.LENGTH_SHORT).show()

            // Navigate to Login with clear back stack
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            activity?.finish()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }
}
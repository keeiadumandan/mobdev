package com.example.byahebuddy

import android.content.Context
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvProfileName  = view.findViewById<TextView>(R.id.tvProfileName)
        val tvProfileEmail = view.findViewById<TextView>(R.id.tvProfileEmail)
        val btnEdit        = view.findViewById<Button>(R.id.btnEdit)
        val btnLogout      = view.findViewById<Button>(R.id.btnLogout)

        // READ - Get saved user data from SharedPreferences
        val sharedPref = requireContext().getSharedPreferences("ByaheBuddyPrefs", Context.MODE_PRIVATE)
        val savedName  = sharedPref.getString("USER_NAME", "")
        val savedEmail = sharedPref.getString("USER_EMAIL", "")

        if (!savedName.isNullOrEmpty()) {
            // Display data from SharedPreferences
            tvProfileName.text  = savedName
            tvProfileEmail.text = savedEmail
        } else {
            // Fallback: get data from Intent if SharedPreferences is empty
            val intentName  = activity?.intent?.getStringExtra("USER_NAME")  ?: "Commuter"
            val intentEmail = activity?.intent?.getStringExtra("USER_EMAIL") ?: "user@email.com"
            tvProfileName.text  = intentName
            tvProfileEmail.text = intentEmail
        }

        // UPDATE - Edit profile button
        btnEdit.setOnClickListener {
            showEditDialog(view)
        }

        // CLEAR - Logout button
        btnLogout.setOnClickListener {
            showLogoutConfirmation()
        }
    }

    private fun showEditDialog(view: View) {
        val dialogView  = LayoutInflater.from(requireContext()).inflate(R.layout.edit_profile, null)
        val etNewName   = dialogView.findViewById<EditText>(R.id.etNewName)
        val etNewEmail  = dialogView.findViewById<EditText>(R.id.etNewEmail)

        // READ - Pre-fill dialog with current data
        val sharedPref    = requireContext().getSharedPreferences("ByaheBuddyPrefs", Context.MODE_PRIVATE)
        val currentName   = sharedPref.getString("USER_NAME", "")
        val currentEmail  = sharedPref.getString("USER_EMAIL", "")

        etNewName.setText(currentName)
        etNewEmail.setText(currentEmail)

        AlertDialog.Builder(requireContext())
            .setTitle("Edit Profile")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val newName  = etNewName.text.toString().trim()
                val newEmail = etNewEmail.text.toString().trim()

                if (newName.isNotEmpty() || newEmail.isNotEmpty()) {
                    // UPDATE - Write new values to SharedPreferences
                    val editor = sharedPref.edit()
                    if (newName.isNotEmpty())  editor.putString("USER_NAME", newName)
                    if (newEmail.isNotEmpty()) editor.putString("USER_EMAIL", newEmail)
                    editor.apply()

                    // Refresh the displayed text on screen
                    view.findViewById<TextView>(R.id.tvProfileName).text  = sharedPref.getString("USER_NAME", "")
                    view.findViewById<TextView>(R.id.tvProfileEmail).text = sharedPref.getString("USER_EMAIL", "")

                    Toast.makeText(requireContext(), "Profile Updated!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "No changes made", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun showLogoutConfirmation() {
        AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout? This will clear your saved session.")
            .setPositiveButton("Logout") { _, _ ->
                // CLEAR - Wipe all SharedPreferences data on logout
                val sharedPref = requireContext().getSharedPreferences("ByaheBuddyPrefs", Context.MODE_PRIVATE)
                sharedPref.edit().clear().apply()

                Toast.makeText(requireContext(), "Logged out successfully!", Toast.LENGTH_SHORT).show()

                // Intent - Go to LoginActivity and clear the back stack
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                activity?.finish()
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
package com.example.byahebuddy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Navigate to Routes fragment when Find Routes is clicked
        view.findViewById<Button>(R.id.btnFindRoutes).setOnClickListener {
            findNavController().navigate(R.id.action_home_to_routes)
        }

        // Navigate to Tools when Check Fares is clicked
        view.findViewById<Button>(R.id.btnCheckFares).setOnClickListener {
            findNavController().navigate(R.id.toolsFragment)
        }
    }
}
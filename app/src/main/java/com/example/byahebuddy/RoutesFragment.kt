package com.example.byahebuddy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

class RoutesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_routes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etFrom = view.findViewById<EditText>(R.id.etFrom)
        val etTo = view.findViewById<EditText>(R.id.etTo)
        val btnSearch = view.findViewById<Button>(R.id.btnSearch)
        val tvResult1 = view.findViewById<TextView>(R.id.tvResult1)
        val tvResult2 = view.findViewById<TextView>(R.id.tvResult2)

        btnSearch.setOnClickListener {
            val from = etFrom.text.toString()
            val to = etTo.text.toString()

            if (from.isNotEmpty() && to.isNotEmpty()) {
                tvResult1.visibility = View.VISIBLE
                tvResult2.visibility = View.VISIBLE
                tvResult1.text = "🚌 Recommended Route\n$from → $to\nJeepney Route 20 → ₱13 Regular | ₱11 Student\nEstimated time: 25 mins"
                tvResult2.text = "🚌 Alternative Route\n$from → $to (via Commonwealth)\nJeepney Route 13 → ₱25 Regular | ₱20 Student\nEstimated time: 20 mins"
            }
        }
    }
}
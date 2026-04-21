package com.example.byahebuddy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

class ToolsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tools, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etFrom = view.findViewById<EditText>(R.id.etCalcFrom)
        val etTo = view.findViewById<EditText>(R.id.etCalcTo)
        val btnCalculate = view.findViewById<Button>(R.id.btnCalculate)
        val tvResult = view.findViewById<TextView>(R.id.tvFareResult)

        btnCalculate.setOnClickListener {
            val from = etFrom.text.toString()
            val to = etTo.text.toString()
            if (from.isNotEmpty() && to.isNotEmpty()) {
                tvResult.visibility = View.VISIBLE
                tvResult.text = """
                    Route Breakdown:
                    📍 $from → $to
                    
                    🚌 Jeepney Route 20 — ₱13
                    🚌 Jeepney Route 13 — ₱13
                    
                    Total per person: ₱26
                    Estimated time: 35-45 mins
                """.trimIndent()
            }
        }
    }
}
package com.example.moneyflow

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.moneyflow.DATA.Database
import java.util.zip.Inflater

class home : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_home, container, false)
        sharedPreferences = requireContext().getSharedPreferences("notes",Context.MODE_PRIVATE)


        val totalUang = Database.getInstance(requireContext()).UserDao().getTotalUang()
        val txttotal: TextView = v.findViewById(R.id.total_uang)
        txttotal.text = format_Rp(totalUang)

        editText =v.findViewById(R.id.notes)
        val savedText = sharedPreferences.getString("savedText", "")
        editText.setText(savedText)

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not used in this example
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not used in this example
            }

            override fun afterTextChanged(s: Editable?) {
                // Save the text to SharedPreferences when it changes
                val editor = sharedPreferences.edit()
                editor.putString("savedText", s.toString())
                editor.apply()
            }
        })

        return v
    }

    override fun onResume() {
        super.onResume()

        val totalUang = Database.getInstance(requireContext()).UserDao().getTotalUang()
        val txttotal: TextView? = view?.findViewById(R.id.total_uang)
        txttotal?.text = format_Rp(totalUang)
    }

    fun format_Rp(uang:Int):String{
        val formattedAngka = String.format("%,d", uang)
        return formattedAngka.replace(',', '.')
    }
}

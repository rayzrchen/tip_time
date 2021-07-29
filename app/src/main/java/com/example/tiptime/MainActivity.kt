package com.example.tiptime

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.calculateButton.setOnClickListener {
            calculateTip()
        }
        handleKeyboard()
    }

    private fun handleKeyboard() {
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                // Hide the keyboard
                val inputMethodManager =
                    getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    private fun calculateTip() {
        val cost = binding.costOfServiceEditText.text.toString().toDoubleOrNull()
        if (cost == null) {
            binding.tipResult.text = ""
            return
        }

        val percentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.2
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        val tip = cost * percentage

        val finalTip = if (binding.roundUpSwitch.isChecked) {
            ceil(tip)
        } else {
            tip
        }

        displayTip(finalTip)


    }

    private fun displayTip(finalTip: Double) {
        binding.tipResult.text =
            getString(R.string.tip_amount, NumberFormat.getCurrencyInstance().format(finalTip))
    }
}
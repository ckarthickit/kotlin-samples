package com.quickplay.vstb.hellokotlin

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random;
class MainActivity : AppCompatActivity() {

    val numberGen = Random(932342)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        val rollButton = findViewById<Button>(R.id.button_roll)
        rollButton.setOnClickListener {
            rollDice()
        }
    }

    private fun rollDice() {
        val rollText = findViewById<TextView>(R.id.roll_msg)
        toast(getString(R.string.dice_rolled));
        rollText.text = "Rolled Number is ${numberGen.nextInt(6)+1}"
    }

    private fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }

}


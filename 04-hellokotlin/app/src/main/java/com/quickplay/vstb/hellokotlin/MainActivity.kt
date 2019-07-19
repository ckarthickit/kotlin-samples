package com.quickplay.vstb.hellokotlin

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random;
class MainActivity : AppCompatActivity() {

    private val numberGen = Random(932342)
    private lateinit var diceImage: ImageView
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
        diceImage = findViewById(R.id.roll_result)
    }

    private fun rollDice() {
        //toast(getString(R.string.dice_rolled));
        val drawableResourceID = when(numberGen.nextInt(6) + 1) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            6 -> R.drawable.dice_6
            else -> R.drawable.empty_dice
        }
        diceImage.setImageResource(drawableResourceID)
    }

    @SuppressWarnings("unused")
    private fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }

}


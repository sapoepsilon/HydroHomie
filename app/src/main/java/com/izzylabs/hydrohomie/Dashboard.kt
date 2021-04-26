package com.izzylabs.hydrohomie

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_dashboard.*

class Dashboard : AppCompatActivity() {
    val TAG = "my message"
    var cupDrink: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        cupImage.setOnClickListener(listener)
    }
    val listener = View.OnClickListener { view ->
        when (view.getId()) {
            R.id.cupImage -> {

                cupDrink++
                cupAmount.text = cupDrink.toString()
                water_Amount.text = "You have had " + 8 *  cupDrink + " oz"
                Log.d(TAG, "You have clicked " + cupDrink + " " , )

            }
        }
    }



}




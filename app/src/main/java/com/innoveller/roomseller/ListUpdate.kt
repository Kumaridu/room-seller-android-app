package com.innoveller.roomseller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.card.MaterialCardView

class ListUpdate : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_booking_info2)

        val bookingRefe = findViewById<MaterialCardView>(R.id.card)

        bookingRefe.setOnClickListener() {
            val intent3 = Intent(applicationContext, BookingDetail2::class.java)

            startActivity(intent3);
        }
    }
}

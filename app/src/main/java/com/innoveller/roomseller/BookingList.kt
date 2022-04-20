package com.innoveller.roomseller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class BookingList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_list)

        val viewDetailBtn = findViewById<Button>(R.id.btn_view_detail);


        viewDetailBtn.setOnClickListener() {
            val intents = Intent(this, BookingDetail::class.java);
            startActivity(intents);
        }
    }
}
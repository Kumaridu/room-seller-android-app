package com.innoveller.roomseller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.btn_logIn);

        button.setOnClickListener() {
            intent = Intent(applicationContext, BookingList::class.java)
            startActivity(intent);
        }
    }
}

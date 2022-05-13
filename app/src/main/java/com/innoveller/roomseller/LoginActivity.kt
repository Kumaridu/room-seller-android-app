package com.innoveller.roomseller

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val button = findViewById<Button>(R.id.btn_logIn);
        val btn2 = findViewById<Button>(R.id.button2)
        val btn3 = findViewById<Button>(R.id.button3)

        btn2.setOnClickListener() {
            val intent2 = Intent(applicationContext, BookingDetail2::class.java)
            startActivity(intent2)
        }

        button.setOnClickListener() {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent);
        }

        btn3.setOnClickListener() {
            val intent3 = Intent(applicationContext, ListUpdate::class.java)
            startActivity(intent3);
        }

        //Subscribing the topic from firebase
        FirebaseMessaging.getInstance().subscribeToTopic("/topics/Hotel20")
            .addOnCompleteListener { task ->
                var msg = "You have subscribe me :) "
                if (!task.isSuccessful) {
                    msg = "Subscription Failed"
                }
                Log.d(BookingList.TAG, msg!!)
            }
    }
}

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

        button.setOnClickListener() {
            intent = Intent(applicationContext, BookingList::class.java)
            startActivity(intent);
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

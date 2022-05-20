package com.innoveller.roomseller

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging
import com.innoveller.roomseller.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val button = binding.btnLogIn

        button.setOnClickListener() {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent);
        }

        //Subscribing the topic from firebase
        FirebaseMessaging.getInstance().subscribeToTopic("/topics/Hotel20")
            .addOnCompleteListener { task ->
                var msg = "You have subscribe me :) "
                if (!task.isSuccessful) {
                    msg = "Subscription Failed"
                }
                Log.d("Login Activity", msg!!)
            }
    }
}

package com.innoveller.roomseller

import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MyBroadCastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("message")
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

//        if (context != null) {
//            AlertDialog.Builder(context)
//                .setMessage(message)
//                .setTitle("New Booking")
//                .setPositiveButton("Ok"){ _: DialogInterface, _: Int -> }
//                .create().show()
//        }
    }
}
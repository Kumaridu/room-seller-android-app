package com.innoveller.roomseller.firebase

import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFireBaseMessagingService : FirebaseMessagingService() {
    private val TAG = "MyFireBaseMessagingService"


    override fun onNewToken(token: String) {
        Log.d(TAG, "onNewToken: The token refreshed: $token")
        super.onNewToken(token)
    }

    //When the app is foreground only then we can consume data from firebase
    //Here should be the logic of how do we want to show the data from firebase
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        //message.data["body"]  => This should be from data payload of firebase
        val messageReceived = message.data["body"];
        Log.d(TAG, "onMessageReceived: message Received $messageReceived")
        if (messageReceived != null) {
            passMessageToActivity(messageReceived)
        }
    }

    private fun passMessageToActivity(message: String) {
        val intent = Intent().apply {
            action = INTENT_ACTION_SEND_MESSAGE
            putExtra("message", message)
        }

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    companion object {
        const val INTENT_ACTION_SEND_MESSAGE = "INTENT_ACTION_SEND_MESSAGE";
    }
}
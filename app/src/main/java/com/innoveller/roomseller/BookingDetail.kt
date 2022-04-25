package com.innoveller.roomseller

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class BookingDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_origin)

        val bookingRef = findViewById<TextView>(R.id.tv_booking_ref)
        val checkIn = findViewById<TextView>(R.id.tv_checkin)
        val checkOut = findViewById<TextView>(R.id.tv_checkout)
        val numRooms = findViewById<TextView>(R.id.tv_num_rooms);
        val numNights = findViewById<TextView>(R.id.tv_num_nights)
        val guestName = findViewById<TextView>(R.id.tv_guest_name)
//
        val intentThatStartThisActivity = intent;
        if(intentThatStartThisActivity != null) {
            var booking = intent.getSerializableExtra("KEY_NAME") as Booking?
            if(booking != null) {
                bookingRef.text = booking.bookingRef;
                checkIn.text = booking.checkInDate;
                checkOut.text = booking.checkoutDate
                numRooms.text = booking.numRooms.toString() + " rooms"
                numNights.text = booking.numNights.toString() + " nights"
                guestName.text = booking.guestName
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.send_confirmation, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_send_confirmation -> Toast.makeText(this, "Will send email confirmation", Toast.LENGTH_SHORT).show()
            else -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }
}
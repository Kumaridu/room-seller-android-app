package com.innoveller.roomseller

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BookingList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview);
        recyclerView.layoutManager = LinearLayoutManager(this);

        val bookingList = MockDataUtils.getBookingList();
        val adapter = BookingListRowAdapter(bookingList);

        adapter.setOnClickListener { view, booking ->
            val intentToStartDetailActivity = Intent(view?.context, BookingDetail::class.java)
            intentToStartDetailActivity.putExtra("KEY_NAME", booking)
            startActivity(intentToStartDetailActivity)
        }

        recyclerView.adapter = adapter;
    }

}
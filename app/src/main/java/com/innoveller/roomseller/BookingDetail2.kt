package com.innoveller.roomseller

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BookingDetail2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.booking_detail_mock)

        val subTotal = findViewById<TextView>(R.id.tv_sub_total)
        val downArrow = findViewById<ImageView>(R.id.imageView)
        val table = findViewById<TableLayout>(R.id.tableRate)

        subTotal.setOnClickListener() {
            if(table.visibility == View.GONE) {
                table.visibility = View.VISIBLE
            } else {
                table.visibility = View.GONE
            }
        }
        downArrow.setOnClickListener() {
            if(table.visibility == View.GONE) {
                table.visibility = View.VISIBLE
            } else {
                table.visibility = View.GONE
            }
        }
     }
}
package com.innoveller.roomseller

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TableLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class BookingDetail2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.booking_detail_mock)
//
//        val downArrow = findViewById<ImageView>(R.id.imageView5)
        val rowLayout = findViewById<ConstraintLayout>(R.id.roomTypeRow1)
//        val rowLayout2 = findViewById<ConstraintLayout>(R.id.roomTypeRow2)
        val table = findViewById<TableLayout>(R.id.tableRate)
//        val table2 = findViewById<TableLayout>(R.id.tableRate1)

        rowLayout.setOnClickListener() {
            if(table.visibility == View.GONE) {
                table.visibility = View.VISIBLE
            } else {
                table.visibility = View.GONE
            }
        }

//        rowLayout2.setOnClickListener() {
//            if(table2.visibility == View.GONE) {
//                table2.visibility = View.VISIBLE
//            } else {
//                table2.visibility = View.GONE
//            }
//        }
     }
}
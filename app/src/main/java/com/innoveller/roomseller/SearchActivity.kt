package com.innoveller.roomseller

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.innoveller.roomseller.adapter.BookingInfoViewAdapter
import com.innoveller.roomseller.databinding.ActivitySearchBinding
import com.innoveller.roomseller.helpers.CalendarAndSortingFieldHelper
import com.innoveller.roomseller.rest.dtos.Booking
import com.innoveller.roomseller.ui.booking.BookingListViewModel
import com.innoveller.roomseller.ui.booking.BookingSearchCriteria
import com.innoveller.roomseller.utilities.DateFormatUtility
import java.util.*


class SearchActivity : AppCompatActivity(){

    private lateinit var binding : ActivitySearchBinding
    private var bookingList = mutableListOf <Booking> ()
    private lateinit var searchCriteria: BookingSearchCriteria

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bookingViewModel = ViewModelProvider(this)[BookingListViewModel::class.java]
        val toolbar = binding.toolbar
        val dateChip = binding.searchByDate

        // recycler view
        val recyclerView: RecyclerView = binding.rvSearchBookingList
        var adapter = BookingInfoViewAdapter(bookingList)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        val progressBar = binding.pbSearchLoading
        val searchView = binding.searchView
        var searchQuery = ""

        bookingViewModel.getBookings().observeForever { result ->
            progressBar.visibility = View.GONE

            if(result.isEmpty()) {
                Toast.makeText(this,"No Match Found", Toast.LENGTH_LONG).show()
                Log.d(TAG, "onCreate: Search Result Empty")
            }

            bookingList.addAll(result)
            adapter.notifyDataSetChanged()
        }

        var builder: BookingSearchCriteria.BookingSearchCriteriaBuilder = BookingSearchCriteria.BookingSearchCriteriaBuilder()

        // Search By Booking reference or guest name
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchQuery = query!!

                builder.bookingRefAndGuestName(searchQuery, searchQuery)
                searchCriteria = builder.build()
                bookingViewModel.loadBookingList(progressBar, searchCriteria)
                Log.d(TAG, "onQueryTextSubmit: Search List: ${bookingList.size}")
                return false;
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                Log.d(TAG, "onQueryTextChange: I changed: $p0")
                return false
            }
        })

        var calendarChosenDate  = Date()
        var searchDateType  = "Booking Date By"


        dateChip.setOnClickListener {
            val inflater = LayoutInflater.from(this)
            val dialogLayoutView = inflater.inflate(R.layout.dialog_calendar_booking_checkin_date, null)
            Log.d(TAG, "onCreateView: This is search date type: $searchDateType")
            CalendarAndSortingFieldHelper.showCalendarWithBookingAndCheckInDateDialog(
                this, dialogLayoutView, searchDateType, calendarChosenDate) { selectedDate, dateType ->
                calendarChosenDate = selectedDate;
                searchDateType = dateType

                if(dateType == CalendarAndSortingFieldHelper.BOOKING_DATE) {
                   builder.bookingIdDate(calendarChosenDate)
                } else {
                    builder.checkInDate(calendarChosenDate)
                }

                dateChip.text = dateType + " " + DateFormatUtility.formatFriendlyDate(calendarChosenDate)
                dateChip.isCloseIconVisible = true

                searchCriteria = builder.build()
                bookingViewModel.loadBookingList(progressBar, searchCriteria);
                Log.d(TAG,"onDateFieldSearchCriteria: will send to the api")
            }
        }

        dateChip.setOnCloseIconClickListener {
            dateChip.text = "Date"
            dateChip.isCloseIconVisible = false
            bookingViewModel.loadBookingList(progressBar, searchCriteria)
        }

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    /* @SuppressLint("ResourceAsColor")
     override fun onCreateOptionsMenu(menu: Menu): Boolean {

         // Inflate the menu; this adds items to the action bar if it is present.
         menuInflater.inflate(R.menu.expandable_search, menu)

         val searchView = menu.findItem(id.expanded_search).actionView as SearchView
 //        searchView.setBackgroundColor(color.input_box_high_light_color_list)
         searchView.isIconified = false;
         searchView.queryHint = "Search by booking ref or guest name"

         searchView.setBackgroundResource(R.drawable.custom_shape)
 //        searchView.setBackgroundColor(R.color.input_box_high_light_color_list)
 //        searchView.setBackgroundColor(R.color.white)

 //        val closeBtnId = searchView.context.resources
 //            .getIdentifier("android:id/search_close_btn", null, null)
 //        val searchEditTextId = searchView.context.resources
 //            .getIdentifier("android:id/search_src_text", null, null)
 //
 ////        val searchSrcTextId = resources.getIdentifier("android:id/search_src_text", null, null)
 ////        val searchEditText = searchView.findViewById<View>(searchSrcTextId) as EditText
 //
 //
 //        val closeBtn = searchView.findViewById<ImageView>(closeBtnId)
 //        val searchEditText = searchView.findViewById<EditText>(searchEditTextId)
 //
 //        if(searchEditText != null) {
 //            searchEditText.setTextColor(Color.BLACK)
 //            searchEditText.setHintTextColor(Color.LTGRAY)
 //        }


 //        searchView.setBackgroundResource(R.color.input_box_high_light_color_list)
         return true
     } */
    companion object {
        private const val TAG = "SearchActivity"
    }

}
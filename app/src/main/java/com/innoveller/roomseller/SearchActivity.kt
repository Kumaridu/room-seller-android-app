package com.innoveller.roomseller

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
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

        bookingViewModel.getBookings().observeForever { result ->
            progressBar.visibility = View.GONE

            if(result.isEmpty() && !searchCriteria.isEmptySearchCriteria) {
                Toast.makeText(this,"No Match Found", Toast.LENGTH_LONG).show()
                Log.d(TAG, "onCreate: Search Result Empty")
            }

            bookingList.clear()
            bookingList.addAll(result)
            Log.d(TAG, "onCreate: booking list size: ${bookingList.size}")
            adapter.notifyDataSetChanged()
        }

        var builder: BookingSearchCriteria.BookingSearchCriteriaBuilder = BookingSearchCriteria.BookingSearchCriteriaBuilder()

        val closeBtnId = searchView.context.resources
            .getIdentifier("android:id/search_close_btn", null, null)
        val searchEditTextId = searchView.context.resources
            .getIdentifier("android:id/search_src_text", null, null)
        val closeBtn = searchView.findViewById<ImageView>(closeBtnId)
        val searchEditText = searchView.findViewById<EditText>(searchEditTextId)

        closeBtn?.setOnClickListener {
            Log.d(TAG, "onCreateView: I was clicked close ")
            searchEditText.clearFocus()
            builder.bookingRefAndGuestName(null, null)
            bookingViewModel.loadBookingList(progressBar, searchCriteria)
        }

        // Search By Booking reference or guest name
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val searchQuery = query!!

                builder.bookingRefAndGuestName(searchQuery, searchQuery)
                searchCriteria = builder.build()
                bookingViewModel.loadBookingList(progressBar, searchCriteria)
                Log.d(TAG, "onQueryTextSubmit: Search List: ${bookingList.size}")
                return false;
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if(query!!.isBlank()) {
                    builder.bookingRefAndGuestName(null, null)
                } else {
                    val searchQuery = query!!
                    builder.bookingRefAndGuestName(searchQuery, searchQuery)
                }

                searchCriteria = builder.build()

                bookingViewModel.loadBookingList(progressBar, searchCriteria)
                Log.d(TAG, "onQueryTextChange: I changed: $query")
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

            // Resetting both date to null
            builder.bookingIdDate(null)
            builder.checkInDate(null)

            searchCriteria =  builder.build()
            bookingViewModel.loadBookingList(progressBar, searchCriteria)
        }

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }


    companion object {
        private const val TAG = "SearchActivity"
    }

}
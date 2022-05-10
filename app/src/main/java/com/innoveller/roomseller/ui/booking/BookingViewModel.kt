package com.innoveller.roomseller.ui.booking

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.innoveller.roomseller.BookingDetail
import com.innoveller.roomseller.BookingList
import com.innoveller.roomseller.adapter.BookingInfoViewAdapter
import com.innoveller.roomseller.rest.dtos.Booking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookingViewModel : ViewModel() {

    private val bookings: MutableLiveData<List<Booking>> by lazy {
        MutableLiveData<List<Booking>>().also {
            loadUsers()
        }
    }

    fun getBookings(): LiveData<List<Booking>> {
        return bookings
    }


//    private fun getBookingList() {
//        val bookingListCall = restApi.bookingList
//
//        bookingListCall.enqueue(object: Callback<List<Booking>> {
//            override fun onResponse(call: Call<List<Booking>>, response: Response<List<Booking>>) {
//                loadingBar.visibility = View.GONE
//                if(response.isSuccessful) {
//                    val bookingListResBody = response.body()
//                    if(bookingListResBody != null) {
//                        Log.d(BookingList.TAG, "onResponse: Get Booking List")
//                        adapter = BookingInfoViewAdapter(bookingListResBody)
//                        recyclerView.adapter = adapter
//
//                        adapter.setOnClickListener { view, booking ->
//                            val intentToStartDetailActivity = Intent(view?.context, BookingDetail::class.java)
//                            intentToStartDetailActivity.putExtra(BookingDetail.BOOKING_ID, booking.id.toString())
//                            startActivity(intentToStartDetailActivity)
//                        }
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<List<Booking>>, t: Throwable) {
//                Log.d(BookingList.TAG, "onFailure: $t")
//                t.printStackTrace()
//            }
//        })
//    }

    private fun loadUsers() {
        // Do an asynchronous operation to fetch users.
    }


    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text
}
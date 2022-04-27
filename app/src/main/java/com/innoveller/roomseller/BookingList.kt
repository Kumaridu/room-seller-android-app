package com.innoveller.roomseller

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.innoveller.roomseller.adapter.BookingInfoViewAdapter
import com.innoveller.roomseller.rest.api.RestApi
import com.innoveller.roomseller.rest.api.RestApiBuilder
import com.innoveller.roomseller.rest.dtos.BookingDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookingList : AppCompatActivity() {

    // static variable at kotlin
    companion object {
        private const val TAG = "BookingList"
    }

     private lateinit var restApi: RestApi
     private lateinit var adapter: BookingInfoViewAdapter
     private lateinit var recyclerView: RecyclerView
     private lateinit var loadingBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_list)

        loadingBar = findViewById(R.id.pb_loading)
        restApi = RestApiBuilder.buildRestApi()
        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)


        getBookingList()
    }

    private fun getBookingList() {
        val bookingListCall = restApi.bookingList

        bookingListCall.enqueue(object: Callback<List<BookingDto>> {
            override fun onResponse(call: Call<List<BookingDto>>,response: Response<List<BookingDto>>) {
                loadingBar.visibility = View.GONE
                if(response.isSuccessful) {
                    val bookingListResBody = response.body()
                    if(bookingListResBody != null) {
                        Log.d(TAG, "onResponse: Get Booking List")
                        adapter =
                            BookingInfoViewAdapter(
                                bookingListResBody
                            )
                        recyclerView.adapter = adapter

                        adapter.setOnClickListener { view, booking ->
                            val intentToStartDetailActivity = Intent(view?.context, BookingDetail::class.java)
                            intentToStartDetailActivity.putExtra(BookingDetail.INTENT_KEY_BOOKING_ID, booking.id.toString())
                            startActivity(intentToStartDetailActivity)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<BookingDto>>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
                t.printStackTrace()
            }
        })
    }
}
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
import com.innoveller.roomseller.rest.dtos.Booking
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
     private lateinit var layoutManager : LinearLayoutManager

     // for infinite scroll
     private var loading = true
     var pastVisibleItems = 0
     var visibleItemCount:Int = 0
     var totalItemCount:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_list)

        loadingBar = findViewById(R.id.pb_loading)
        restApi = RestApiBuilder.buildRestApi()
        recyclerView = findViewById(R.id.recyclerview)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager


        getBookingList()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(dy > 0) {
                    visibleItemCount = recyclerView.childCount
                    totalItemCount = layoutManager.itemCount
                    pastVisibleItems = layoutManager.findFirstCompletelyVisibleItemPosition()

                    if (loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            // fetch new data
                            getBookingList()
                            loading = true;
                        }
                    }
                }
            }
        })
    }

    private fun getBookingList() {
        val bookingListCall = restApi.bookingList

        bookingListCall.enqueue(object: Callback<List<Booking>> {
            override fun onResponse(call: Call<List<Booking>>, response: Response<List<Booking>>) {
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

            override fun onFailure(call: Call<List<Booking>>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
                t.printStackTrace()
            }
        })
    }
}
package com.innoveller.roomseller

import android.content.*
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.innoveller.roomseller.firebase.MyFireBaseMessagingService.Companion.INTENT_ACTION_SEND_MESSAGE
import com.innoveller.roomseller.adapter.BookingInfoViewAdapter
import com.innoveller.roomseller.rest.api.RestApi
import com.innoveller.roomseller.rest.api.RestApiBuilder
import com.innoveller.roomseller.rest.dtos.Booking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.innoveller.roomseller.databinding.ActivityBookingListBinding

class BookingList : AppCompatActivity() {

    private lateinit var binding: ActivityBookingListBinding
    // static variable at kotlin
    companion object {
        const val TAG = "BookingList"
    }

     lateinit var receiver : BroadcastReceiver
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
        binding = ActivityBookingListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingBar = binding.pbLoading
        recyclerView = binding.recyclerview
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        restApi = RestApiBuilder.buildRestApi()

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


////         will show the message from firebase with dialog when the app is in foreground
        receiver = object: BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val message = intent?.getStringExtra("message")

//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

                if (context != null) {
                    AlertDialog.Builder(this@BookingList)
                        .setMessage(message)
                        .setTitle("New Booking")
                        .setPositiveButton("Ok"){ _: DialogInterface, _: Int -> }
                        .create().show()
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: I call called")
        Log.d(TAG, "onResume: After clicking notification. I go there not on create method")

        var filter = IntentFilter(INTENT_ACTION_SEND_MESSAGE)
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter)
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: I call called")
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver)
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
                        adapter = BookingInfoViewAdapter(bookingListResBody)
                        recyclerView.adapter = adapter

                        adapter.setOnClickListener { view, booking ->
                            val intentToStartDetailActivity = Intent(view?.context, BookingDetail::class.java)
                            intentToStartDetailActivity.putExtra(BookingDetail.BOOKING_ID, booking.id.toString())
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
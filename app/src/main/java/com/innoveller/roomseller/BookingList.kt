package com.innoveller.roomseller

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.innoveller.roomseller.rest.api.RestApi
import com.innoveller.roomseller.rest.api.RestApiBuilder
import com.innoveller.roomseller.rest.dtos.BookingDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookingList : AppCompatActivity() {

     private lateinit var restApi: RestApi
     private var bookingList: List<BookingDto> = ArrayList<BookingDto>()
     private lateinit var adapter: BookingListRowAdapter
     private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_list)

        restApi = RestApiBuilder.buildRestApi();
        recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        getBookingList()


//        getBookingById();


//        val bookingList = MockDataUtils.getBookingList();
//        getBookingList();
    }

    fun getBookingList() {
        val bookingListCall = restApi.bookingList

        bookingListCall.enqueue(object: Callback<List<BookingDto>> {
            override fun onResponse(call: Call<List<BookingDto>>,response: Response<List<BookingDto>>) {
                if(response.isSuccessful) {
                    val bookingListResBody = response.body()
                    if(bookingListResBody != null) {
                        println("Get booking list")
                        println(bookingListResBody.toString())
                        adapter = BookingListRowAdapter(bookingListResBody);
                        recyclerView.adapter = adapter

                        adapter.setOnClickListener { view, booking ->
                            val intentToStartDetailActivity = Intent(view?.context, BookingDetail::class.java)
                            intentToStartDetailActivity.putExtra("KEY_NAME",booking)
                            startActivity(intentToStartDetailActivity)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<BookingDto>>, t: Throwable) {
                println("got error")
                t.printStackTrace()
            }

        })
    }

    fun getBookingById() {
        val bookingCall = restApi.bookingById

        bookingCall.enqueue(object: Callback<BookingDto> {
            override fun onResponse(call: Call<BookingDto>, response: Response<BookingDto>) {
                if(response.isSuccessful) {
                   val booking = response.body()
                    if(booking != null) {
                        println("getting data from rest")
                        println(booking.bookingDate)
                    }

                }
            }

            override fun onFailure(call: Call<BookingDto>, t: Throwable) {
                println("got error")
                t.printStackTrace()
            }

        })
    }

}
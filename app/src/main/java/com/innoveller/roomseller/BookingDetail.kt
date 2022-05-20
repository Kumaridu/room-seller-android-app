package com.innoveller.roomseller

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.innoveller.roomseller.adapter.RoomTypeAdapter
import com.innoveller.roomseller.databinding.ActivityBookingDetailBinding
import com.innoveller.roomseller.databinding.ItemRoomTypeBinding
import com.innoveller.roomseller.mockData.MockRoomTypeInfo
import com.innoveller.roomseller.rest.api.RestApi
import com.innoveller.roomseller.rest.api.RestApiBuilder
import com.innoveller.roomseller.rest.dtos.Booking
import com.innoveller.roomseller.utilities.DateFormatUtility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class BookingDetail : AppCompatActivity() {

    private lateinit var binding: ActivityBookingDetailBinding

    companion object {
        const val BOOKING_ID = "booking_id"
        const val TAG = "BookingDetail"
    }

    private lateinit var restApi: RestApi
    private lateinit var guestEmail: String
    private lateinit var adapter: RoomTypeAdapter
    private lateinit var roomTypeRecyclerView: RecyclerView
    private lateinit var layoutManager : LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        binding.pbLoading.visibility = View.VISIBLE

        roomTypeRecyclerView = binding.rvRoomTypeList
        layoutManager = LinearLayoutManager(this)
        roomTypeRecyclerView.layoutManager = layoutManager

        restApi = RestApiBuilder.buildRestApi()

        //Get booking id from booking list click event listener or from firebase
        val bookingId = intent?.getStringExtra(BOOKING_ID)
        if(bookingId != null) {
            Log.d(TAG, "onCreate: Going to request booking info via Id: $bookingId")
            getBookingById(bookingId.toString())
        }

    }

    fun emailOnClick(view: View) {
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_send_email_confirmation, null)
//        val dialogLayout = layoutInflater.inflate(R.layout.dialog_calendar_booking_checkin_date, null)
//        val sendEmail = dialogLayout.findViewById<TextView>(R.id.edit_booking_ref)
//        sendEmail.text = guestEmail

        MaterialAlertDialogBuilder(this)
            .setTitle("Send Confirmation Email")
            .setView(dialogLayout)
            .setPositiveButton("Send Email"){ dialog, which->
                Toast.makeText(this, "Will call to back end", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                // do something on positive button click
            }
            .show()
    }

    fun printOnClick(view: View) {
        Toast.makeText(this, "I am going to print", Toast.LENGTH_SHORT).show()
    }

    private fun getBookingById(bookingId : String) {
        val bookingCall = restApi.getBookingById(bookingId)

        bookingCall.enqueue(object: Callback<Booking> {
            override fun onResponse(call: Call<Booking>, response: Response<Booking>) {
                binding.pbLoading.visibility = View.GONE
                if(response.isSuccessful) {
                    val booking = response.body()
                    if(booking != null) {
                        Log.d(TAG, "onResponse: Successfully got booking information")
                        if(booking != null) {

                            // Will update later with real api
                            var roomList = MockRoomTypeInfo.getRoomTypeList();
                            adapter = RoomTypeAdapter(roomList)
                            roomTypeRecyclerView.adapter = adapter

                            // Item divider
                            roomTypeRecyclerView.addItemDecoration(
                                DividerItemDecoration(applicationContext, layoutManager.orientation)
                            )
                            adapter.setOnClickListener { view, booking ->
                                val binding = ItemRoomTypeBinding.bind(view)
                                toggleRoomRateRow(binding)
                            }

                            updateBookingDetail(booking)
                            guestEmail = booking.customer.email
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Booking>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.printStackTrace()}")
                t.printStackTrace()
            }
        })
    }

    private fun updateBookingDetail(booking: Booking) {
        binding.tvBookingRef.text = booking.reference
        binding.tvNumGuests.text = booking.numberOfGuests.toString() + if(booking.numberOfGuests > 1) " Guests" else " Guest"
        binding.tvBookedOn.text = DateFormatUtility.formatFriendlyDateTimeWithYear(booking.bookingDate)
        binding.tvCheckIn.text = DateFormatUtility.formatFriendlyDate(booking.checkInDate)
        binding.tvCheckout.text = DateFormatUtility.formatFriendlyDate(booking.checkOutDate)
        binding.tvNumNights.text = booking.numberOfNight.toString() + if(booking.numberOfNight > 1) " nights" else " night"
        binding.tvNumRooms.text = booking.numberOfRooms.toString() + if(booking.numberOfRooms > 1) " rooms" else " room"

        binding.fragmentBookingSummaryInfo.tvGuestName.text = booking.customer.name
        binding.fragmentBookingSummaryInfo.tvNationality.text = booking.customer.nationality
        binding.fragmentBookingSummaryInfo.tvPhoneNumber.text = booking.customer.phoneNumber
        binding.fragmentBookingSummaryInfo.tvEmail.text = booking.customer.email
        binding.fragmentBookingSummaryInfo.tvSpecialRequest.text = booking.customer.specialRequest
        binding.fragmentBookingSummaryInfo.tvPaymentAmount.text = booking.payment.amount.amount.toString()
        binding.fragmentBookingSummaryInfo.tvGatewayType.text = booking.payment.method
    }

    private fun toggleRoomRateRow(binding: ItemRoomTypeBinding) {
        val table = binding.tableRate
        if(table.visibility == View.GONE) {
            table.visibility = View.VISIBLE
        } else {
            table.visibility = View.GONE
        }

    }
}
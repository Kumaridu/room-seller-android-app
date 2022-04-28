package com.innoveller.roomseller

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.innoveller.roomseller.rest.api.RestApi
import com.innoveller.roomseller.rest.api.RestApiBuilder
import com.innoveller.roomseller.rest.dtos.Booking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookingDetail : AppCompatActivity() {

    companion object {
        const val INTENT_KEY_BOOKING_ID = "INTENT_KEY_BOOKING_ID"
    }

    private val TAG = "BookingDetail"
    private lateinit var restApi: RestApi

    private lateinit var loading: ProgressBar;
    private lateinit var layout: View

    //Booking Details
    lateinit var bookingRef: TextView
    lateinit var numGuests: TextView
    lateinit var bookedOn: TextView
    lateinit var checkIn: TextView
    lateinit var checkOut: TextView
    lateinit var numRooms: TextView
    lateinit var numNights: TextView


    //Room TypeInfo
    lateinit var maxOccupancy: TextView
    lateinit var ratePlan: TextView
    lateinit var extraBedRate: TextView
    lateinit var subTotal: TextView

    //Guest Information
    lateinit var guestName: TextView
    lateinit var nationality: TextView
    lateinit var phoneNo: TextView
    lateinit var email: TextView
    lateinit var specialRequest: TextView

    //Payment Summary
    lateinit var paymentType: TextView
    lateinit var totalAmount: TextView
    lateinit var discount: TextView
    lateinit var taxes: TextView
    lateinit var convenienceFee: TextView
    lateinit var paymentAmount: TextView
    lateinit var paymentCurrency: TextView
    lateinit var gatewayType: TextView
    lateinit var transactionId: TextView
    lateinit var commission: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_detail_v3)

//        restApi = RestApiBuilder.buildRestApi()
//
//        bookingRef = findViewById(R.id.tv_booking_ref)
//        numGuests = findViewById(R.id.tv_num_guests)
//        bookedOn = findViewById(R.id.tv_bookedon)
//        checkIn = findViewById(R.id.tv_checkin)
//        checkOut = findViewById(R.id.tv_checkout)
//        numRooms = findViewById(R.id.tv_num_rooms);
//        numNights = findViewById(R.id.tv_num_nights)
//
//        maxOccupancy = findViewById(R.id.tv_max_occupancy)
//        ratePlan = findViewById(R.id.tv_rate_plan)
//        extraBedRate = findViewById(R.id.tv_extra_bed)
//        subTotal = findViewById(R.id.tv_sub_total)
//
//        guestName = findViewById(R.id.tv_guest_name)
//        nationality = findViewById(R.id.tv_nationality)
//        phoneNo = findViewById(R.id.tv_phone_no)
//        email = findViewById(R.id.tv_email)
//        specialRequest = findViewById(R.id.tv_special_request)
//
//        paymentType = findViewById(R.id.tv_payment_type)
//        totalAmount = findViewById(R.id.tv_total_amount)
//        discount = findViewById(R.id.tv_discount)
//        taxes = findViewById(R.id.tv_taxes)
//        convenienceFee = findViewById(R.id.tv_convenience_fee)
//        paymentAmount = findViewById(R.id.tv_payment_amount)
//        paymentCurrency = findViewById(R.id.tv_payment_currency)
//        gatewayType = findViewById(R.id.tv_gateway_type)
//        transactionId = findViewById(R.id.tv_transaction_id)
//        commission = findViewById(R.id.tv_commission)
//
//        loading = findViewById(R.id.pb_loading)
//        layout = findViewById(R.id.bookingDetail)
//
//        val bookingId: String? = intent?.getStringExtra(INTENT_KEY_BOOKING_ID)
//        println("This is booking id: $bookingId")
//
//        if(bookingId != null) {
//            Log.d(TAG, "onCreate: Going to request booking info via Id: $bookingId")
//            getBookingById(bookingId)
//        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.send_confirmation, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_send_confirmation -> Toast.makeText(this, "Will send email confirmation", Toast.LENGTH_SHORT).show()
            else -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getBookingById(bookingId : String) {
        val bookingCall = restApi.getBookingById(bookingId)

        bookingCall.enqueue(object: Callback<Booking> {
            override fun onResponse(call: Call<Booking>, response: Response<Booking>) {
                loading.visibility = View.GONE
                layout.visibility = View.VISIBLE
                if(response.isSuccessful) {
                    val booking = response.body()
                    if(booking != null) {
                        Log.d(TAG, "onResponse: Successfully got booking information")
                        if(booking != null) {
                            mapDtoToView(booking)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Booking>, t: Throwable) {
                println("got error")
                t.printStackTrace()
            }
        })
    }

    private fun mapDtoToView(booking: Booking) {
        bookingRef.text = booking.reference;
        numGuests.text = booking.numberOfGuests.toString() + if(booking.numberOfGuests > 1) " Guests" else " Guest"
        bookedOn.text = booking.bookingDate
        checkIn.text = booking.checkInDate;
        checkOut.text = booking.checkOutDate
        numRooms.text = booking.numberOfRooms.toString() + " rooms"
        numNights.text = booking.numberOfNight.toString() + " nights"

        maxOccupancy.text = "N/A"
        ratePlan.text = "N/A"
        extraBedRate.text = "N/A"
        subTotal.text = "N/A"

        guestName.text = booking.customer.name
        nationality.text = booking.customer.nationality
        phoneNo.text = booking.customer.phoneNumber
        email.text = booking.customer.email
        specialRequest.text = booking.customer.specialRequest

        paymentAmount.text = booking.payment.amount.amount.toString()
        paymentCurrency.text = booking.payment.amount.currency
        gatewayType.text = booking.payment.method
    }
}
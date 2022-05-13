package com.innoveller.roomseller

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.innoveller.roomseller.rest.api.RestApi
import com.innoveller.roomseller.rest.api.RestApiBuilder
import com.innoveller.roomseller.rest.dtos.Booking
import com.innoveller.roomseller.utilities.DateFormatUtility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookingDetail : AppCompatActivity() {

    companion object {
        const val BOOKING_ID = "booking_id"
        const val TAG = "BookingDetail"
    }

    private lateinit var restApi: RestApi

    private lateinit var loading: ProgressBar
    private lateinit var layout: View

    //Booking Details
    lateinit var bookingRef: TextView
    lateinit var numGuests: TextView
    lateinit var bookedOn: TextView
    lateinit var checkIn: TextView
    lateinit var checkOut: TextView
    lateinit var numNights: TextView
    lateinit var numRooms: TextView

    //Room TypeInfo
    lateinit var maxOccupancy: TextView
    lateinit var ratePlan: TextView
    lateinit var extraBedCount: TextView
    lateinit var roomTypeSubTotalAmount: TextView

    //Guest Information
    lateinit var guestName: TextView
    lateinit var nationality: TextView
    lateinit var phoneNo: TextView
    lateinit var email: TextView
    lateinit var specialRequest: TextView

    //Payment Summary
    lateinit var paymentType: TextView
    lateinit var discount: TextView
    lateinit var taxes: TextView
    lateinit var convenienceFee: TextView
    lateinit var paymentAmount: TextView
    lateinit var gatewayType: TextView
    lateinit var transactionId: TextView
    lateinit var commission: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_detail)

        restApi = RestApiBuilder.buildRestApi()

        var toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        bookingRef = findViewById(R.id.tv_booking_ref)
        numGuests = findViewById(R.id.tv_num_guests)
        bookedOn = findViewById(R.id.tv_booked_on)
        checkIn = findViewById(R.id.tv_check_in)
        checkOut = findViewById(R.id.tv_checkout)

        numNights = findViewById(R.id.tv_num_nights)
        numRooms = findViewById(R.id.tv_num_rooms)

        maxOccupancy = findViewById(R.id.tv_max_occupancy)
        ratePlan = findViewById(R.id.tv_rate_plan)
        extraBedCount = findViewById(R.id.tv_extra_bed)
        roomTypeSubTotalAmount = findViewById(R.id.tv_room_type_sub_total)

        guestName = findViewById(R.id.tv_guest_name)
        nationality = findViewById(R.id.tv_nationality)
        phoneNo = findViewById(R.id.tv_phone_number)
        email = findViewById(R.id.tv_email)
        specialRequest = findViewById(R.id.tv_special_request)

        paymentType = findViewById(R.id.tv_payment_type)
        discount = findViewById(R.id.tv_discount)
        taxes = findViewById(R.id.tv_taxes)
        convenienceFee = findViewById(R.id.tv_convenience_fee)
        paymentAmount = findViewById(R.id.tv_payment_amount)
        gatewayType = findViewById(R.id.tv_gateway_type)
        transactionId = findViewById(R.id.tv_transaction_id_label)
        commission = findViewById(R.id.tv_commission)

        loading = findViewById(R.id.pb_loading)
        layout = findViewById(R.id.bookingDetail)

        //Get booking id from booking list click event listener or from firebase
        val bookingId = intent?.getStringExtra(BOOKING_ID)

        if(bookingId != null) {
            Log.d(TAG, "onCreate: Going to request booking info via Id: $bookingId")
            getBookingById(bookingId)
        }

        val rowInfo = findViewById<ConstraintLayout>(R.id.roomTypeRowInfo)
        val table = findViewById<TableLayout>(R.id.tableRate)
        rowInfo.setOnClickListener(){
            if(table.visibility == View.GONE) {
                table.visibility = View.VISIBLE
            } else {
                table.visibility = View.GONE
            }
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.send_confirmation, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId) {
//            R.id.action_send_confirmation -> Toast.makeText(this, "Will send email confirmation", Toast.LENGTH_SHORT).show()
//            else -> {
//
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }

    private fun getBookingById(bookingId : String) {
        val bookingCall = restApi.getBookingById(bookingId)

        bookingCall.enqueue(object: Callback<Booking> {
            override fun onResponse(call: Call<Booking>, response: Response<Booking>) {
                loading.visibility = View.GONE
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
                Log.d(TAG, "onFailure: ${t.printStackTrace()}")
                t.printStackTrace()
            }
        })
    }

    private fun mapDtoToView(booking: Booking) {
        bookingRef.text = booking.reference;
        numGuests.text = booking.numberOfGuests.toString() + if(booking.numberOfGuests > 1) " Guests" else " Guest"

        bookedOn.text = DateFormatUtility.formatFriendlyDateTimeWithYear(booking.bookingDate)
        checkIn.text = DateFormatUtility.formatFriendlyDate(booking.checkInDate)
        checkOut.text =DateFormatUtility.formatFriendlyDate(booking.checkOutDate)
        numNights.text = booking.numberOfNight.toString() + " nights"
        numRooms.text = booking.numberOfRooms.toString() + " rooms"

        maxOccupancy.text = "2 max occupancy"
        ratePlan.text = "Standard Rate"
        extraBedCount.text = "1 extra bed"
        roomTypeSubTotalAmount.text = "MMK 1500000.0"

        guestName.text = booking.customer.name
        nationality.text = booking.customer.nationality
        phoneNo.text = booking.customer.phoneNumber
        email.text = booking.customer.email
        specialRequest.text = booking.customer.specialRequest

        paymentAmount.text = booking.payment.amount.amount.toString()
        gatewayType.text = booking.payment.method
    }
}
package com.innoveller.roomseller.ui.booking

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.innoveller.roomseller.rest.api.RestApi
import com.innoveller.roomseller.rest.api.RestApiBuilder
import com.innoveller.roomseller.rest.dtos.Booking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class BookingListViewModel : ViewModel() {

    private var resultMutableLiveData: MutableLiveData<List<Booking>> = MutableLiveData<List<Booking>>()
    private var searchMutableLiveData: MutableLiveData<List<Booking>> = MutableLiveData<List<Booking>>()
    private val restApi: RestApi = RestApiBuilder.buildRestApi()

    fun getBookings(): LiveData<List<Booking>> {
        return resultMutableLiveData
    }

    fun getSearchedBookings(): LiveData<List<Booking>> {
        return searchMutableLiveData
    }

    fun loadBookingList(loadingBar: ProgressBar, query: String) {
        if(query.isNotBlank()) {
            Log.d(TAG, "loadBookingList: There is search query: $query")
            val bookingCall = restApi.getBookingById("1")
            bookingCall.enqueue(object: Callback<Booking> {
                override fun onResponse(call: Call<Booking>, response: Response<Booking>) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "onResponse: Booking View Model getting booking list")
                        loadingBar.visibility = View.GONE
                        val bookingListBody = response.body()
                        val mockList = Arrays.asList(bookingListBody!!, bookingListBody,  bookingListBody,  bookingListBody,  bookingListBody,  bookingListBody,  bookingListBody,  bookingListBody)
                        if (mockList != null) {
                        resultMutableLiveData.postValue(mockList!!)
//                            searchMutableLiveData.postValue(mockList!!)
                        }
                    }
                }

                override fun onFailure(call: Call<Booking>, t: Throwable) {
                    Log.d(TAG,"onFailure $t")
                    t.printStackTrace()
                }
            })
        } else {
            val bookingListCall = restApi.bookingList
            bookingListCall.enqueue(object : Callback<List<Booking>> {
                override fun onResponse(call: Call<List<Booking>>, response: Response<List<Booking>>) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "onResponse: Booking View Model getting booking list")
                        loadingBar.visibility = View.GONE
                        val bookingListBody = response.body()
                        if (bookingListBody != null) {
                            resultMutableLiveData.postValue(bookingListBody!!)
                        }
                    }
                }

                override fun onFailure(call: Call<List<Booking>?>, t: Throwable) {
                    Log.d(TAG,"onFailure $t")
                    t.printStackTrace()
                }
            })
        }
    }

    companion object {
        private const val TAG = "BookingViewModel"
    }
}
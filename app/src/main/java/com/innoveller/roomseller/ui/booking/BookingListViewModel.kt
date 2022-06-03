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
    private val restApi: RestApi = RestApiBuilder.buildRestApi()
    private var apiCallCount = 0;

    fun getBookings(): LiveData<List<Booking>> {
        return resultMutableLiveData
    }

    fun loadBookingList(loadingBar: ProgressBar, searchCriteria: BookingSearchCriteria?) {
        apiCallCount++
        Log.d(TAG, "loadBookingList: Api call count: $apiCallCount")
        loadingBar.visibility = View.VISIBLE
        val bookingListCall = restApi.bookingList
        bookingListCall.enqueue(object : Callback<List<Booking>> {
            override fun onResponse(call: Call<List<Booking>>, response: Response<List<Booking>>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "onResponse: Booking View Model getting booking list")
                    loadingBar.visibility = View.GONE
                    var bookingListBody = response.body()
                    if (bookingListBody != null) {

                        //this condition will be from server to let us know if there is more data or not to fetch. Currently just make mock data
                        if(apiCallCount >= 6) {

                            // mock search implementation
                            if(searchCriteria != null) {
                                for(booking in bookingListBody!!) {
                                    if (searchCriteria.guestName != null) {
                                        bookingListBody = bookingListBody.filter { booking ->
                                            booking.customer.name.contentEquals(searchCriteria.guestName)
                                        }
                                    }
                                    if (searchCriteria.bookingRef != null) {
                                        bookingListBody = bookingListBody.filter { booking ->
                                            booking.reference.contentEquals(searchCriteria.bookingRef)
                                        }
                                    }
                                    if (searchCriteria.checkInDate != null) {
                                        bookingListBody = bookingListBody.filter { booking ->
                                            booking.checkInDate == searchCriteria.checkInDate
                                        }
                                        if (searchCriteria.bookingDate != null) {
                                            bookingListBody = bookingListBody.filter { booking ->
                                                booking.bookingDate == searchCriteria.bookingDate
                                            }
                                        }
                                    }
                                }

                            }  else {
                                resultMutableLiveData.postValue(listOf<Booking>())
                            }

                        } else {
                            resultMutableLiveData.postValue(bookingListBody!!)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Booking>?>, t: Throwable) {
                Log.d(TAG,"onFailure $t")
                t.printStackTrace()
            }
        })
    }

//    fun loadBookingList(loadingBar: ProgressBar, query: String) {
//        apiCallCount++
//        Log.d(TAG, "loadBookingList: Api call count: $apiCallCount")
//        loadingBar.visibility = View.VISIBLE
//        if(query.isNotBlank()){
//                Log.d(TAG, "loadBookingList: There is search query: $query")
//                val bookingCall = restApi.getBookingById("1")
//                bookingCall.enqueue(object: Callback<Booking> {
//                    override fun onResponse(call: Call<Booking>, response: Response<Booking>) {
//                        loadingBar.visibility = View.GONE
//                        if (response.isSuccessful) {
//                            Log.d(TAG, "onResponse: Booking View Model getting booking list")
//                            val bookingListBody = response.body()
////                        val mockList = Arrays.asList(bookingListBody!!, bookingListBody,  bookingListBody,  bookingListBody,  bookingListBody, bookingListBody,  bookingListBody,  bookingListBody)
//                            val mockList = Arrays.asList(bookingListBody!!, bookingListBody, bookingListBody,  bookingListBody,  bookingListBody,)
//                            if (mockList != null) {
//                                //this condition will be from server to let us know if there is more data or not to fetch. Currently just make mock data
//                                if(apiCallCount >= 6) {
//                                    resultMutableLiveData.postValue(listOf<Booking>())
//                                } else {
//                                    if(!query.contentEquals("si", ignoreCase = true)) {
//                                        resultMutableLiveData.postValue(listOf<Booking>())
//                                    } else {
//                                        resultMutableLiveData.postValue(mockList!!)
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                    override fun onFailure(call: Call<Booking>, t: Throwable) {
//                        Log.d(TAG,"onFailure $t")
//                        t.printStackTrace()
//                    }
//                })
//        } else {
//            val bookingListCall = restApi.bookingList
//            bookingListCall.enqueue(object : Callback<List<Booking>> {
//                override fun onResponse(call: Call<List<Booking>>, response: Response<List<Booking>>) {
//                    if (response.isSuccessful) {
//                        Log.d(TAG, "onResponse: Booking View Model getting booking list")
//                        loadingBar.visibility = View.GONE
//                        val bookingListBody = response.body()
//                        if (bookingListBody != null) {
//                            //this condition will be from server to let us know if there is more data or not to fetch. Currently just make mock data
//                            if(apiCallCount >= 6) {
//                                resultMutableLiveData.postValue(listOf<Booking>())
//                            } else {
//                                resultMutableLiveData.postValue(bookingListBody!!)
//                            }
//                        }
//                    }
//                }
//
//                override fun onFailure(call: Call<List<Booking>?>, t: Throwable) {
//                    Log.d(TAG,"onFailure $t")
//                    t.printStackTrace()
//                }
//            })
//        }
//    }

    companion object {
        private const val TAG = "BookingViewModel"
    }
}
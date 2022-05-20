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

class BookingListViewModel : ViewModel() {
    private val TAG = "BookingViewModel"

    private var resultMutableLiveData: MutableLiveData<List<Booking>> = MutableLiveData<List<Booking>>()
//    private val resultMutableLiveData = MutableLiveData<List<Booking>>()

    var previousResult = listOf<Booking>();
//    val moreProducts = MutableLiveData<List<Booking>>()
    private val restApi: RestApi = RestApiBuilder.buildRestApi()



/* later */

    val list = mutableListOf<Booking>()


    fun getBookings(): LiveData<List<Booking>> {
        return resultMutableLiveData
    }

    fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

    fun loadBookingList(loadingBar: ProgressBar) {
        val bookingListCall = restApi.bookingList
        bookingListCall.enqueue(object : Callback<List<Booking>> {
            override fun onResponse(call: Call<List<Booking>>, response: Response<List<Booking>>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "onResponse: Booking View Model geting booking list")
                    loadingBar.visibility = View.GONE
                    val bookingListBody = response.body()
                    if (bookingListBody != null) {

                        resultMutableLiveData.value = bookingListBody!!
                        Log.d(TAG, "onResponse: result list size: " + resultMutableLiveData.value!!.size)

//                        resultMutableLiveData.value = bookingListBody!!
//                        moreProducts.postValue(bookingListBody!!)
//                        resultMutableLiveData.value = resultMutableLiveData.value.orEmpty() + moreProducts.value.orEmpty()
//
//                        list.addAll(resultMutableLiveData.value!!)
//                        list.addAll(bookingListBody)
//                        Log.d(TAG, "onResponse: Get Booking List:")
//                        resultMutableLiveData.value = list
//
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
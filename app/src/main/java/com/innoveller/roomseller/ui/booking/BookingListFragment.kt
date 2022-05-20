package com.innoveller.roomseller.ui.booking

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.innoveller.roomseller.BookingDetail
import com.innoveller.roomseller.adapter.BookingInfoViewAdapter
import com.innoveller.roomseller.databinding.FragmentBookingListBinding
import com.innoveller.roomseller.rest.dtos.Booking

class BookingListFragment : Fragment() {

    private var _binding: FragmentBookingListBinding? = null

    private val TAG = "BookingFragment"

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val bookingList = mutableListOf < Booking > ()

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View {
        val bookingViewModel = ViewModelProvider(this)[BookingListViewModel::class.java]

        _binding = FragmentBookingListBinding.inflate(inflater, container, false)
        bookingViewModel.getBookings()
        val root: View = binding.root

        val progressBar: ProgressBar = binding.pbLoading
        val recyclerView: RecyclerView = binding.rvBookingList
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager


        var adapter = BookingInfoViewAdapter(bookingList)
        recyclerView.adapter = adapter

        bookingViewModel.loadBookingList(progressBar)
        bookingViewModel.getBookings().observe(viewLifecycleOwner) { result ->
            bookingList.addAll(result)
            adapter.notifyDataSetChanged()
        }

        adapter.setOnClickListener { view, booking ->
            val intentToStartDetailActivity = Intent(view?.context, BookingDetail::class.java)
            intentToStartDetailActivity.putExtra(BookingDetail.BOOKING_ID, booking.id.toString())
            startActivity(intentToStartDetailActivity)
        }

        //Infinite Scroll View
        var loading = true
        var previousTotal = 0;
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(dy > 0) {
                    val visibleItemCount = recyclerView.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems = layoutManager.findFirstCompletelyVisibleItemPosition()

                    if(loading) {
                        if(totalItemCount > previousTotal) {
                            Log.d(TAG, "onScrolled: Successfully loaded booking list")
                            loading = false
                            previousTotal = totalItemCount
                        }
                    }
                    if (!loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            // fetch new data
                            Log.d(TAG, "onScrolled: Going to load more booking list")
                            bookingViewModel.loadBookingList(progressBar)
                            Log.d(TAG, "onScrolled: Setting the loading true")
                            loading = true;
                        }
                    }
                }
            }
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
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
import com.innoveller.roomseller.utilities.EndlessRecyclerViewScrollListener
import com.innoveller.roomseller.utilities.EndlessRecyclerViewScrollListener.LoadMoreListener

class BookingListFragment : Fragment() {

    private var _binding: FragmentBookingListBinding? = null
    private val bookingList = mutableListOf < Booking > ()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View {
        val bookingViewModel = ViewModelProvider(this)[BookingListViewModel::class.java]

        _binding = FragmentBookingListBinding.inflate(inflater, container, false)
        bookingViewModel.getBookings()
        val root: View = binding.root
        val progressBar: ProgressBar = binding.pbLoading
        val recyclerView: RecyclerView = binding.rvBookingList
        val layoutManager = LinearLayoutManager(context)
        var adapter = BookingInfoViewAdapter(bookingList)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        bookingViewModel.loadBookingList(progressBar)
        bookingViewModel.getBookings().observe(viewLifecycleOwner) { result ->
            Log.d(TAG, "onCreateView: observe live data changes and notify the adapter")
            bookingList.addAll(result)
            adapter.notifyDataSetChanged()
        }

        adapter.setOnClickListener { view, booking ->
            val intentToStartDetailActivity = Intent(view?.context, BookingDetail::class.java)
            intentToStartDetailActivity.putExtra(BookingDetail.BOOKING_ID, booking.id.toString())
            startActivity(intentToStartDetailActivity)
        }

        //Endless Scroll View
        recyclerView.addOnScrollListener(EndlessRecyclerViewScrollListener(layoutManager) {
            bookingViewModel.loadBookingList(progressBar)
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "BookingFragment"
    }
}
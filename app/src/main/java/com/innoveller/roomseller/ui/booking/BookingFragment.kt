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

class BookingFragment : Fragment() {

    private var _binding: FragmentBookingListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View {
        val bookingViewModel = ViewModelProvider(this)[BookingViewModel::class.java]

        _binding = FragmentBookingListBinding.inflate(inflater, container, false)
        bookingViewModel.getBookings()
        val root: View = binding.root

        val progressBar: ProgressBar = binding.pbLoading
        val recyclerView: RecyclerView = binding.recyclerview
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        var adapter: BookingInfoViewAdapter

        bookingViewModel.loadBookingList(progressBar)
        bookingViewModel.getBookings().observe(viewLifecycleOwner) { result ->
//            Should I put here or at view model
//            progressBar.visibility = View.GONE
            adapter = BookingInfoViewAdapter(result)
            recyclerView.adapter = adapter

            adapter.setOnClickListener { view, booking ->
                val intentToStartDetailActivity = Intent(view?.context, BookingDetail::class.java)
                intentToStartDetailActivity.putExtra(BookingDetail.BOOKING_ID, booking.id.toString())
                startActivity(intentToStartDetailActivity)
            }
        }

        //Infinite Scroll View
        var loading = true
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(dy > 0) {
                    val visibleItemCount = recyclerView.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems = layoutManager.findFirstCompletelyVisibleItemPosition()

                    if (loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            // fetch new data
                            bookingViewModel.loadBookingList(progressBar)

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
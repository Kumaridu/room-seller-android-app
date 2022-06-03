package com.innoveller.roomseller.ui.booking

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.innoveller.roomseller.BookingDetail
import com.innoveller.roomseller.R
import com.innoveller.roomseller.SearchActivity
import com.innoveller.roomseller.adapter.BookingInfoViewAdapter
import com.innoveller.roomseller.databinding.FragmentBookingListBinding
import com.innoveller.roomseller.rest.dtos.Booking
import com.innoveller.roomseller.utilities.EndlessRecyclerViewScrollListener
import java.util.*


class BookingListFragment : Fragment() {

    private var _binding: FragmentBookingListBinding? = null
    private var bookingList = mutableListOf < Booking > ()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View {
        val bookingViewModel = ViewModelProvider(this)[BookingListViewModel::class.java]

        _binding = FragmentBookingListBinding.inflate(inflater, container, false)
        bookingViewModel.getBookings()
        val progressBar: ProgressBar = binding.pbLoading
        val recyclerView: RecyclerView = binding.rvBookingList
        val root = binding.root
        val layoutManager = LinearLayoutManager(context)
        var adapter = BookingInfoViewAdapter(bookingList)
        var pbBottomProgressBar = binding.pbBottomLoading

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        bookingViewModel.loadBookingList(progressBar, null)
        bookingViewModel.getBookings().observe(viewLifecycleOwner) { result ->
            //If there is no search result
//            if(isSearch) {
//                if(result.isEmpty()) {
//                    Toast.makeText(context,"No Match Found", Toast.LENGTH_LONG).show()
//                }
//                isSearch = false
//            }
            //If it is calling the api after resetting the api load, then clear the booking list and reset the firstTimeLoad to false
//            if(isFirstTimeLoad) {
//                EndlessRecyclerViewScrollListener.previousItemTotalCount = 0;
//                bookingList.clear()
//                isFirstTimeLoad = false
//            }

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
            bookingViewModel.loadBookingList(pbBottomProgressBar, null)
        })

        // Search By Date
        var calendarChosenDate  = Date()
        var searchDateType  = "Booking Date By"
//        searchFieldDate.text = searchDateType + " " + DateFormatUtility.formatFriendlyDate(calendarChosenDate);
//
//        searchByDateLayout.setOnClickListener {
//            val inflater = LayoutInflater.from(context)
//            val dialogLayoutView = inflater.inflate(R.layout.dialog_calendar_booking_checkin_date, null)
//            Log.d(TAG, "onCreateView: This is search date type: $searchDateType")
//            CalendarAndSortingFieldHelper.showCalendarWithBookingAndCheckInDateDialog(
//                context, dialogLayoutView, searchDateType, calendarChosenDate) { selectedDate, dateType ->
//                calendarChosenDate = selectedDate;
//                searchDateType = dateType
//                searchFieldDate.text = dateType + " " + DateFormatUtility.formatFriendlyDate(calendarChosenDate);
//
//                EndlessRecyclerViewScrollListener.previousItemTotalCount = 0
//                isFirstTimeLoad = true
//                //Currently mock the search query
//                searchQuery = "si";
//                bookingViewModel.loadBookingList(progressBar, searchQuery);
//                Log.d(TAG,"onDateFieldSearchCriteria: will send to the api")
//            }
//        }
        setHasOptionsMenu(true);
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId === R.id.action_search) {
            val intent = Intent(context, SearchActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val TAG = "BookingFragment"
    }
}

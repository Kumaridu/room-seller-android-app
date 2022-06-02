package com.innoveller.roomseller.ui.booking

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.innoveller.roomseller.BookingDetail
import com.innoveller.roomseller.MainActivity
import com.innoveller.roomseller.R
import com.innoveller.roomseller.SearchActivity
import com.innoveller.roomseller.adapter.BookingInfoViewAdapter
import com.innoveller.roomseller.databinding.FragmentBookingListBinding
import com.innoveller.roomseller.helpers.CalendarAndSortingFieldHelper
import com.innoveller.roomseller.rest.dtos.Booking
import com.innoveller.roomseller.utilities.DateFormatUtility
import com.innoveller.roomseller.utilities.EndlessRecyclerViewScrollListener
import java.util.*


class BookingListFragment : Fragment() {

    private var _binding: FragmentBookingListBinding? = null
    private var bookingList = mutableListOf < Booking > ()
    private var searchBookingList = mutableListOf<Booking>()

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
        val searchByDateLayout = binding.searchByDate
        val searchView = binding.tvSearchFilter
        val layoutManager = LinearLayoutManager(context)
        var adapter = BookingInfoViewAdapter(bookingList)
        var isFirstTimeLoad = true
        var searchQuery = ""
        var isSearch = false
        var pbBottomProgressBar = binding.pbBottomLoading
        var searchFieldDate = binding.tvSearchFieldDate

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        bookingViewModel.loadBookingList(progressBar, searchQuery)
        bookingViewModel.getBookings().observe(viewLifecycleOwner) { result ->
            //If there is no search result
            if(isSearch) {
                if(result.isEmpty()) {
                    Toast.makeText(context,"No Match Found", Toast.LENGTH_LONG).show()
                }
                isSearch = false
            }
            //If it is calling the api after resetting the api load, then clear the booking list and reset the firstTimeLoad to false
            if(isFirstTimeLoad) {
                EndlessRecyclerViewScrollListener.previousItemTotalCount = 0;
                bookingList.clear()
                isFirstTimeLoad = false
            }

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
            bookingViewModel.loadBookingList(pbBottomProgressBar, searchQuery)
        })

        val closeBtnId = searchView.context.resources
            .getIdentifier("android:id/search_close_btn", null, null)
        val searchEditTextId = searchView.context.resources
            .getIdentifier("android:id/search_src_text", null, null)
        val closeBtn = searchView.findViewById<ImageView>(closeBtnId)
        val searchEditText = searchView.findViewById<EditText>(searchEditTextId)

//        closeBtn?.setOnClickListener {
//            Log.d(TAG, "onCreateView: I was clicked close ")
//            searchView.setQuery("", false)
//            searchEditText.clearFocus()
//            searchQuery = ""
//            isFirstTimeLoad = true
//            bookingViewModel.loadBookingList(progressBar, searchQuery)
//        }
//
//        // Search By Booking reference or guest name
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                searchQuery = query!!
//                isSearch = true
//                //Resetting the previous Item total count to 0
//                EndlessRecyclerViewScrollListener.previousItemTotalCount = 0
//                isFirstTimeLoad = true
//
//                bookingViewModel.loadBookingList(progressBar, query!!)
//                Log.d(TAG, "onQueryTextSubmit: Search List: ${bookingList.size}")
//                return false;
//            }
//
//            override fun onQueryTextChange(p0: String?): Boolean {
//                Log.d(TAG, "onQueryTextChange: I changed: $p0")
//                return false
//            }
//        })

        // Search By Date
        var calendarChosenDate  = Date()
        var searchDateType  = "Booking Date By"
//        searchFieldDate.text = searchDateType + " " + DateFormatUtility.formatFriendlyDate(calendarChosenDate);

        searchByDateLayout.setOnClickListener {
            val inflater = LayoutInflater.from(context)
            val dialogLayoutView = inflater.inflate(R.layout.dialog_calendar_booking_checkin_date, null)
            Log.d(TAG, "onCreateView: This is search date type: $searchDateType")
            CalendarAndSortingFieldHelper.showCalendarWithBookingAndCheckInDateDialog(
                context, dialogLayoutView, searchDateType, calendarChosenDate) { selectedDate, dateType ->
                calendarChosenDate = selectedDate;
                searchDateType = dateType
                searchFieldDate.text = dateType + " " + DateFormatUtility.formatFriendlyDate(calendarChosenDate);

                EndlessRecyclerViewScrollListener.previousItemTotalCount = 0
                isFirstTimeLoad = true
                //Currently mock the search query
                searchQuery = "si";
                bookingViewModel.loadBookingList(progressBar, searchQuery);
                Log.d(TAG,"onDateFieldSearchCriteria: will send to the api")
            }
        }
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

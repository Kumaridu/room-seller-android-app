package com.innoveller.roomseller.ui.booking

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.innoveller.roomseller.BookingDetail
import com.innoveller.roomseller.R
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

        closeBtn?.setOnClickListener {
            Log.d(TAG, "onCreateView: I was clicked close ")
            searchView.setQuery("", false)
            searchEditText.clearFocus()
            searchQuery = ""
            isFirstTimeLoad = true
            bookingViewModel.loadBookingList(progressBar, searchQuery)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchQuery = query!!
                isSearch = true
                //Resetting the previous Item total count to 0
                EndlessRecyclerViewScrollListener.previousItemTotalCount = 0
                isFirstTimeLoad = true

                bookingViewModel.loadBookingList(progressBar, query!!)
                Log.d(TAG, "onQueryTextSubmit: Search List: ${bookingList.size}")
                return false;
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                Log.d(TAG, "onQueryTextChange: I changed: $p0")
                return false
            }
        })

        var selectedDate  = Date()
        var dateType  = "Booking Date By"
        searchFieldDate.text = dateType + " " + DateFormatUtility.formatFriendlyDate(selectedDate);

        searchByDateLayout.setOnClickListener {
            val inflater = LayoutInflater.from(context)
            val dialogLayoutView = inflater.inflate(R.layout.dialog_calendar_booking_checkin_date, null)
            CalendarAndSortingFieldHelper.showCalendarWithBookingAndCheckInDateDialog(
                context, dialogLayoutView, dateType, selectedDate) { selectedDate1, dateType1 ->
                selectedDate = selectedDate1;
                dateType = dateType1
                searchFieldDate.text = dateType + " " + DateFormatUtility.formatFriendlyDate(selectedDate);
                Log.d(TAG,"onDateFieldSearchCriteria: will send to the api")
            }
        }

//        var currentSortedType = "Booking Date By "
//        Log.d(TAG, "onCreateView: This is current Sort TYpe: $currentSortedType")
//        var calendarView : CalendarView
//
////        var formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
//        var selectedSearchDate  = Date()
//        searchFieldDate.text = currentSortedType + DateFormatUtility.formatFriendlyDate(selectedSearchDate)
//        var dialogLayoutView: View
//
//        // Date Dialog box
//            searchByDateLayout.setOnClickListener {
//            val inflater = LayoutInflater.from(context)
//            dialogLayoutView = inflater.inflate(R.layout.dialog_calendar_booking_checkin_date, null)
//            val dateToggleGroup = dialogLayoutView.findViewById<MaterialButtonToggleGroup>(R.id.btn_toggle_group)
//            calendarView = dialogLayoutView.findViewById(R.id.clv)
//
//            if(currentSortedType.equals("Booking Date By ", ignoreCase = true)) {
//                Log.d(TAG, "onCreateView: current Sorted Type: $currentSortedType")
//                dateToggleGroup.check(R.id.btn_search_by_booking_date)
////                dateToggleGroup.check(R.id.btn_search_by_check_in_date)
//            } else {
//                Log.d(TAG, "onCreateView: current Sorted Type: $currentSortedType")
//                dateToggleGroup.check(R.id.btn_search_by_check_in_date)
//            }
//
//            dateToggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
//                val materialButton: MaterialButton = dialogLayoutView.findViewById(checkedId)
//                if (isChecked) {
//                    val selectedDateValue = materialButton.text.toString()
//                    Log.d(TAG, "onCreateView: selected Date value: $selectedDateValue")
//                    currentSortedType =
//                        if (selectedDateValue.equals("Booking Date", ignoreCase = true)) {
//                            "Booking Date By "
//                        } else {
//                            "Check In Date By "
//                        }
//                    Log.d(TAG, "onCreateView: onchange current sort type: $currentSortedType")
//                }
//            }
//
//
//
//            calendarView.date = selectedSearchDate.getTime()
//            calendarView.setOnDateChangeListener(object: CalendarView.OnDateChangeListener {
//                override fun onSelectedDayChange(p0: CalendarView, year: Int, month: Int, day: Int) {
//                    var year: String = java.lang.String.valueOf(year) // year
//                    var month: String = (java.lang.String.valueOf(month + 1)) //month
//
//                    if(month.length == 1) {
//                        month = "0$month"
//                    }
//                    var day: String = java.lang.String.valueOf(day) //day
//                    if(day.length == 1) {
//                        day = "0$day"
//                    }
//
//                    var localDate = LocalDate.parse("$year-$month-$day")
//                    selectedSearchDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
//                    calendarView.setDate(selectedSearchDate.time)
//
//                    Log.d(TAG, "onSelectedDayChange: selected Search Date: $selectedSearchDate")
//                }
//            })
//
//            MaterialAlertDialogBuilder(inflater.context)
//                .setView(dialogLayoutView)
//                .setPositiveButton("SELECT") { dialog, which->
//                    // have to call the api
//                    searchFieldDate.text = currentSortedType + DateFormatUtility.formatFriendlyDate(selectedSearchDate)
//                    dialog.dismiss()
//                }
//                .setNegativeButton("CANCEL") {dialog, which->
//                    dialog.dismiss()
//                }
//                .show()
//        }
        
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

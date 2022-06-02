package com.innoveller.roomseller.helpers;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.innoveller.roomseller.R;
import com.innoveller.roomseller.utilities.DateFormatUtility;

import java.text.ParseException;
import java.util.Date;

public class CalendarAndSortingFieldHelper {
    private static String currentSortDateBy;

    public interface OnDateFieldSearchSelectionListener {
        void onDateFieldSearchCriteria(Date selectedDate, String dateType);
    }

    public static void showCalendarWithBookingAndCheckInDateDialog(Context context, View dialogView,
                                                                   String dateType, Date previousChosenDate,
                                                                   OnDateFieldSearchSelectionListener listener) {

        currentSortDateBy = dateType;
        final Date[] currentChosenDate = new Date[1];
        MaterialButtonToggleGroup dateToggleGroup = dialogView.findViewById(R.id.btn_toggle_group);
        CalendarView calendarView = dialogView.findViewById(R.id.clv);


        if(currentSortDateBy.equalsIgnoreCase("Booking Date By")) {
            dateToggleGroup.check(R.id.btn_search_by_booking_date);
        } else {
            dateToggleGroup.check(R.id.btn_search_by_check_in_date);
        }

        dateToggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            MaterialButton materialButton = dialogView.findViewById(checkedId);
            if(isChecked) {
                String selectedDateType = materialButton.getText().toString();
                if(selectedDateType.equalsIgnoreCase("Booking Date")) {
                    currentSortDateBy =  "Booking Date By";
                } else {
                    currentSortDateBy = "Check In Date By";
                }
            }
        });

        calendarView.setDate(previousChosenDate.getTime());
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String chosenDateString = year + "-" + (month+1) + "-" + dayOfMonth;
            try {
                currentChosenDate[0] = DateFormatUtility.parseISODate(chosenDateString);
                calendarView.setDate(currentChosenDate[0].getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        // showing the dialog
        new MaterialAlertDialogBuilder(context)
                .setView(dialogView)
                .setPositiveButton("SELECT", (dialog, i) -> {
                    listener.onDateFieldSearchCriteria(currentChosenDate[0], currentSortDateBy);
                })
                .setNegativeButton("CANCEL", (dialog, i) -> {
                    dialog.cancel();
                }).show();
    }
}

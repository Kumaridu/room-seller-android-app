package com.innoveller.roomseller;

import android.view.View;

import com.innoveller.roomseller.rest.dtos.BookingDto;

public interface BookingListRowOnClickListener {
     void onClick(View view, BookingDto booking);
}

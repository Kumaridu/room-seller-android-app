package com.innoveller.roomseller.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.innoveller.roomseller.R;
import com.innoveller.roomseller.rest.dtos.Booking;
import com.innoveller.roomseller.utilities.DateFormatUtility;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class BookingInfoViewAdapter extends RecyclerView.Adapter<BookingInfoViewAdapter.MyViewHolder> {

    private List<Booking> bookingList;
    private OnBookingClickListener onBookingClickListener;

    public BookingInfoViewAdapter(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    public interface OnBookingClickListener {
        void onClick(View view, Booking booking);
    }

    public void setOnClickListener(OnBookingClickListener clickListener) {
        this.onBookingClickListener = clickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking_info,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Booking booking = bookingList.get(position);
        String night = booking.numberOfNight > 1 ? "nights" : "night";
        String guest = booking.numberOfGuests > 1 ? "guests" : "guest";
        String room = booking.numberOfRooms > 1 ? "rooms" : "room";

        holder.guestName.setText(booking.customer.name + " | " + booking.numberOfGuests + " " + guest + " | " + booking.numberOfRooms + " " + room);
        holder.numNights.setText(booking.numberOfNight + " " + night);

        // will modify later with real time, this is for test
//        holder.bookedOn.setText("April 21 2022, 01:19pm");

        try {
            holder.bookedOn.setText(DateFormatUtility.formatFriendlyDateTimeWithYear(booking.bookingDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.checkInDate.setText(DateFormatUtility.formatFriendlyDate(booking.checkInDate));
        holder.checkOutDate.setText(DateFormatUtility.formatFriendlyDate(booking.checkOutDate));
        holder.bookingRef.setText(booking.reference);

        holder.bookingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBookingClickListener.onClick(v, booking);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView guestName, numNights, bookingRef, bookedOn, checkInDate, checkOutDate;
        private MaterialCardView bookingLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            guestName = itemView.findViewById(R.id.tv_row_guest_name_num_guest_num_rooms);
            numNights = itemView.findViewById(R.id.tv_row_nights);
            bookingRef = itemView.findViewById(R.id.tv_row_booking_ref);
            bookedOn = itemView.findViewById(R.id.tv_row_booked_on);
            checkInDate = itemView.findViewById(R.id.tv_checkIn);
            checkOutDate = itemView.findViewById(R.id.tv_row_checkout);
            bookingLayout = itemView.findViewById(R.id.bookingRowLayout2);
        }
    }
}

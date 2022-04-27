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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking_info2,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Booking booking = bookingList.get(position);
        String night = booking.numberOfNight > 1 ? "nights" : "night";
        String guest = booking.numberOfGuests > 1 ? "guests" : "guest";
        String room = booking.numberOfRooms > 1 ? "rooms" : "room";
        String bookSum = new StringBuilder().append(booking.numberOfNight).append(" ").append(night).append(" - ")
                .append(booking.numberOfRooms).append(" ").append(room).toString();

        holder.guestName.setText(booking.customer.name + " | " + booking.numberOfGuests + " " + guest);
        holder.bookSummary.setText(bookSum);
        holder.checkInDate.setText(booking.checkInDate);
        holder.checkOutDate.setText(booking.checkOutDate);
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
        private TextView guestName, bookSummary, bookingRef, checkInDate, checkOutDate;
        private MaterialCardView bookingLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            guestName = itemView.findViewById(R.id.tv_test_guest_name_num_guest);
            bookSummary = itemView.findViewById(R.id.tv_test_nights_and_rooms);
            bookingRef = itemView.findViewById(R.id.tv_test_booking_ref);
            checkInDate = itemView.findViewById(R.id.tv_test_checkIn);
            checkOutDate = itemView.findViewById(R.id.tv_test_checkout);
            bookingLayout = itemView.findViewById(R.id.bookingRowLayout2);

//            guestName = itemView.findViewById(R.id.tv_test_guest_name_num_guest);
//            bookSummary = itemView.findViewById(R.id.tv_detail_num_night);
//            bookingRef = itemView.findViewById(R.id.tv_row_booking_ref);
//            checkInDate = itemView.findViewById(R.id.tv_row_checkin);
//            checkOutDate = itemView.findViewById(R.id.tv_row_checkout);
//            bookingLayout = itemView.findViewById(R.id.bookingRowLayout);
        }
    }
}

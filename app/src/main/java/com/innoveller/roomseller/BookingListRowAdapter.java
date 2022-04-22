package com.innoveller.roomseller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookingListRowAdapter extends RecyclerView.Adapter<BookingListRowAdapter.MyViewHolder> {

    private List<Booking> bookingList;
    private BookingListRowOnClickListener clickListener;

    public BookingListRowAdapter(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_row,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Booking booking = bookingList.get(position);
        holder.guestName.setText(booking.getGuestName());
        holder.bookSummary.setText(booking.getNightGuestRoomInfo());
        holder.checkInDate.setText(booking.getCheckInDate());
        holder.checkOutDate.setText(booking.getCheckoutDate());
        holder.bookingRef.setText(booking.getBookingRef());


        holder.bookingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(v, booking);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public void setOnClickListener(BookingListRowOnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView guestName, bookSummary, bookingRef, checkInDate, checkOutDate;
        private ConstraintLayout bookingLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            guestName = itemView.findViewById(R.id.tv_detail_guest_name);
            bookSummary = itemView.findViewById(R.id.tv_detail_num_night);
            bookingRef = itemView.findViewById(R.id.tv_booking_ref);
            checkInDate = itemView.findViewById(R.id.tv_checkin);
            checkOutDate = itemView.findViewById(R.id.tv_checkout);
            bookingLayout = itemView.findViewById(R.id.bookingRowLayout);
        }
    }
}

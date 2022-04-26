package com.innoveller.roomseller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.innoveller.roomseller.rest.dtos.BookingDto;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BookingListRowAdapter extends RecyclerView.Adapter<BookingListRowAdapter.MyViewHolder> {

    private List<BookingDto> bookingList;
    private BookingListRowOnClickListener clickListener;

    public BookingListRowAdapter(List<BookingDto> bookingList) {
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
        final BookingDto booking = bookingList.get(position);
        holder.guestName.setText(booking.customer.name);
        holder.bookSummary.setText(booking.numberOfGuests+ "-" + booking.numberOfRooms);
        holder.checkInDate.setText(booking.checkInDate);
        holder.checkOutDate.setText(booking.checkOutDate);
        holder.bookingRef.setText(booking.reference);


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
            guestName = itemView.findViewById(R.id.tv_row_guest_name);
            bookSummary = itemView.findViewById(R.id.tv_detail_num_night);
            bookingRef = itemView.findViewById(R.id.tv_row_booking_ref);
            checkInDate = itemView.findViewById(R.id.tv_row_checkin);
            checkOutDate = itemView.findViewById(R.id.tv_row_checkout);
            bookingLayout = itemView.findViewById(R.id.bookingRowLayout);
        }
    }
}

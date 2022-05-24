package com.innoveller.roomseller.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.innoveller.roomseller.R;
import com.innoveller.roomseller.databinding.ItemBookingInfoBinding;
import com.innoveller.roomseller.rest.dtos.Booking;
import com.innoveller.roomseller.utilities.DateFormatUtility;

import java.text.ParseException;
import java.util.List;

public class BookingInfoViewAdapter extends RecyclerView.Adapter<BookingInfoViewAdapter.BookingItemViewHolder> {

    private List<Booking> bookingList;
    private BookingInfoViewAdapter.OnBookingClickListener onBookingClickListener;

    public BookingInfoViewAdapter(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    public interface OnBookingClickListener {
        void onClick(View view, Booking booking);
    }

    public void setOnClickListener(BookingInfoViewAdapter.OnBookingClickListener clickListener) {
        this.onBookingClickListener = clickListener;
    }

    @NonNull
    @Override
    public BookingItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking_info,parent,false);
        return new BookingItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingItemViewHolder holder, int position) {
        BookingInfoViewAdapter.BookingItemViewHolder itemHolder = (BookingInfoViewAdapter.BookingItemViewHolder) holder;
        final Booking booking = bookingList.get(position);
        String night = booking.numberOfNight > 1 ? "nights" : "night";
        String guest = booking.numberOfGuests > 1 ? "guests" : "guest";
        String room = booking.numberOfRooms > 1 ? "rooms" : "room";

        itemHolder.guestName.setText(booking.customer.name + " | " + booking.numberOfGuests + " " + guest + " | " + booking.numberOfRooms + " " + room);
        itemHolder.numNights.setText(booking.numberOfNight + " " + night);

        try {
            itemHolder.bookedOn.setText(DateFormatUtility.formatFriendlyDateTimeWithYear(booking.bookingDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        itemHolder.checkInDate.setText(DateFormatUtility.formatFriendlyDate(booking.checkInDate));
        itemHolder.checkOutDate.setText(DateFormatUtility.formatFriendlyDate(booking.checkOutDate));
        itemHolder.bookingRef.setText(booking.reference);

        itemHolder.bookingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBookingClickListener.onClick(v, booking);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookingList == null ? 0 : bookingList.size();
    }

    class BookingItemViewHolder extends RecyclerView.ViewHolder {
        private TextView guestName, numNights, bookingRef, bookedOn, checkInDate, checkOutDate;
        private MaterialCardView bookingLayout;

        public BookingItemViewHolder(View itemView) {
            super(itemView);
            ItemBookingInfoBinding binding = ItemBookingInfoBinding.bind(itemView);
            guestName = binding.tvRowGuestNameNumGuestNumRooms;
            numNights = binding.tvRowNights;
            bookingRef = binding.tvRowBookingRef;
            bookedOn = binding.tvRowBookedOn;
            checkInDate = binding.tvCheckIn;
            checkOutDate = binding.tvRowCheckout;
            bookingLayout = binding.bookingRowLayout2;
        }
    }
}

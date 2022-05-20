package com.innoveller.roomseller.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.innoveller.roomseller.R;
import com.innoveller.roomseller.databinding.ItemBookingInfoBinding;
import com.innoveller.roomseller.databinding.ItemBookingListLoadingBinding;
import com.innoveller.roomseller.rest.dtos.Booking;
import com.innoveller.roomseller.utilities.DateFormatUtility;

import java.text.ParseException;
import java.util.List;

public class BookingInfoViewAdapter extends RecyclerView.Adapter< RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking_info,parent,false);
            return new BookingItemViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking_list_loading,parent,false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof BookingItemViewHolder) {
            BookingItemViewHolder itemHolder = (BookingItemViewHolder) holder;
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
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return bookingList == null ? 0 : bookingList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == bookingList.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
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

     class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public LoadingViewHolder(View itemView) {
            super(itemView);
            ItemBookingListLoadingBinding binding = ItemBookingListLoadingBinding.bind(itemView);
            progressBar = binding.pbLoadMore;
        }
    }
}

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
import com.innoveller.roomseller.rest.dtos.Booking;
import com.innoveller.roomseller.utilities.DateFormatUtility;

import java.text.ParseException;
import java.util.List;
import com.innoveller.roomseller.databinding.ItemBookingInfoBinding;

public class BookingInfoViewAdapter4 extends RecyclerView.Adapter<BookingInfoViewAdapter4.MyViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<Booking> bookingList;
    private OnBookingClickListener onBookingClickListener;

    public BookingInfoViewAdapter4(List<Booking> bookingList) {
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
        if(viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking_info,parent,false);
            return new MyViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking_list_loading,parent,false);
            return  new MyViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Booking booking = bookingList.get(position);
        String night = booking.numberOfNight > 1 ? "nights" : "night";
        String guest = booking.numberOfGuests > 1 ? "guests" : "guest";
        String room = booking.numberOfRooms > 1 ? "rooms" : "room";

        holder.guestName.setText(booking.customer.name + " | " + booking.numberOfGuests + " " + guest + " | " + booking.numberOfRooms + " " + room);
        holder.numNights.setText(booking.numberOfNight + " " + night);


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
//        if(position == bookingList.size() - 1) {
//            holder.loading.setVisibility(View.VISIBLE);
//        } else {
//            holder.loading.setVisibility(View.GONE);
//        }
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return bookingList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void updateAdapter(List<Booking> mDataList) {
        this.bookingList = mDataList;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView guestName, numNights, bookingRef, bookedOn, checkInDate, checkOutDate;
//        private ProgressBar loading;
        private MaterialCardView bookingLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            ItemBookingInfoBinding binding = ItemBookingInfoBinding.bind(itemView);
            guestName = binding.tvRowGuestNameNumGuestNumRooms;
            numNights = binding.tvRowNights;
            bookingRef = binding.tvRowBookingRef;
            bookedOn = binding.tvRowBookedOn;
            checkInDate = binding.tvCheckIn;
            checkOutDate = binding.tvRowCheckout;
            bookingLayout = binding.bookingRowLayout2;
//            loading = binding.pbRowLoading;
        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.pb_load_more);
        }
    }
}

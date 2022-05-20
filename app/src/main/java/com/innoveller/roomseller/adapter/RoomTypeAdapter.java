package com.innoveller.roomseller.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.innoveller.roomseller.R;
import com.innoveller.roomseller.databinding.ItemRoomTypeBinding;
import com.innoveller.roomseller.rest.dtos.RoomType;

import java.util.List;

public class RoomTypeAdapter extends RecyclerView.Adapter<RoomTypeAdapter.RoomTypeViewHolder> {
    private List<RoomType> roomTypeList;
    private OnRoomTypeClickListener clickListener;

    public RoomTypeAdapter(List<RoomType> roomTypeList) {
        this.roomTypeList = roomTypeList;
    }

    public void setOnClickListener(OnRoomTypeClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RoomTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_type,parent,false);
        return new RoomTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomTypeViewHolder holder, int position) {
        final RoomType roomType = roomTypeList.get(position);

        holder.roomType.setText(roomType.roomType);
        holder.maxOccupancy.setText(roomType.maxOccupancy);
        holder.subTotal.setText(roomType.subTotal);
        holder.ratePlan.setText(roomType.ratePlan);

        holder.roomTypeRowInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClick(view, roomType);
            }
        });
    }

    @Override
    public int getItemCount() {
        return roomTypeList.size();
    }

    public interface OnRoomTypeClickListener {
        void onClick(View view, RoomType roomType);
    }

    class RoomTypeViewHolder extends RecyclerView.ViewHolder {
        private TextView roomType, maxOccupancy, subTotal, ratePlan;
        private ConstraintLayout roomTypeRowInfo;

        public RoomTypeViewHolder(View itemView) {
            super(itemView);
            ItemRoomTypeBinding binding = ItemRoomTypeBinding.bind(itemView);
            roomType = binding.tvRoomType;
            maxOccupancy = binding.tvMaxOccupancy;
            subTotal = binding.tvRoomTypeSubTotal;
            ratePlan = binding.tvRatePlan;
            roomTypeRowInfo = binding.roomTypeRowInfo;
        }
    }

}

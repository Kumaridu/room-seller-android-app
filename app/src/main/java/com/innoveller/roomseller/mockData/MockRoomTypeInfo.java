package com.innoveller.roomseller.mockData;

import com.innoveller.roomseller.R;
import com.innoveller.roomseller.rest.dtos.RoomType;

import java.util.ArrayList;
import java.util.List;

public class MockRoomTypeInfo {

    public static List<RoomType> getRoomTypeList() {
        List<RoomType> list = new ArrayList<>();
        RoomType room1 = new RoomType("Deluxe Room", "2 max Occupancy", "MMK 200000.0", "Standard Rate", "1");
        RoomType room2 = new RoomType("Standard Sgl/Dbl", "2 max Occupancy", "MMK 150000.0", "Standard Rate", "1");

        list.add(room1);
        list.add(room2);
        return list;
    }
}

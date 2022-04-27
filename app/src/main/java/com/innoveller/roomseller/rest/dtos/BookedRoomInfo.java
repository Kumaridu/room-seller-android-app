package com.innoveller.roomseller.rest.dtos;

import java.io.Serializable;

public class BookedRoomInfo implements Serializable {
    public String roomTypeName;
    public int numberOfAdult;
    public int numberOfChild;
    public int numberOfExtraBed;
    public Money amount;
}

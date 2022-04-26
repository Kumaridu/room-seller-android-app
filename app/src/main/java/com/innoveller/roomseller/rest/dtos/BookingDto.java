package com.innoveller.roomseller.rest.dtos;

import java.io.Serializable;
import java.util.List;

public class BookingDto implements Serializable {
    public int id;
    public String reference;
    public CustomerDto customer;
    public HotelInfo hotelInfo;
    public String bookingDate;
    public String checkInDate;
    public String checkOutDate;
    public int numberOfNights;
    public int numberOfRooms;
    public int numberOfGuests;
    public Money totalAmount;
    public Payment payment;
    public List<BookedRoomInfo> bookedRooms;

    @Override
    public String toString() {
        return "BookingDto{" +
                "id=" + id +
                ", reference='" + reference + '\'' +
                ", customer=" + customer +
                ", hotelInfo=" + hotelInfo +
                ", bookingDate='" + bookingDate + '\'' +
                ", checkInDate='" + checkInDate + '\'' +
                ", checkOutDate='" + checkOutDate + '\'' +
                ", numberOfNights=" + numberOfNights +
                ", numberOfRooms=" + numberOfRooms +
                ", numberOfGuests=" + numberOfGuests +
                ", totalAmount=" + totalAmount +
                ", payment=" + payment +
                ", bookedRooms=" + bookedRooms +
                '}';
    }
}

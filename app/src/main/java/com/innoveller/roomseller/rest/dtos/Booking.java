package com.innoveller.roomseller.rest.dtos;

import java.util.List;

public class Booking {
    public int id;
    public String reference;
    public Customer customer;
    public HotelInfo hotelInfo;
    public String bookingDate;
    public String checkInDate;
    public String checkOutDate;
    public int numberOfNight;
    public int numberOfRooms;
    public int numberOfGuests;
    public Money totalAmount;
    public Payment payment;
    public List<BookedRoomInfo> bookedRooms;
}

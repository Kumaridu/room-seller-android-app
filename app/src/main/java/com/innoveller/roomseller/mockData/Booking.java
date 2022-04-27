package com.innoveller.roomseller.mockData;


import java.io.Serializable;

public class Booking implements Serializable {

    private String guestName;
    private int numNights;
    private int numGuests;
    private int numRooms;
    private String checkInDate;
    private String checkoutDate;
    private String bookingRef;

    public Booking(String guestName, int numNights, int numGuests, int numRooms, String checkInDate,
                   String checkoutDate, String bookingRef) {
        this.guestName = guestName;
        this.numNights = numNights;
        this.numGuests = numGuests;
        this.numRooms = numRooms;
        this.checkInDate = checkInDate;
        this.checkoutDate = checkoutDate;
        this.bookingRef = bookingRef;
    }

    public String getGuestName() {
        return guestName;
    }

    public int getNumNights() {
        return numNights;
    }

    public int getNumGuests() {
        return numGuests;
    }

    public int getNumRooms() {
        return numRooms;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public String getBookingRef() {
        return bookingRef;
    }

    public String getNightGuestRoomInfo() {
        String night = this.numNights > 1 ? "nights" : "night";
        String guest = this.numGuests > 1 ? "guests" : "guest";
        String room = this.numRooms > 1 ? "rooms" : "room";
        return this.numNights + " " + night + " - " + this.numGuests + " " + guest + " - " + this.numRooms + " " + room;
    }
}

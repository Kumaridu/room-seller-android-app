package com.innoveller.roomseller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockDataUtils {

    public static List<Booking> getBookingList() {
        List<Booking> bookings = new ArrayList<>();
        Booking booking1 = new Booking("Su Su Aye",2,2,1,"Dec 19", "Dec 21","109-20j");
        Booking booking2 = new Booking("Zin Zin",1,2,1,"Nov 1", "Nov 2","101-40e");
        Booking booking3 = new Booking("Aye Aye",2,3,1,"Aug 19", "Aug 21","10s-22d");
        Booking booking4 = new Booking("Ma Ma",1,2,1,"Dec 20", "Dec 21","20r-10k");
        Booking booking5 = new Booking("Khin Myat Aye",2,2,1,"Dec 19", "Dec 21","20d-203");
        Booking booking6 = new Booking("Khaing Zin",2,2,1,"Nov 19", "Nov 21","12s-2dd");
        Booking booking7 = new Booking("Su Su Aye",2,2,1,"Dec 19", "Dec 21","109-20j");

        Booking booking8 = new Booking("Su Su Aye",2,2,1,"Dec 19", "Dec 21","109-20j");
        Booking booking9 = new Booking("Zin Zin",1,2,1,"Nov 1", "Nov 2","101-40e");
        Booking booking10 = new Booking("Aye Aye",2,3,1,"Aug 19", "Aug 21","10s-22d");
        Booking booking11 = new Booking("Ma Ma",1,2,1,"Dec 20", "Dec 21","20r-10k");
        Booking booking12 = new Booking("Khin Myat Aye",2,2,1,"Dec 19", "Dec 21","20d-203");
        Booking booking13 = new Booking("Khaing Zin",2,2,1,"Nov 19", "Nov 21","12s-2dd");
        Booking booking14 = new Booking("Su Su Aye",2,2,1,"Dec 19", "Dec 21","109-20j");

        bookings.add(booking1);
        bookings.add(booking2);
        bookings.add(booking3);
        bookings.add(booking4);
        bookings.add(booking5);
        bookings.add(booking6);
        bookings.add(booking7);

        bookings.add(booking8);
        bookings.add(booking9);
        bookings.add(booking10);
        bookings.add(booking11);
        bookings.add(booking12);
        bookings.add(booking13);
        bookings.add(booking14);

        return bookings;
    }
}

package com.innoveller.roomseller.mockData;

import java.util.ArrayList;
import java.util.List;

public class MockDataUtils {

    public static List<BookingFake> getBookingList() {
        List<BookingFake> bookings = new ArrayList<>();
        BookingFake booking1 = new BookingFake("Su Su Aye",2,2,1,"Dec 19, 2021", "Dec 21, 2021","109-20j");
        BookingFake booking2 = new BookingFake("Zin Zin",1,2,1,"Nov 1, 2021", "Nov 2, 2021","101-40e");
        BookingFake booking3 = new BookingFake("Aye Aye",2,3,1,"Aug 19, 2021", "Aug 21, 2021","10s-22d");
        BookingFake booking4 = new BookingFake("Ma Ma",1,2,1,"Dec 20, 2021", "Dec 21, 2021","20r-10k");
        BookingFake booking5 = new BookingFake("Khin Myat Aye",2,2,1,"Dec 19, 2021", "Dec 21, 2021","20d-203");
        BookingFake booking6 = new BookingFake("Khaing Zin",2,2,1,"Nov 19, 2021", "Nov 21, 2021","12s-2dd");
        BookingFake booking7 = new BookingFake("Su Su Aye",2,2,1,"Dec 19, 2021", "Dec 21, 2021","109-20j");

        BookingFake booking8 = new BookingFake("Su Su Aye",2,2,1,"Dec 19, 2021", "Dec 21, 2021","109-20j");
        BookingFake booking9 = new BookingFake("Zin Zin",1,2,1,"Nov 1, 2021", "Nov 2, 2021","101-40e");
        BookingFake booking10 = new BookingFake("Aye Aye",2,3,1,"Aug 19, 2021", "Aug 21, 2021","10s-22d");
        BookingFake booking11 = new BookingFake("Ma Ma",1,2,1,"Dec 20, 2021", "Dec 21, 2021","20r-10k");
        BookingFake booking12 = new BookingFake("Khin Myat Aye",2,2,1,"Dec 19, 2021", "Dec 21, 2021","20d-203");
        BookingFake booking13 = new BookingFake("Khaing Zin",2,2,1,"Nov 19, 2021", "Nov 21, 2021","12s-2dd");
        BookingFake booking14 = new BookingFake("Su Su Aye",2,2,1,"Dec 19, 2021", "Dec 21, 2021","109-20j");

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

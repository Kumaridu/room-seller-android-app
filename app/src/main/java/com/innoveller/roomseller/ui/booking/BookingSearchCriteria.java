package com.innoveller.roomseller.ui.booking;

import java.util.Date;

public class BookingSearchCriteria {
    private String bookingRef;
    private String guestName;
    private Date checkInDate;
    private Date bookingDate;

//    public BookingSearchCriteria(String bookingRef, String guestName, Date checkInDate, Date bookingDate) {
//        this.bookingRef = bookingRef;
//        this.guestName = guestName;
//        this.checkInDate = checkInDate;
//        this.bookingDate = bookingDate;
//    }

    private BookingSearchCriteria(BookingSearchCriteriaBuilder builder) {
        this.bookingRef = builder.bookingRef;
        this.guestName = builder.guestName;
        this.checkInDate = builder.checkInDate;
        this.bookingDate = builder.bookingDate;
    }

    public String getBookingRef() {
        return bookingRef;
    }

    public String getGuestName() {
        return guestName;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public static class BookingSearchCriteriaBuilder {
        private String bookingRef;
        private String guestName;
        private Date checkInDate;
        private Date bookingDate;

        public BookingSearchCriteriaBuilder() {}

        public BookingSearchCriteriaBuilder bookingRefAndGuestName(String bookingRef, String guestName) {
            this.bookingRef = bookingRef;
            this.guestName = guestName;
            return this;
        }

        public BookingSearchCriteriaBuilder checkInDate(Date checkInDate) {
            this.checkInDate = checkInDate;
            return this;
        }

        public BookingSearchCriteriaBuilder bookingIdDate(Date bookingDate) {
            this.bookingDate = bookingDate;
            return this;
        }

        public BookingSearchCriteria build() {
            BookingSearchCriteria criteria = new BookingSearchCriteria(this);
            return criteria;
        }
    }



}

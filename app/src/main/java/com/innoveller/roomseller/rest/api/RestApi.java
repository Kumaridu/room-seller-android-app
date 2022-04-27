package com.innoveller.roomseller.rest.api;

import com.innoveller.roomseller.rest.dtos.BookingDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RestApi {

    @GET("bookings")
    Call<List<BookingDto>> getBookingList();

    @GET("bookings/{booking_id}")
    Call<BookingDto> getBookingById(@Path("booking_id") String bookingId);

}

package com.innoveller.roomseller.rest.api;

import com.innoveller.roomseller.rest.dtos.BookingDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestApi {

    @GET("bookings")
    Call<List<BookingDto>> getBookingList();

    @GET("bookings/1")
    Call<BookingDto> getBookingById();

}

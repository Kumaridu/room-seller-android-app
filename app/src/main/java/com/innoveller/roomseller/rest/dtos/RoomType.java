package com.innoveller.roomseller.rest.dtos;

public class RoomType {
    public String roomType;
    public String maxOccupancy;
    public String subTotal;
    public String ratePlan;
    public String extraBed;

    public RoomType(String roomType, String maxOccupancy, String subTotal, String ratePlan, String extraBed) {
        this.roomType = roomType;
        this.maxOccupancy = maxOccupancy;
        this.subTotal = subTotal;
        this.ratePlan = ratePlan;
        this.extraBed = extraBed;
    }
}

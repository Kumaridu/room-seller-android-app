package com.innoveller.roomseller.rest.dtos;

import java.io.Serializable;

public class Payment implements Serializable {
    public String date;
    public Money amount;
    public String method;
}

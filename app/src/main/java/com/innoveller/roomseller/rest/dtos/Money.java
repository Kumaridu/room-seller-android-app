package com.innoveller.roomseller.rest.dtos;

import java.io.Serializable;

public class Money {
    public double amount;
    public String currency;


    public static Money of(double amount, String currency) {
        Money money = new Money();
        money.amount = amount;
        money.currency = currency;
        return money;
    }
}
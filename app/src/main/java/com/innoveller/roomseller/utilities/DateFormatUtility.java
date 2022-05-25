package com.innoveller.roomseller.utilities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class DateFormatUtility {
    private final static DateFormat FRIENDLY_DATE_FORMAT = new SimpleDateFormat("MMM dd, yyyy");

    private final static DateFormat FRIENDLY_DATE_TIME_YEAR_FORMAT = new SimpleDateFormat("MMM dd yyyy, hh:mm a");

    private final static DateFormat ISO_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static String formatFriendlyDate(Date date){
        return FRIENDLY_DATE_FORMAT.format(date);
    }

    public static String formatFriendlyDateTimeWithYear(Date date) throws ParseException {
        return FRIENDLY_DATE_TIME_YEAR_FORMAT.format(date);
    }

    public static String formatISODate(Date date){
        return ISO_DATE_FORMAT.format(date);
    }

    public static Date parseISODate(String date) throws ParseException {
        return ISO_DATE_FORMAT.parse(date);
    }
}

package com.innoveller.roomseller.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtility {
    private final static DateFormat FRIENDLY_DATE_FORMAT = new SimpleDateFormat("MMM dd, yyyy");

    private final static DateFormat FRIENDLY_DATE_TIME_YEAR_FORMAT = new SimpleDateFormat("MMM dd yyyy, hh:mm a");

    public static String formatFriendlyDate(Date date){
        return FRIENDLY_DATE_FORMAT.format(date);
    }

    public static String formatFriendlyDateTimeWithYear(Date date) throws ParseException {
        return FRIENDLY_DATE_TIME_YEAR_FORMAT.format(date);
    }

}

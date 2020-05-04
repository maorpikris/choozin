package com.choozin.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class DateCalculation {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String diff(String date) {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-d");
        LocalDate created = LocalDate.parse(date, formatter);

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("d-MMM");
        Period period = Period.between(now, created);
        if (period.getYears() > 1) {
            return created.format(formatter);
        } else if (period.getMonths() > 1) {
            return created.format(formatter2);
        } else if (period.getDays() > 1) {
            return period.getDays() + " days ago.";
        } else {
            return "Today";
        }
    }
}

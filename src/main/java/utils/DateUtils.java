package utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.time.LocalDate;
import java.util.Random;

public class DateUtils {

    public static int getCurrentYearInteger() {
        return LocalDate.now().getYear();
    }

    protected int generateFakeNumber() {
        Random rand = new Random();
        return rand.nextInt(9999);
    }

    protected String getCurrentDate() {
        DateTime nowUTC = new DateTime(DateTimeZone.UTC);
        int day = nowUTC.getDayOfMonth();
        if (day < 10) {
            String dayValue = "0" + day;
            return dayValue;
        }
        return String.valueOf(day);
    }

    protected String getCurrentMonth() {
        DateTime now = new DateTime (DateTimeZone.UTC);
        int month = now.getMonthOfYear();
        if (month< 10) {
            String monthValue = "0" + month;
            return monthValue;
        }
        return String.valueOf(month);
    }

    protected String getCurrentYearString() {
        DateTime now = new DateTime(DateTimeZone.UTC);
        return now.getYear() + "";
    }

    protected String getCurrentDay() {
        return getCurrentDate() + "/" + getCurrentMonth() + "/" + getCurrentYearString();
    }
}

package com.example.ryhmakaakkoapplication;
//test
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeStamp {


    public static int DATE;
    public static int MINUTE;
    public static int HOURS;
    public static int monthNumber;
    public static int formattedYear;

    public TimeStamp(){

    }
    public static int month(){
        /**
         *
         * Otamme Päivämäärän ja formatoimme sen vain kuukausiksi
         * esimerkiksi nyt funktio palauttaisi formatted month = Dec
         * joka pitää muuttaa numeroiksi joka tapahtuu alhaalla  if lauseilla
         * katsomme mikä kuukausi se on ja muutamme sen vain numeroksi.
         * ja palautemme kuukaudeksi
         *
         */

        //Date
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat month = new SimpleDateFormat("MMM", Locale.getDefault());
        String formattedMonth = month.format(c);


        if (formattedMonth.equals("Jan")) {
            monthNumber = 1;
        } else if (formattedMonth.equals("Feb")) {
            monthNumber = 2;
        } else if (formattedMonth.equals("Mar")) {
            monthNumber = 3;
        } else if (formattedMonth.equals("Apr")) {
            monthNumber = 4;
        } else if (formattedMonth.equals("May")) {
            monthNumber = 5;
        } else if (formattedMonth.equals("Jun")) {
            monthNumber = 6;
        } else if (formattedMonth.equals("Jul")) {
            monthNumber = 7;
        } else if (formattedMonth.equals("Aug")) {
            monthNumber = 8;
        } else if (formattedMonth.equals("Sep")) {
            monthNumber = 9;
        } else if (formattedMonth.equals("Oct")) {
            monthNumber = 10;
        } else if (formattedMonth.equals("Nov")) {
            monthNumber = 11;
        } else if (formattedMonth.equals("Dec")) {
            monthNumber = 12;
        }
        return monthNumber;
    }

    public static int date(){
        /**
         *
         * Otamme Päivämäärän ja formatoimme sen vain Päiväksi
         * joka tuo c muuttujalle esimerkiksi arvon 7. joka palautetaan main activityyn.
         * ja palautemme päivän
         *
         */

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat date = new SimpleDateFormat("dd", Locale.getDefault());
        DATE = Integer.parseInt(date.format(c));

        return DATE;
    }


    public static int year(){
        /**
         *
         * Otamme Päivämäärän ja formatoimme sen vain vuodeksi
         * ja palautemme vuoden
         */

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat year = new SimpleDateFormat("yyyy", Locale.getDefault());
        formattedYear = Integer.parseInt(year.format(c));

        return formattedYear;
    }

    public static int minute(){
        /**
         *
         * Otamme Päivämäärän ja formatoimme sen vain minuuteiksi
         * ja palautemme minuutti
         *
         */
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat minutes = new SimpleDateFormat("mm", Locale.getDefault());
        MINUTE = Integer.parseInt(minutes.format(c));

        return MINUTE;
    }

    public static int hour(){
        /**
         *
         * Otamme Päivämäärän ja formatoimme sen vain tunneiksi
         * ja palautemme tunti
         *
         *
         */
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat hour = new SimpleDateFormat("HH", Locale.getDefault());
        HOURS = Integer.parseInt(hour.format(c));

        return HOURS;
    }
}

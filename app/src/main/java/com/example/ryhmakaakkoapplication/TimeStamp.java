package com.example.ryhmakaakkoapplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
/**
 * Tämä luokka hakee ja tuo oikein formatoidut päivämäärät
 * @author Joni Tahvanainen (RaikasNeiti)
 * @version 1.0 12/2020
 */
public class TimeStamp {

    /**
     * Päivämäärä
     *
     */
    public static int DATE;
    /**
     *  Minuuttit
     */

    public static int MINUTE;
    /**
     *  Tunnit
     */
    public static int HOURS;
    /**
     * Kuukaudet
     */
    public static int monthNumber;
    /**
     * Vuodet
     */
    public static int formattedYear;

    /**
     * Hakee kuukauden kalenterista ja palauttaa sen numerona.
     * @param c on kalenterista saatu merkkijono.
     * @param month on formatisoitu osa c merkkijonosta.
     * @param formattedMonth on kuukausi merkkijonona
     * @param monthNumber on formattedMonth muutettu luvuksi.
     * @return int monthNumber
     */

    public static int month(){

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

    /**
     * Hakee päivämäärän kalenterista ja palauttaa sen.
     * @param c on kalenterista saatu merkkijono.
     * @param date on formatisoitu pieni osa c merkkijonosta.
     * @param DATE on päivämäärä date merkkijonosta.
     *
     * @return int Date
     */


    public static int date(){


        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat date = new SimpleDateFormat("dd", Locale.getDefault());
        DATE = Integer.parseInt(date.format(c));

        return DATE;
    }

    /**
     * Hakee vuoden kalenterista ja palauttaa sen.
     * @param c on kalenterista saatu merkkijono.
     * @param year on formatisoitu pieni osa c merkkijonosta.
     * @param formattedYear on vuosi year merkkijonosta.
     * @return int formattedYear
     */

    public static int year(){


        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat year = new SimpleDateFormat("yyyy", Locale.getDefault());
        formattedYear = Integer.parseInt(year.format(c));

        return formattedYear;
    }

    /**
     * hakee minuutit kalenterista ja palautaa ne.
     * @param c on kalenterista saatu merkkijono.
     * @param minutes on formatisoitu pieni osa c merkkijonosta.
     * @param MINUTE on minuutit minutes merkkijonosta.
     * @return int MINUTE
     */
    public static int minute(){

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat minutes = new SimpleDateFormat("mm", Locale.getDefault());
        MINUTE = Integer.parseInt(minutes.format(c));

        return MINUTE;
    }

    /**
     * Hakee tunnit kalenterista ja palauttaa ne.
     * @param c on kalenterista saatu merkkijono.
     * @param hour on formatisoitu pieni osa c merkkijonosta.
     * @param HOURS on tunnit hour merkkijonosta.
     * @return int HOURS
     */
    public static int hour(){

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat hour = new SimpleDateFormat("HH", Locale.getDefault());
        HOURS = Integer.parseInt(hour.format(c));

        return HOURS;
    }
}

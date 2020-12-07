package com.example.ryhmakaakkoapplication;

public class Entry {
    private String glukoosi;
    private int dayOfMonth;
    private int month;
    private int year;
    private int time;


    public Entry(String glukoosi, int dayOfMonth, int month, int year) {
        this.glukoosi = glukoosi;
        this.dayOfMonth = dayOfMonth;
        this.month = month;
        this.year = year;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Glukoosiarvo on " + glukoosi;
    }

    public String getGlukoosi() {
        return glukoosi;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void setGlukoosi(String glukoosi) {
        this.glukoosi = glukoosi;
    }


}
package com.example.z3.RoomDatabase;

import androidx.room.Ignore;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;

public class multiDate implements Serializable {

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    @Ignore
    private Date fullDate;


    public Date getFullDate() {
        return fullDate;
    }

    public void setFullDate(Date fullDate) {
        this.fullDate = fullDate;
    }

    public multiDate(int year, int month, int day, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.fullDate = new GregorianCalendar(year, month - 1, day , hour, minute).getTime();
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

}

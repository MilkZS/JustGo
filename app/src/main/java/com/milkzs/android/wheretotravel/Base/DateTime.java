package com.milkzs.android.wheretotravel.Base;

/**
 * Created by milkdz on 2018/5/12.
 */

public class DateTime {

    private int year;
    private int month;
    private int day;

    private DateTime() {
    }

    private DateTime(DateTime dateTime) {
        this.year = dateTime.year;
        this.month = dateTime.month;
        this.day = dateTime.day;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public static String formatDate(DateTime dateTime){
        return dateTime.getYear() + "-"
                + dateTime.getMonth() + "-"
                + dateTime.getDay();
    }

    /**
     * if dateTime1 older than dateTime2, return true,
     * less return false
     * equal return true
     *
     * @param dateTime1
     * @param dateTime2
     * @return
     */
    public static boolean judge(DateTime dateTime1,DateTime dateTime2) {

        if(dateTime2 == null){
            return true;
        }

        if(dateTime1.getYear() < dateTime2.getYear()){
            return false;
        }else if(dateTime1.getYear() > dateTime2.getYear()){
            return true;
        }

        if(dateTime1.getMonth() < dateTime2.getMonth()){
            return false;
        }else if(dateTime1.getMonth() > dateTime2.getMonth()){
            return true;
        }

        if(dateTime1.getDay() >= dateTime2.getDay()){
            return true;
        }
        return false;
    }

    public static class Builder {

        private DateTime dateTime;

        public Builder() {
            this.dateTime = new DateTime();
        }

        public Builder setYear(int year) {
            dateTime.year = year;
            return this;
        }

        public Builder setMonth(int month) {
            dateTime.month = month;
            return this;
        }

        public Builder setDay(int day) {
            dateTime.day = day;
            return this;
        }

        public DateTime build() {
            return new DateTime(dateTime);
        }
    }
}

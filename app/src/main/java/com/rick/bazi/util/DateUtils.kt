package com.rick.bazi.util

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class DateUtils {

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertMillisToLocalDate(millis: Long): ZonedDateTime {
        // Interpret the milliseconds as the start of the day in UTC, then convert to Los Angeles time
        val utcDateAtStartOfDay = Instant.ofEpochMilli(millis)
            .atZone(ZoneOffset.UTC)
            .toLocalDate()
        println("UTC Date at Start of Day: $utcDateAtStartOfDay") // Debugging UTC date

        // Convert to the same instant in Local time zone
        val localDate = utcDateAtStartOfDay.atStartOfDay(ZoneId.systemDefault())
        println("Local Date: $localDate") // Debugging local date


        return localDate

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun dateToString(date: ZonedDateTime?): String {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
        return dateFormatter.format(date)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDayTianganBaseStr(
        year: Int, month: Int, day: Int
    ): String {
        var s = year
        var u = 0
        var m = month
        var d = day
        var x = 0
        var r = 0

        //check year 2000 case, todo
        if(month == 1 || month == 2){
            m = month + 12
            s = year - 1
        }

        //calculate x
        if(s <= 2099 && s >= 2000){
            x = 54
        }
        if(s <= 1999 && s >= 1900){
            x = 9
        }

        if(s % 100 == 0){
            s = 100
        }else{
            s = s % 100
        }

        u = s % 4

        val result = "{$s},{$u},{$m},{$d},{$x}"

        return result
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDayTianganBase(
        year: Int, month: Int, day: Int
    ): Int {
        var s = year
        var u = 0
        var m = month
        var d = day
        var x = 0
        var r = 0

//        Log.i("[Rick]","Step 1");

        //check year 2000 case, todo
        if(month == 1 || month == 2){
            m = month + 12
            s = year - 1
        }

        //calculate x
        if(s <= 2399 && s >= 2300){
            x = 6
        }
        if(s <= 2299 && s >= 2200){
            x = 22
        }
        if(s <= 2199 && s >= 2100){
            x = 38
        }
        if(s <= 2099 && s >= 2000){
            x = 54
        }
        if(s <= 1999 && s >= 1900){
            x = 9
        }

        if(s <= 1899 && s >= 1800){
            x = 25
        }
        if(s <= 1799 && s >= 1700){
            x = 41
        }
        if(s <= 1699 && s >= 1600){
            x = 57
        }

        if(s % 100 == 0){
            s = 100
        }else{
            s = s % 100
        }

//        Log.i("[Rick]","Step 2");
        u = s % 4

        r = (s/4)*6 + ((s/4)*3 + u)*5 + (m*3 - 7)/5 + d + x
        if(m % 2 == 0){
            r = r + 30
        }
//        Log.i("[Rick]","Step 3 ${r}");
        return r
    }
}
package com.rick.bazi.util

import android.os.Build
import androidx.annotation.RequiresApi
import com.rick.bazi.data.DiZhi
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.TimeZone

class BaziUtil {

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDizhiByLunarMonth(lunarMonth: Int): DiZhi {
        var dz: DiZhi = DiZhi.DIZHI_ZI
        if (lunarMonth == 11) {
            dz = DiZhi.DIZHI_ZI
        } else if (lunarMonth == 12) {
            dz = DiZhi.DIZHI_CHOU
        } else if (lunarMonth == 1) {
            dz = DiZhi.DIZHI_YIN
        } else if (lunarMonth == 2) {
            dz = DiZhi.DIZHI_MOU
        } else if (lunarMonth == 3) {
            dz = DiZhi.DIZHI_CHEN
        } else if (lunarMonth == 4) {
            dz = DiZhi.DIZHI_SI
        } else if (lunarMonth == 5) {
            dz = DiZhi.DIZHI_WU
        } else if (lunarMonth == 6) {
            dz = DiZhi.DIZHI_WEI
        } else if (lunarMonth == 7) {
            dz = DiZhi.DIZHI_SHEN
        } else if (lunarMonth == 8) {
            dz = DiZhi.DIZHI_YOU
        } else if (lunarMonth == 9) {
            dz = DiZhi.DIZHI_XU
        } else if (lunarMonth == 10) {
            dz = DiZhi.DIZHI_HAI
        }
        return dz;
    }

    fun getHourDZ(hour: Int): DiZhi {
        //calculate HourDizhi
        var dz = DiZhi.DIZHI_ZI
        var hourDZ: Int = 0
        if (hour == 23 || hour == 0) {
            dz = DiZhi.DIZHI_ZI
            hourDZ = 1
        } else if (hour == 1 || hour == 2) {
            dz = DiZhi.DIZHI_CHOU
            hourDZ = 2
        } else if (hour == 3 || hour == 4) {
            dz = DiZhi.DIZHI_YIN
            hourDZ = 3
        } else if (hour == 5 || hour == 6) {
            dz = DiZhi.DIZHI_MOU
            hourDZ = 4
        } else if (hour == 7 || hour == 8) {
            dz = DiZhi.DIZHI_CHEN
            hourDZ = 5
        } else if (hour == 9 || hour == 10) {
            dz = DiZhi.DIZHI_SI
            hourDZ = 6
        } else if (hour == 11 || hour == 12) {
            dz = DiZhi.DIZHI_WU
            hourDZ = 7
        } else if (hour == 13 || hour == 14) {
            dz = DiZhi.DIZHI_WEI
            hourDZ = 8
        } else if (hour == 15 || hour == 16) {
            dz = DiZhi.DIZHI_SHEN
            hourDZ = 9
        } else if (hour == 17 || hour == 18) {
            dz = DiZhi.DIZHI_YOU
            hourDZ = 10
        } else if (hour == 19 || hour == 20) {
            dz = DiZhi.DIZHI_XU
            hourDZ = 11
        } else if (hour == 21 || hour == 22) {
            dz = DiZhi.DIZHI_HAI
            hourDZ = 12
        }

        return dz
    }

    fun getHourDZInt(hour: Int): Int {
        var dz = DiZhi.DIZHI_ZI
        var hourDZ: Int = 0
        if (hour == 23 || hour == 0) {
            dz = DiZhi.DIZHI_ZI
            hourDZ = 1
        } else if (hour == 1 || hour == 2) {
            dz = DiZhi.DIZHI_CHOU
            hourDZ = 2
        } else if (hour == 3 || hour == 4) {
            dz = DiZhi.DIZHI_YIN
            hourDZ = 3
        } else if (hour == 5 || hour == 6) {
            dz = DiZhi.DIZHI_MOU
            hourDZ = 4
        } else if (hour == 7 || hour == 8) {
            dz = DiZhi.DIZHI_CHEN
            hourDZ = 5
        } else if (hour == 9 || hour == 10) {
            dz = DiZhi.DIZHI_SI
            hourDZ = 6
        } else if (hour == 11 || hour == 12) {
            dz = DiZhi.DIZHI_WU
            hourDZ = 7
        } else if (hour == 13 || hour == 14) {
            dz = DiZhi.DIZHI_WEI
            hourDZ = 8
        } else if (hour == 15 || hour == 16) {
            dz = DiZhi.DIZHI_SHEN
            hourDZ = 9
        } else if (hour == 17 || hour == 18) {
            dz = DiZhi.DIZHI_YOU
            hourDZ = 10
        } else if (hour == 19 || hour == 20) {
            dz = DiZhi.DIZHI_XU
            hourDZ = 11
        } else if (hour == 21 || hour == 22) {
            dz = DiZhi.DIZHI_HAI
            hourDZ = 12
        }

        return hourDZ
    }

    val solarTermInfo = arrayOf(
        0,
        21208,
        42467,
        63836,
        85337,
        107014,
        128867,
        150921,
        173149,
        195551,
        218072,
        240693,
        263343,
        285989,
        308563,
        331033,
        353350,
        375494,
        397447,
        419210,
        440795,
        462224,
        483532,
        504758
    )

//    var solarTerm = new Array("小寒","大寒","立春","雨水","惊蛰","春分","清明","谷雨","立夏","小满","芒种","夏至","小暑","大暑","立秋","处暑","白露","秋分","寒露","霜降","立冬","小雪","大雪","冬至");

    fun getJieqiByLunaryear(
        lunarYear: Int,
        year: Int,
        month: Int,
        day: Int,
        hour: Int,
        minute: Int,
    ): Int {
        var lunarSolarTerms = Array<Long>(24) { 0 }

//        var gCalendar : GregorianCalendar = GregorianCalendar.getInstance()

        val calendar = GregorianCalendar.getInstance().apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }

        var lunarGap = lunarYear - 1900
        println("lunarYear=$lunarYear  lunarGap=$lunarGap")

        for (i in 0..23) {
            lunarSolarTerms[i] =
                31556925974 * lunarGap + solarTermInfo[i] * 60000 - 2208549300000
            println("lunarSolarTerms[$i]=${lunarSolarTerms[i]}")

        }

        val cal = Calendar.getInstance()
        cal.set(year, month, day, hour, minute, 1)

        var solarMilisec = cal.timeInMillis


        var jieqi = 0
        for (i in 0..23) {
            println("Compare $i, solar[$solarMilisec] VS lunar ${lunarSolarTerms[i]}")
            if (solarMilisec < lunarSolarTerms[0]) {
                println("Found jieqi=$jieqi, < 0")
                jieqi = -1
                break
            }
            if (solarMilisec > lunarSolarTerms[23]) {
                println("Found jieqi=$jieqi, > 23")
                jieqi = -1
                break
            }

            if (solarMilisec > lunarSolarTerms[i]) {
                jieqi = i
                println("Found jieqi=$jieqi")
                break
            }
        }
        return jieqi;
    }

    fun getMonthDZByLunaryear(
        lunarYear: Int,
        lunarMonth: Int,
        year: Int,
        month: Int,
        day: Int,
        hour: Int,
        minute: Int,
    ): DiZhi {
        println("lunarYear=$lunarYear, lunarMonth=$lunarMonth, year=$year, month=$month, day=$day, hour=$hour ")
        var dz: DiZhi = DiZhi.DIZHI_ZI

        var jieqi = getJieqiByLunaryear(lunarYear, year, month, day, hour, minute)

        if (jieqi < 0 || jieqi >= 22) {
            dz = DiZhi.DIZHI_ZI
        } else if (jieqi == 0 || jieqi == 1) {
            dz = DiZhi.DIZHI_CHOU
        } else if (jieqi == 2 || jieqi == 3) {
            dz = DiZhi.DIZHI_YIN
        } else if (jieqi == 4 || jieqi == 5) {
            dz = DiZhi.DIZHI_MOU
        } else if (jieqi == 6 || jieqi == 7) {
            dz = DiZhi.DIZHI_CHEN
        } else if (jieqi == 8 || jieqi == 9) {
            dz = DiZhi.DIZHI_SI
        } else if (jieqi == 10 || jieqi == 11) {
            dz = DiZhi.DIZHI_WU
        } else if (jieqi == 12 || jieqi == 13) {
            dz = DiZhi.DIZHI_WEI
        } else if (jieqi == 14 || jieqi == 15) {
            dz = DiZhi.DIZHI_SHEN
        } else if (jieqi == 16 || jieqi == 17) {
            dz = DiZhi.DIZHI_YOU
        } else if (jieqi == 18 || jieqi == 19) {
            dz = DiZhi.DIZHI_XU
        } else if (jieqi == 20 || jieqi == 21) {
            dz = DiZhi.DIZHI_HAI
        }
        return dz
    }


    fun makeUTCCalendar(): Calendar? {
        val calendar: Calendar = GregorianCalendar.getInstance().apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        return calendar
    }

    fun UTC(
        calendar: Calendar,
        year: Int,
        month: Int,
        day: Int,
        hour: Int,
        minute: Int,
        second: Int
    ): Long {
        var l: Long = 0
        calendar.clear()
        calendar.set(year, month, day, hour, minute, second)
        return calendar.timeInMillis
    }

    fun getSolarTermCalendar(
        calendar: Calendar,
        solarYear: Int,
        index: Int
    ): Date {
        var l = (31556925974.7.toLong() * (solarYear - 1900)) + (solarTermInfo[index] * 60000L)
        l = l + UTC(calendar, 1900, 0, 6, 2, 5, 0)
        return Date(l)
    }

    fun getUTCDay(calendar: Calendar, date: Date): Int {
        var day = 1
        calendar.clear()
        calendar.timeInMillis = date.time

        return calendar.get(Calendar.DAY_OF_MONTH)
    }

    fun getSolarTermDay(
        calendar: Calendar,
        solarYear: Int,
        index: Int
    ): Int {
        val date: Date = getSolarTermCalendar(calendar, solarYear, index)
        return getUTCDay(calendar, date)
    }

    fun getCyclicalYearBase(
        solarYear: Int,
        solarMonth: Int,
        solarDay: Int,
        hour: Int
    ) : Int {
        var cyclicalYear = 0
        val calendar: Calendar = makeUTCCalendar()!!

        var term2 = getSolarTermDay(calendar, solarYear, 2)

        if ((solarMonth < 1) || ((solarMonth == 1) && (solarDay < term2))) {
            cyclicalYear = (((solarYear - 1900) + 36) - 1) % 60;
        } else {
            cyclicalYear = ((solarYear - 1900) + 36) % 60;
        }

        cyclicalYear += 1
        println("cyclicalYear=$cyclicalYear")
        return cyclicalYear
    }

    fun getCyclicalMonthBase(
        solarYear: Int,
        solarMonth: Int,
        solarDay: Int,
        hour: Int
    ) : Int{
        var cyclicalYear = 0
        var cyclicalMonth = 0
        var cyclicalDay : Int = 0

        val calendar: Calendar = makeUTCCalendar()!!

        var term2 = getSolarTermDay(calendar, solarYear, 2)

        if ((solarMonth < 1) || ((solarMonth == 1) && (solarDay < term2))) {
            cyclicalYear = (((solarYear - 1900) + 36) - 1) % 60;
        } else {
            cyclicalYear = ((solarYear - 1900) + 36) % 60;
        }

        var firstNode = getSolarTermDay(calendar, solarYear, solarMonth * 2)

        if (solarDay < firstNode) {
            cyclicalMonth = (((solarYear - 1900) * 12) + solarMonth + 12) % 60;
        } else {
            cyclicalMonth = (((solarYear - 1900) * 12) + solarMonth + 13) % 60;
        }
        println("cyclicalMonth=$cyclicalMonth")

        var tmpUTC = UTC(calendar, solarYear, solarMonth, solarDay, hour, 0, 0)
        var tmp =  (tmpUTC / 86400000) + 25567 + 10
        cyclicalDay = (tmp % 60).toInt()
        println("cyclicalDay=$cyclicalDay")

        return cyclicalMonth
    }


}


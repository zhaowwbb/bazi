package com.rick.bazi.util
import kotlin.math.floor
import kotlin.math.sin


object VietCalendar {
    private const val PI = Math.PI

    /**
     *
     * @param dd
     * @param mm
     * @param yy
     * @return the number of days since 1 January 4713 BC (Julian calendar)
     */
    fun jdFromDate(dd: Int, mm: Int, yy: Int): Int {
        val a = (14 - mm) / 12
        val y = yy + 4800 - a
        val m = mm + 12 * a - 3
        var jd = dd + (153 * m + 2) / 5 + 365 * y + y / 4 - y / 100 + y / 400 - 32045
        if (jd < 2299161) {
            jd = dd + (153 * m + 2) / 5 + 365 * y + y / 4 - 32083
        }
        //jd = jd - 1721425;
        return jd
    }

    /**
     * http://www.tondering.dk/claus/calendar.html Section: Is there a formula
     * for calculating the Julian day number?
     *
     * @param jd - the number of days since 1 January 4713 BC (Julian calendar)
     * @return
     */
    fun jdToDate(jd: Int): IntArray {
        val a: Int
        val b: Int
        val c: Int
        if (jd > 2299160) { // After 5/10/1582, Gregorian calendar
            a = jd + 32044
            b = (4 * a + 3) / 146097
            c = a - b * 146097 / 4
        } else {
            b = 0
            c = jd + 32082
        }
        val d = (4 * c + 3) / 1461
        val e = c - 1461 * d / 4
        val m = (5 * e + 2) / 153
        val day = e - (153 * m + 2) / 5 + 1
        val month = m + 3 - 12 * (m / 10)
        val year = b * 100 + d - 4800 + m / 10
        return intArrayOf(day, month, year)
    }

    /**
     * Solar longitude in degrees Algorithm from: Astronomical Algorithms, by
     * Jean Meeus, 1998
     *
     * @param jdn - number of days since noon UTC on 1 January 4713 BC
     * @return
     */
    private fun sunLongitude(jdn: Double): Double {
        return sunLongitudeAA98(jdn)
    }

    private fun sunLongitudeAA98(jdn: Double): Double {
        val julianTime = (jdn - 2451545.0) / 36525 // Time in Julian centuries from 2000-01-01 12:00:00 GMT
        val squareJulianTime = julianTime * julianTime
        val dr = PI / 180 // degree to radian
        val meanAnomaly =
            357.52910 + 35999.05030 * julianTime - 0.0001559 * squareJulianTime - 0.00000048 * julianTime * squareJulianTime // mean anomaly, degree
        val meanLongitude = 280.46645 + 36000.76983 * julianTime + 0.0003032 * squareJulianTime // mean longitude, degree
        var dl = (1.914600 - 0.004817 * julianTime - 0.000014 * squareJulianTime) * sin(dr * meanAnomaly)
        dl += (0.019993 - 0.000101 * julianTime) * sin(dr * 2 * meanAnomaly) + 0.000290 * sin(dr * 3 * meanAnomaly)
        var trueLongitude = meanLongitude + dl // true longitude, degree
        trueLongitude -= 360 * floorInt(trueLongitude / 360) // Normalize to (0, 360)
        return trueLongitude
    }

    private fun newMoon(k: Int): Double {
        //return CC2K.newMoonTime(k);
        return newMoonAA98(k)
    }

    /**
     * Julian day number of the kth new moon after (or before) the New Moon of
     * 1900-01-01 13:51 GMT. Accuracy: 2 minutes Algorithm from: Astronomical
     * Algorithms, by Jean Meeus, 1998
     *
     * @param k
     * @return the Julian date number (number of days since noon UTC on 1
     * January 4713 BC) of the New Moon
     */
    private fun newMoonAA98(k: Int): Double {
        val julianTime = k / 1236.85 // Time in Julian centuries from 1900 January 0.5
        val squareJulianTime = julianTime * julianTime
        val cubicJulianTime = squareJulianTime * julianTime
        val dr = PI / 180
        var jd1 = 2415020.75933 + 29.53058868 * k + 0.0001178 * squareJulianTime - 0.000000155 * cubicJulianTime
        jd1 += 0.00033 * sin((166.56 + 132.87 * julianTime - 0.009173 * squareJulianTime) * dr) // Mean new moon
        val sunMeanAnomaly = 359.2242 + 29.10535608 * k - 0.0000333 * squareJulianTime - 0.00000347 * cubicJulianTime // Sun's mean anomaly
        val moonMeanAnomaly =
            306.0253 + 385.81691806 * k + 0.0107306 * squareJulianTime + 0.00001236 * cubicJulianTime // Moon's mean anomaly
        val f =
            21.2964 + 390.67050646 * k - 0.0016528 * squareJulianTime - 0.00000239 * cubicJulianTime // Moon's argument of latitude
        var c1 = (0.1734 - 0.000393 * julianTime) * sin(sunMeanAnomaly * dr) + 0.0021 * sin(2 * dr * sunMeanAnomaly)
        c1 = c1 - 0.4068 * sin(moonMeanAnomaly * dr) + 0.0161 * sin(dr * 2 * moonMeanAnomaly)
        c1 -= 0.0004 * sin(dr * 3 * moonMeanAnomaly)
        c1 = c1 + 0.0104 * sin(dr * 2 * f) - 0.0051 * sin(dr * (sunMeanAnomaly + moonMeanAnomaly))
        c1 = c1 - 0.0074 * sin(dr * (sunMeanAnomaly - moonMeanAnomaly)) + 0.0004 * sin(dr * (2 * f + sunMeanAnomaly))
        c1 = c1 - 0.0004 * sin(dr * (2 * f - sunMeanAnomaly)) - 0.0006 * sin(dr * (2 * f + moonMeanAnomaly))
        c1 += 0.0010 * sin(dr * (2 * f - moonMeanAnomaly)) + 0.0005 * sin(dr * (2 * moonMeanAnomaly + sunMeanAnomaly))
        val deltAt: Double
        deltAt = if (julianTime < -11) {
            0.001 + 0.000839 * julianTime + 0.0002261 * squareJulianTime - 0.00000845 * cubicJulianTime - 0.000000081 * julianTime * cubicJulianTime
        } else {
            -0.000278 + 0.000265 * julianTime + 0.000262 * squareJulianTime
        }
        return jd1 + c1 - deltAt
    }

    private fun floorInt(d: Double): Int {
        return floor(d).toInt()
    }

    private fun getSunLongitude(dayNumber: Int, timeZone: Double): Double {
        return sunLongitude(dayNumber - 0.5 - timeZone / 24)
    }

    private fun getNewMoonDay(k: Int, timeZone: Double): Int {
        val jd = newMoon(k)
        return floorInt(jd + 0.5 + timeZone / 24)
    }

    private fun getLunarMonth11(yy: Int, timeZone: Double): Int {
        val off = jdFromDate(31, 12, yy) - 2415021.076998695
        val k = floorInt(off / 29.530588853)
        var nm = getNewMoonDay(k, timeZone)
        val sunLong = floorInt(getSunLongitude(nm, timeZone) / 30)
        if (sunLong >= 9) {
            nm = getNewMoonDay(k - 1, timeZone)
        }
        return nm
    }

    private fun getLeapMonthOffset(a11: Int, timeZone: Double): Int {
        val k = floorInt(0.5 + (a11 - 2415021.076998695) / 29.530588853)
        var last: Int // Month 11 contains point of sun longutide 3*PI/2 (December solstice)
        var i = 1 // We start with the month following lunar month 11
        var arc = floorInt(getSunLongitude(getNewMoonDay(k + i, timeZone), timeZone) / 30)
        do {
            last = arc
            i++
            arc = floorInt(getSunLongitude(getNewMoonDay(k + i, timeZone), timeZone) / 30)
        } while (arc != last && i < 14)
        return i - 1
    }

    /**
     *
     * @param dd
     * @param mm
     * @param yy
     * @param timeZone
     * @return array of [lunarDay, lunarMonth, lunarYear, leapOrNot]
     */
    fun convertSolar2Lunar(dd: Int, mm: Int, yy: Int, timeZone: Double): IntArray {
        val lunarDay: Int
        var lunarMonth: Int
        var lunarYear: Int
        var lunarLeap: Int
        val dayNumber = jdFromDate(dd, mm, yy)
        val k = floorInt((dayNumber - 2415021.076998695) / 29.530588853)
        var monthStart = getNewMoonDay(k + 1, timeZone)
        if (monthStart > dayNumber) {
            monthStart = getNewMoonDay(k, timeZone)
        }
        var a11 = getLunarMonth11(yy, timeZone)
        var b11 = a11
        if (a11 >= monthStart) {
            lunarYear = yy
            a11 = getLunarMonth11(yy - 1, timeZone)
        } else {
            lunarYear = yy + 1
            b11 = getLunarMonth11(yy + 1, timeZone)
        }
        lunarDay = dayNumber - monthStart + 1
        val diff = floorInt((monthStart - a11) / 29.toDouble())
        lunarLeap = 0
        lunarMonth = diff + 11
        if (b11 - a11 > 365) {
            val leapMonthDiff = getLeapMonthOffset(a11, timeZone)
            if (diff >= leapMonthDiff) {
                lunarMonth = diff + 10
                if (diff == leapMonthDiff) {
                    lunarLeap = 1
                }
            }
        }
        if (lunarMonth > 12) {
            lunarMonth -= 12
        }
        if (lunarMonth >= 11 && diff < 4) {
            lunarYear -= 1
        }
        return intArrayOf(lunarDay, lunarMonth, lunarYear, lunarLeap)
    }

    fun convertLunar2Solar(
        lunarDay: Int,
        lunarMonth: Int,
        lunarYear: Int,
        lunarLeap: Int,
        timeZone: Double
    ): IntArray {
        val a11: Int
        val b11: Int
        if (lunarMonth < 11) {
            a11 = getLunarMonth11(lunarYear - 1, timeZone)
            b11 = getLunarMonth11(lunarYear, timeZone)
        } else {
            a11 = getLunarMonth11(lunarYear, timeZone)
            b11 = getLunarMonth11(lunarYear + 1, timeZone)
        }
        val k = floorInt(0.5 + (a11 - 2415021.076998695) / 29.530588853)
        var off = lunarMonth - 11
        if (off < 0) {
            off += 12
        }
        if (b11 - a11 > 365) {
            val leapOff = getLeapMonthOffset(a11, timeZone)
            var leapMonth = leapOff - 2
            if (leapMonth < 0) {
                leapMonth += 12
            }
            if (lunarLeap != 0 && lunarMonth != leapMonth) {
                println("Invalid input!")
                return intArrayOf(0, 0, 0)
            } else if (lunarLeap != 0 || off >= leapOff) {
                off += 1
            }
        }
        val monthStart = getNewMoonDay(k + off, timeZone)
        return jdToDate(monthStart + lunarDay - 1)
    }

    fun getLunarMonth(        solarDay: Int,
                              solarMonth: Int,
                              solarYear: Int) : IntArray{
        val timeZone = 7.0
        val jd = jdFromDate(solarDay, solarMonth, solarYear)
        val s = jdToDate(jd)
        val lunarDate = convertSolar2Lunar(s[0], s[1], s[2], timeZone)
        return lunarDate
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val timeZone = 7.0
        val jd = jdFromDate(6, 11, 2020)
        val s = jdToDate(jd)
        val l = convertSolar2Lunar(s[0], s[1], s[2], timeZone)
        val s2 = convertLunar2Solar(l[0], l[1], l[2], l[3], timeZone)
        if (s[0] == s2[0] && s[1] == s2[1] && s[2] == s2[2]) {
            println("OK! " + s[0] + "/" + s[1] + "/" + s[2] + " -> " + l[0] + "/" + l[1] + "/" + l[2] + if (l[3] == 0) "" else " leap")
        } else {
            System.err.println("ERROR! " + s[0] + "/" + s[1] + "/" + s[2] + " -> " + l[0] + "/" + l[1] + "/" + l[2] + if (l[3] == 0) "" else " leap")
        }
    }
}
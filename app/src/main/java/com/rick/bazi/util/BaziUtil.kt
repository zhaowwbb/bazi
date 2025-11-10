package com.rick.bazi.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rick.bazi.R
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.data.BaziSummaryMSG.Companion.TAOHUA_0
import com.rick.bazi.data.BaziSummaryMSG.Companion.TAOHUA_1
import com.rick.bazi.data.BaziSummaryMSG.Companion.TAOHUA_2
import com.rick.bazi.data.BaziSummaryMSG.Companion.TAOHUA_3
import com.rick.bazi.data.BaziSummaryMSG.Companion.TAOHUA_4
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.FEMALE
import com.rick.bazi.data.MALE
import com.rick.bazi.data.ShiShen
import com.rick.bazi.data.TianGan
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.temporal.TemporalAdjusters
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.TimeZone

class BaziUtil {

    val tianganStrMap: Map<TianGan, Int> = mapOf(
        TianGan.TIANGAN_JIA to R.string.tiangan_jia,
        TianGan.TIANGAN_YI to R.string.tiangan_yi,
        TianGan.TIANGAN_BING to R.string.tiangan_bing,
        TianGan.TIANGAN_DING to R.string.tiangan_ding,
        TianGan.TIANGAN_WU to R.string.tiangan_wu,
        TianGan.TIANGAN_JI to R.string.tiangan_ji,
        TianGan.TIANGAN_GENG to R.string.tiangan_geng,
        TianGan.TIANGAN_XIN to R.string.tiangan_xin,
        TianGan.TIANGAN_REN to R.string.tiangan_ren,
        TianGan.TIANGAN_GUI to R.string.tiangan_gui
    )

    val dizhiStrMap: Map<DiZhi, Int> = mapOf(
        DiZhi.DIZHI_ZI to R.string.dizhi_zi,
        DiZhi.DIZHI_CHOU to R.string.dizhi_chou,
        DiZhi.DIZHI_YIN to R.string.dizhi_yin,
        DiZhi.DIZHI_MOU to R.string.dizhi_mou,
        DiZhi.DIZHI_CHEN to R.string.dizhi_chen,
        DiZhi.DIZHI_SI to R.string.dizhi_si,
        DiZhi.DIZHI_WU to R.string.dizhi_wu,
        DiZhi.DIZHI_WEI to R.string.dizhi_wei,
        DiZhi.DIZHI_SHEN to R.string.dizhi_shen,
        DiZhi.DIZHI_YOU to R.string.dizhi_you,
        DiZhi.DIZHI_XU to R.string.dizhi_xu,
        DiZhi.DIZHI_HAI to R.string.dizhi_hai
    )

    val tgLookupMap: Map<Int, TianGan> = mapOf(
        1 to TianGan.TIANGAN_JIA,
        2 to TianGan.TIANGAN_YI,
        3 to TianGan.TIANGAN_BING,
        4 to TianGan.TIANGAN_DING,
        5 to TianGan.TIANGAN_WU,
        6 to TianGan.TIANGAN_JI,
        7 to TianGan.TIANGAN_GENG,
        8 to TianGan.TIANGAN_XIN,
        9 to TianGan.TIANGAN_REN,
        0 to TianGan.TIANGAN_GUI
    )

    val dzLookupMap: Map<Int, DiZhi> = mapOf(
        1 to DiZhi.DIZHI_ZI,
        2 to DiZhi.DIZHI_CHOU,
        3 to DiZhi.DIZHI_YIN,
        4 to DiZhi.DIZHI_MOU,
        5 to DiZhi.DIZHI_CHEN,
        6 to DiZhi.DIZHI_SI,
        7 to DiZhi.DIZHI_WU,
        8 to DiZhi.DIZHI_WEI,
        9 to DiZhi.DIZHI_SHEN,
        10 to DiZhi.DIZHI_YOU,
        11 to DiZhi.DIZHI_XU,
        0 to DiZhi.DIZHI_HAI
    )

    val msgMap: Map<String, Int> = mapOf(
        TAOHUA_0 to R.string.msg_taohua_0,
        TAOHUA_1 to R.string.msg_taohua_1,
        TAOHUA_2 to R.string.msg_taohua_2,
        TAOHUA_3 to R.string.msg_taohua_3,
        TAOHUA_4 to R.string.msg_taohua_4
    )

    val tgYangrenLookupMap: Map<TianGan, DiZhi> = mapOf(
        TianGan.TIANGAN_JIA to DiZhi.DIZHI_MOU,
        TianGan.TIANGAN_YI to DiZhi.DIZHI_YIN,
        TianGan.TIANGAN_BING to DiZhi.DIZHI_WU,
        TianGan.TIANGAN_DING to DiZhi.DIZHI_SI,
        TianGan.TIANGAN_WU to DiZhi.DIZHI_WU,
        TianGan.TIANGAN_JI to DiZhi.DIZHI_SI,
        TianGan.TIANGAN_GENG to DiZhi.DIZHI_YOU,
        TianGan.TIANGAN_XIN to DiZhi.DIZHI_SHEN,
        TianGan.TIANGAN_REN to DiZhi.DIZHI_ZI,
        TianGan.TIANGAN_GUI to DiZhi.DIZHI_HAI
    )

    val yangDizhiSet = setOf(
        DiZhi.DIZHI_ZI,
        DiZhi.DIZHI_YIN,
        DiZhi.DIZHI_CHEN,
        DiZhi.DIZHI_WU,
        DiZhi.DIZHI_SHEN,
        DiZhi.DIZHI_XU
    )

    val yinDizhiSet = setOf(
        DiZhi.DIZHI_CHOU,
        DiZhi.DIZHI_MOU,
        DiZhi.DIZHI_SI,
        DiZhi.DIZHI_WEI,
        DiZhi.DIZHI_YOU,
        DiZhi.DIZHI_HAI
    )

    fun isYangDizhi(dz: DiZhi): Boolean {
        return yangDizhiSet.contains(dz)
    }

    fun isYinDizhi(dz: DiZhi): Boolean {
        return yinDizhiSet.contains(dz)
    }

    fun getYangren(tg: TianGan): DiZhi {
        return tgYangrenLookupMap.get(tg)!!
    }

    val taohuaSet = setOf(DiZhi.DIZHI_ZI, DiZhi.DIZHI_WU, DiZhi.DIZHI_MOU, DiZhi.DIZHI_YOU)

    fun isTaohua(dz: DiZhi): Boolean {
        return taohuaSet.contains(dz)
    }

    @Composable
    fun getTianGan(tianganBase: Int): TianGan {
        var tg: TianGan = TianGan.TIANGAN_JIA
        val tgIndex = tianganBase % 10
        tg = tgLookupMap[tgIndex]!!
        return tg
    }

    @Composable
    fun getDiZhi(dizhiBase: Int): DiZhi {
        var dz: DiZhi = DiZhi.DIZHI_ZI
        val index = dizhiBase % 12
        dz = dzLookupMap[index]!!
        return dz
    }

    @Composable
    fun getTianGanLabel(tiangan: TianGan): String {
        val key = tianganStrMap.getValue(tiangan)
        val label = stringResource(key)
        return label
    }

    @Composable
    fun getDizhiLabel(dizhi: DiZhi): String {
        val dizhiStrKey = dizhiStrMap.getValue(dizhi)
        val label = stringResource(dizhiStrKey)
        return label
    }

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
        var pos: Int = index
        if (index > 23) {
            println("Found broken case, index=$index")
            pos = 23
        }
        var l = (31556925974.7.toLong() * (solarYear - 1900)) + (solarTermInfo[pos] * 60000L)
        l = l + UTC(calendar, 1900, 0, 6, 2, 5, 0)
        return Date(l)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getLocalDate(
        calendar: Calendar,
        solarYear: Int,
        solarMonth: Int
    ): ZonedDateTime {
        var index = (solarMonth - 1) * 2
        println("   getLocalDate  index= $index")
        var l = (31556925974.7.toLong() * (solarYear - 1900)) + (solarTermInfo[index] * 60000L)
        l = l + UTC(calendar, 1900, 0, 6, 2, 5, 0)

        val utcDateAtStartOfDay = Instant.ofEpochMilli(l)
            .atZone(ZoneOffset.UTC)
            .toLocalDate()

        val localDate = utcDateAtStartOfDay.atStartOfDay(ZoneId.systemDefault())
        return localDate
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
    ): Int {
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
    ): Int {
        var cyclicalYear = 0
        var cyclicalMonth = 0
        var cyclicalDay: Int = 0

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
        var tmp = (tmpUTC / 86400000) + 25567 + 10
        cyclicalDay = (tmp % 60).toInt()
        println("cyclicalDay=$cyclicalDay")

        return cyclicalMonth
    }

    fun getTodayCyclicalYearBase(): Int {
        val calendar = Calendar.getInstance()

        var yearBase = getCyclicalYearBase(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
            calendar.get(Calendar.HOUR_OF_DAY)
        )
        return yearBase
    }

    fun getCanggan(dz: DiZhi): Array<TianGan> {
        var tgArray = arrayOf(TianGan.TIANGAN_JIA)
        if (dz == DiZhi.DIZHI_ZI) {
            tgArray = arrayOf(TianGan.TIANGAN_GUI)
        } else if (dz == DiZhi.DIZHI_CHOU) {
            tgArray = arrayOf(TianGan.TIANGAN_JI, TianGan.TIANGAN_GUI, TianGan.TIANGAN_XIN)
        } else if (dz == DiZhi.DIZHI_YIN) {
            tgArray = arrayOf(TianGan.TIANGAN_JIA, TianGan.TIANGAN_BING, TianGan.TIANGAN_WU)
        } else if (dz == DiZhi.DIZHI_MOU) {
            tgArray = arrayOf(TianGan.TIANGAN_YI)
        } else if (dz == DiZhi.DIZHI_CHEN) {
            tgArray = arrayOf(TianGan.TIANGAN_WU, TianGan.TIANGAN_YI, TianGan.TIANGAN_GUI)
        } else if (dz == DiZhi.DIZHI_SI) {
            tgArray = arrayOf(TianGan.TIANGAN_BING, TianGan.TIANGAN_GENG, TianGan.TIANGAN_WU)
        } else if (dz == DiZhi.DIZHI_WU) {
            tgArray = arrayOf(TianGan.TIANGAN_DING, TianGan.TIANGAN_JI)
        } else if (dz == DiZhi.DIZHI_WEI) {
            tgArray = arrayOf(TianGan.TIANGAN_JI, TianGan.TIANGAN_DING, TianGan.TIANGAN_YI)
        } else if (dz == DiZhi.DIZHI_SHEN) {
            tgArray = arrayOf(TianGan.TIANGAN_GENG, TianGan.TIANGAN_REN, TianGan.TIANGAN_WU)
        } else if (dz == DiZhi.DIZHI_YOU) {
            tgArray = arrayOf(TianGan.TIANGAN_XIN)
        } else if (dz == DiZhi.DIZHI_XU) {
            tgArray = arrayOf(TianGan.TIANGAN_WU, TianGan.TIANGAN_XIN, TianGan.TIANGAN_DING)
        } else if (dz == DiZhi.DIZHI_HAI) {
            tgArray = arrayOf(TianGan.TIANGAN_REN, TianGan.TIANGAN_JIA)
        }
        return tgArray
    }

    @Composable
    fun getCangganLabel(tgArray: Array<TianGan>): String {
        val builder = StringBuilder()
        tgArray.forEach {
            builder.append(stringResource(tianganStrMap.get(it)!!))
        }
        return builder.toString()
    }

    fun isDayunDirectionForward(
        gender: String,
        yearDz: DiZhi
    ): Boolean {
        var ret = true
        if (((gender == MALE) && isYangDizhi(yearDz)) || ((gender == FEMALE) && isYinDizhi(yearDz))) {
            return true
        }

        if (((gender == MALE) && isYinDizhi(yearDz)) || ((gender == FEMALE) && isYangDizhi(yearDz))) {
            return false
        }
        return ret
    }

    @Composable
    fun getDayunLabelList(
        gender: String,
        yearDz: DiZhi,
        monthBase: Int,
        startIndex: Int,
        endIndex: Int,
        dayunStartYear: Int,
        yearOffet: Int,
        birthDateYear: Int,
        birthDateMonth: Int,
        birthDateDay: Int
    ): String {
        val builder = StringBuilder()
        var base = monthBase
        var dayTG = 0
        var dayDZ = 0
        var tg: TianGan = TianGan.TIANGAN_JIA
        var dz: DiZhi = DiZhi.DIZHI_ZI

        var yearUnit = stringResource(R.string.age_unit)

        //forward
        if (isDayunDirectionForward(gender, yearDz)) {
//        if (((gender == MALE) && isYangDizhi(yearDz)) || ((gender == FEMALE) && isYinDizhi(yearDz))) {
            for (i in startIndex..endIndex) {
                base = monthBase + i
                base = base % 60

                dayTG = base % 10
                dayDZ = base % 12
                tg = tgLookupMap[dayTG]!!
                dz = dzLookupMap.get(dayDZ)!!
                builder.append(getTianGanLabel(tg))
                builder.append(getDizhiLabel(dz))
                builder.append(" ")
                builder.append(dayunStartYear + (i - 1) * 10)
                builder.append("-")
                builder.append((i - 1) * 10 + yearOffet)
                builder.append(yearUnit)
                builder.append("\n")
            }
        }
        //backward
        if (!(isDayunDirectionForward(gender, yearDz))) {
//        if (((gender == MALE) && isYinDizhi(yearDz)) || ((gender == FEMALE) && isYangDizhi(yearDz))) {
            for (i in startIndex..endIndex) {
                base = monthBase - i
                if (base < 0) {
                    base = base + 60
                }
                base = base % 60

                dayTG = base % 10
                dayDZ = base % 12
                tg = tgLookupMap[dayTG]!!
                dz = dzLookupMap.get(dayDZ)!!
                builder.append(getTianGanLabel(tg))
                builder.append(getDizhiLabel(dz))
                builder.append(dayunStartYear + (i - 1) * 10)
                builder.append("-")
                builder.append((i - 1) * 10 + yearOffet)
                builder.append(yearUnit)

                builder.append("\n")
            }
        }

        return builder.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertMillisToLocalDate(millis: Long): ZonedDateTime {
        // Interpret the milliseconds as the start of the day in UTC, then convert to Los Angeles time
        val utcDateAtStartOfDay = Instant.ofEpochMilli(millis)
            .atZone(ZoneOffset.UTC)
            .toLocalDate()

        // Convert to the same instant in Local time zone
        val localDate = utcDateAtStartOfDay.atStartOfDay(ZoneId.systemDefault())
        return localDate
    }

    fun getJieqiByMonth(calendar: Calendar, solarYear: Int, solarMonth: Int): Date {
        var index = (solarMonth - 1) * 2
        return getSolarTermCalendar(calendar, solarYear, index)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDayunStartDays(
        solarYear: Int,
        solarMonth: Int,
        solarDay: Int,
        dayunForward: Boolean,
        hour: Int,
        minute: Int
    ): Int {
        var ret = 0
        println("solarYear=$solarYear  solarMonth=$solarMonth  solarDay=$solarDay")
        val calendar: Calendar = makeUTCCalendar()!!

        var year = solarYear
        var month = solarMonth
        var preJieqi: ZonedDateTime = convertMillisToLocalDate(calendar.timeInMillis)
        var nextJieqi: ZonedDateTime = convertMillisToLocalDate(calendar.timeInMillis)
        var tmpUTC = UTC(calendar, solarYear, solarMonth - 1, solarDay, hour, 1, 1)
        var birthDate: ZonedDateTime = convertMillisToLocalDate(tmpUTC)
        var lastDayOfMonth = calendar.getMaximum(Calendar.DAY_OF_MONTH)
        println("birthDate lastDayOfMonth= $lastDayOfMonth")

        var curJieqi: ZonedDateTime = getLocalDate(calendar, solarYear, solarMonth)
        var curJieqiMaxDay = calendar.getMaximum(Calendar.DAY_OF_MONTH)
        println("curJieqiMaxDay= $curJieqiMaxDay")

        println("curJieqi= $curJieqi")
        println("birthDate= $birthDate")

        if (dayunForward) {
            //go to next month Jieqi
            if (birthDate.compareTo(curJieqi) > 0) {
                if (solarMonth == 12) {
                    year = solarYear + 1
                    month = 1
                    nextJieqi = getLocalDate(calendar, year, month)
                } else {
                    month = solarMonth + 1
                    nextJieqi = getLocalDate(calendar, year, month)
                }
                println("year=$year  month=$month  nextJieqi=$nextJieqi")

                ret = nextJieqi.dayOfMonth + (lastDayOfMonth - birthDate.dayOfMonth + 1)

                println("   ret= $ret")
            } else if (birthDate.compareTo(curJieqi) < 0) {
                println("   curJieqi.dayOfMonth= $curJieqi.dayOfMonth  , birthDate.dayOfMonth=$birthDate.dayOfMonth")
                ret = curJieqi.dayOfMonth - birthDate.dayOfMonth + 1
                return ret
            } else {
                return 0
            }
        } else {
            //go to previous month Jieqi

            println("[previous month] birthDate=$birthDate  month=$month  curJieqi=$curJieqi")
            if (birthDate.compareTo(curJieqi) > 0) {
                ret = birthDate.dayOfMonth - curJieqi.dayOfMonth + 1
                return ret
            } else if (birthDate.compareTo(curJieqi) < 0) {
                if (solarMonth == 1) {
                    year = solarYear - 1
                    month = 12
                    preJieqi = getLocalDate(calendar, year, month)
                } else {
                    month = solarMonth - 1
                    preJieqi = getLocalDate(calendar, year, month)
                }
                month = month - 1
                tmpUTC = UTC(calendar, year, month, solarDay, hour, 1, 1)
                lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
                println("[previous month] preJieqi=$preJieqi  lastDayOfMonth=$lastDayOfMonth")
//                val endOfMonth = preJieqi.toLocalDate().with(TemporalAdjusters.lastDayOfMonth())
                ret = (lastDayOfMonth - preJieqi.dayOfMonth + 1) + birthDate.dayOfMonth
                return ret
            } else {
                return 0
            }
        }

        return ret
    }

    fun getDayunStartOffset(dayunDays: Int): Int {
        var ret = 0
        var offset = dayunDays / 3
        var remains = dayunDays % 3
        if (remains == 2) {
            offset = offset + 1
        }
        return offset
    }

    fun getDayunStartYear(year: Int, dayunDays: Int): Int {
        var ret = 0
        var dyStartYear = year
        var offset = dayunDays / 3
        var remains = dayunDays % 3
        if (remains == 2) {
            offset = offset + 1
        }
        dyStartYear = dyStartYear + offset

        println("year =$year  dyStartYear=$dyStartYear ")
        return dyStartYear
    }

    @Composable
    fun getDayunTaohuaResult(
        baziInfo: BaziInfo,
        taohuaMap: Map<DiZhi, DiZhi>
    ): String {
        val builder = StringBuilder()
        var isTaohua: Boolean = false
        var base = baziInfo.monthBase
        var dayTG = 0
        var dayDZ = 0
        var tg: TianGan = TianGan.TIANGAN_JIA
        var dz: DiZhi = DiZhi.DIZHI_ZI

        var yearUnit = stringResource(R.string.age_unit)
        var dayunStartYear =
            BaziUtil().getDayunStartYear(baziInfo.birthDateYear, baziInfo.dayunDays)
        var yearOffet = BaziUtil().getDayunStartOffset(baziInfo.dayunDays)

        var dayunTaohuaLabel = stringResource(R.string.app_taohua_dayun)
        var toLabel = stringResource(R.string.app_to)
//        builder.append(dayunTaohuaLabel).append("\n")

        if (baziInfo.dayunForward) {
            for (i in 1..12) {
                base = baziInfo.monthBase + i
                base = base % 60

                dayTG = base % 10
                dayDZ = base % 12
                tg = tgLookupMap[dayTG]!!
                dz = dzLookupMap.get(dayDZ)!!
                if (taohuaMap.containsKey(dz)) {
                    //found dayun taohua
                    builder.append(getTianGanLabel(tg))
                    builder.append(getDizhiLabel(dz))
                    builder.append(" ")
                    builder.append(dayunStartYear + (i - 1) * 10)
                    builder.append("  ")
                    builder.append((i - 1) * 10 + yearOffet)
                    builder.append(yearUnit)
                    builder.append(toLabel)
                    builder.append((i - 1) * 10 + yearOffet + 9)
                    builder.append(yearUnit)
                    builder.append("\n")
                }
            }
        } else {
            for (i in 1..12) {
                base = baziInfo.monthBase - i
                if (base < 0) {
                    base = base + 60
                }
                base = base % 60

                dayTG = base % 10
                dayDZ = base % 12
                tg = tgLookupMap[dayTG]!!
                dz = dzLookupMap.get(dayDZ)!!
//                builder.append(getTianGanLabel(tg))
//                builder.append(getDizhiLabel(dz))
                if (taohuaMap.containsKey(dz)) {
                    //found dayun taohua
                    builder.append(getTianGanLabel(tg))
                    builder.append(getDizhiLabel(dz))
                    builder.append(" ")
                    builder.append(dayunStartYear + (i - 1) * 10)
                    builder.append("  ")
                    builder.append((i - 1) * 10 + yearOffet)
                    builder.append(yearUnit)
                    builder.append(toLabel)
                    builder.append((i - 1) * 10 + yearOffet + 9)
                    builder.append(yearUnit)
                    builder.append("\n")
                }
            }
        }
        return builder.toString()
    }

    @Composable
    fun getBirthDateLabel(baziInfo: BaziInfo): String {
        val calendarStr = stringResource(R.string.app_calendar)
        var yearLabel = stringResource(R.string.app_label_year)
        var monthLabel = stringResource(R.string.app_label_month)
        var dayLabel = stringResource(R.string.app_label_day)
        var hourLabel = stringResource(R.string.app_label_hour)

        var genderStr = MALE
        if (baziInfo.gender == MALE) {
            genderStr = stringResource(R.string.app_bazi_male)
        } else {
            genderStr = stringResource(R.string.app_bazi_female)
        }
        val birthDateStr =
            "$calendarStr ${baziInfo.birthDateYear}$yearLabel ${baziInfo.birthDateMonth}$monthLabel ${baziInfo.birthDateDay}$dayLabel ${baziInfo.birthHour}$hourLabel  $genderStr"

        return birthDateStr
    }

    @Composable
    fun getTitleLable(baziInfo: BaziInfo): String {
        var genderStr = ""
        if (baziInfo.gender == MALE) {
            genderStr = stringResource(R.string.msg_male)
        } else {
            genderStr = stringResource(R.string.msg_female)
        }
        return genderStr
    }

    fun getNextTG(tg : TianGan) : TianGan{
        var nextTg = TianGan.TIANGAN_JIA

        if(tg == TianGan.TIANGAN_JIA){
            nextTg = TianGan.TIANGAN_YI
        }else if(tg == TianGan.TIANGAN_YI){
            nextTg = TianGan.TIANGAN_BING
        }else if(tg == TianGan.TIANGAN_BING){
            nextTg = TianGan.TIANGAN_DING
        }else if(tg == TianGan.TIANGAN_DING){
            nextTg = TianGan.TIANGAN_WU
        }else if(tg == TianGan.TIANGAN_WU){
            nextTg = TianGan.TIANGAN_JI
        }else if(tg == TianGan.TIANGAN_JI){
            nextTg = TianGan.TIANGAN_GENG
        }else if(tg == TianGan.TIANGAN_GENG){
            nextTg = TianGan.TIANGAN_XIN
        }else if(tg == TianGan.TIANGAN_XIN){
            nextTg = TianGan.TIANGAN_REN
        }else if(tg == TianGan.TIANGAN_REN){
            nextTg = TianGan.TIANGAN_GUI
        }else if(tg == TianGan.TIANGAN_GUI){
            nextTg = TianGan.TIANGAN_JIA
        }

        return nextTg
    }

    fun getHourTG(dayTG : TianGan, hour: Int) : TianGan{
        var hourTg = TianGan.TIANGAN_JIA
        var hourDZSeq = getHourDZInt(hour)
        if(dayTG == TianGan.TIANGAN_JIA || dayTG == TianGan.TIANGAN_JI){
            hourTg = TianGan.TIANGAN_JIA
        }
        if(dayTG == TianGan.TIANGAN_YI || dayTG == TianGan.TIANGAN_GENG){
            hourTg = TianGan.TIANGAN_BING
        }
        if(dayTG == TianGan.TIANGAN_BING || dayTG == TianGan.TIANGAN_XIN){
            hourTg = TianGan.TIANGAN_WU
        }
        if(dayTG == TianGan.TIANGAN_DING || dayTG == TianGan.TIANGAN_REN){
            hourTg = TianGan.TIANGAN_GENG
        }
        if(dayTG == TianGan.TIANGAN_WU || dayTG == TianGan.TIANGAN_GUI){
            hourTg = TianGan.TIANGAN_REN
        }

        for(i in 2..hourDZSeq){
            hourTg = getNextTG(hourTg)
        }

        println("Calcuate hour Tiangan, dayTG =$dayTG  hour=$hour ")
        return hourTg
    }

    fun isLady(baziInfo: BaziInfo) : Boolean{
        if (baziInfo.gender == FEMALE) {
            return true
        }
        return false
    }

    fun isTianKeDiChongOneway(tg1 : TianGan, dz1 : DiZhi, tg2 : TianGan, dz2 : DiZhi) : Boolean{
        var ret = false

        //6 Jia
        if(tg1 == TianGan.TIANGAN_JIA){
            if(dz1 == DiZhi.DIZHI_ZI){
                if(tg2 == TianGan.TIANGAN_GENG && dz2 == DiZhi.DIZHI_WU){
                    return true
                }
            }
            if(dz1 == DiZhi.DIZHI_XU){
                if(tg2 == TianGan.TIANGAN_GENG && dz2 == DiZhi.DIZHI_CHEN){
                    return true
                }
            }
            if(dz1 == DiZhi.DIZHI_SHEN){
                if(tg2 == TianGan.TIANGAN_GENG && dz2 == DiZhi.DIZHI_YIN){
                    return true
                }
            }
            if(dz1 == DiZhi.DIZHI_WU){
                if(tg2 == TianGan.TIANGAN_GENG && dz2 == DiZhi.DIZHI_ZI){
                    return true
                }
            }
            if(dz1 == DiZhi.DIZHI_CHEN){
                if(tg2 == TianGan.TIANGAN_GENG && dz2 == DiZhi.DIZHI_XU){
                    return true
                }
            }
            if(dz1 == DiZhi.DIZHI_YIN){
                if(tg2 == TianGan.TIANGAN_GENG && dz2 == DiZhi.DIZHI_SHEN){
                    return true
                }
            }
        }

        //6 Yi
        if(tg1 == TianGan.TIANGAN_YI) {
            if (dz1 == DiZhi.DIZHI_CHOU) {
                if (tg2 == TianGan.TIANGAN_XIN && dz2 == DiZhi.DIZHI_WEI) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_HAI) {
                if (tg2 == TianGan.TIANGAN_XIN && dz2 == DiZhi.DIZHI_SI) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_YOU) {
                if (tg2 == TianGan.TIANGAN_XIN && dz2 == DiZhi.DIZHI_MOU) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_WEI) {
                if (tg2 == TianGan.TIANGAN_XIN && dz2 == DiZhi.DIZHI_CHOU) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_SI) {
                if (tg2 == TianGan.TIANGAN_XIN && dz2 == DiZhi.DIZHI_HAI) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_MOU) {
                if (tg2 == TianGan.TIANGAN_XIN && dz2 == DiZhi.DIZHI_YOU) {
                    return true
                }
            }
        }

        //6 Bing
        if(tg1 == TianGan.TIANGAN_BING) {
            if (dz1 == DiZhi.DIZHI_YIN) {
                if (tg2 == TianGan.TIANGAN_GUI && dz2 == DiZhi.DIZHI_YOU) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_ZI) {
                if (tg2 == TianGan.TIANGAN_REN && dz2 == DiZhi.DIZHI_WU) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_XU) {
                if (tg2 == TianGan.TIANGAN_REN && dz2 == DiZhi.DIZHI_CHEN) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_SHEN) {
                if (tg2 == TianGan.TIANGAN_REN && dz2 == DiZhi.DIZHI_YIN) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_WU) {
                if (tg2 == TianGan.TIANGAN_REN && dz2 == DiZhi.DIZHI_ZI) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_CHEN) {
                if (tg2 == TianGan.TIANGAN_REN && dz2 == DiZhi.DIZHI_XU) {
                    return true
                }
            }
        }

        //6 Ding
        if(tg1 == TianGan.TIANGAN_DING) {
            if (dz1 == DiZhi.DIZHI_MOU) {
                if (tg2 == TianGan.TIANGAN_REN && dz2 == DiZhi.DIZHI_SHEN) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_CHOU) {
                if (tg2 == TianGan.TIANGAN_GUI && dz2 == DiZhi.DIZHI_WEI) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_HAI) {
                if (tg2 == TianGan.TIANGAN_GUI && dz2 == DiZhi.DIZHI_SI) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_YOU) {
                if (tg2 == TianGan.TIANGAN_GUI && dz2 == DiZhi.DIZHI_MOU) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_WEI) {
                if (tg2 == TianGan.TIANGAN_GUI && dz2 == DiZhi.DIZHI_CHOU) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_SI) {
                if (tg2 == TianGan.TIANGAN_GUI && dz2 == DiZhi.DIZHI_HAI) {
                    return true
                }
            }
        }

        //6 Wu
        if(tg1 == TianGan.TIANGAN_WU) {
            if (dz1 == DiZhi.DIZHI_CHEN) {
                if (tg2 == TianGan.TIANGAN_JIA && dz2 == DiZhi.DIZHI_XU) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_YIN) {
                if (tg2 == TianGan.TIANGAN_JIA && dz2 == DiZhi.DIZHI_SHEN) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_ZI) {
                if (tg2 == TianGan.TIANGAN_JIA && dz2 == DiZhi.DIZHI_WU) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_XU) {
                if (tg2 == TianGan.TIANGAN_JIA && dz2 == DiZhi.DIZHI_CHEN) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_SHEN) {
                if (tg2 == TianGan.TIANGAN_JIA && dz2 == DiZhi.DIZHI_YIN) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_WU) {
                if (tg2 == TianGan.TIANGAN_JIA && dz2 == DiZhi.DIZHI_ZI) {
                    return true
                }
            }
        }

        //6 Ji
        if(tg1 == TianGan.TIANGAN_JI) {
            if (dz1 == DiZhi.DIZHI_SI) {
                if (tg2 == TianGan.TIANGAN_YI && dz2 == DiZhi.DIZHI_HAI) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_MOU) {
                if (tg2 == TianGan.TIANGAN_YI && dz2 == DiZhi.DIZHI_YOU) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_CHOU) {
                if (tg2 == TianGan.TIANGAN_YI && dz2 == DiZhi.DIZHI_WEI) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_HAI) {
                if (tg2 == TianGan.TIANGAN_YI && dz2 == DiZhi.DIZHI_SI) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_YOU) {
                if (tg2 == TianGan.TIANGAN_YI && dz2 == DiZhi.DIZHI_MOU) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_WEI) {
                if (tg2 == TianGan.TIANGAN_YI && dz2 == DiZhi.DIZHI_CHOU) {
                    return true
                }
            }
        }

        //6 Geng
        if(tg1 == TianGan.TIANGAN_GENG) {
            if (dz1 == DiZhi.DIZHI_WU) {
                if (tg2 == TianGan.TIANGAN_BING && dz2 == DiZhi.DIZHI_ZI) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_CHEN) {
                if (tg2 == TianGan.TIANGAN_BING && dz2 == DiZhi.DIZHI_XU) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_YIN) {
                if (tg2 == TianGan.TIANGAN_BING && dz2 == DiZhi.DIZHI_SHEN) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_ZI) {
                if (tg2 == TianGan.TIANGAN_BING && dz2 == DiZhi.DIZHI_WU) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_XU) {
                if (tg2 == TianGan.TIANGAN_BING && dz2 == DiZhi.DIZHI_CHEN) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_SHEN) {
                if (tg2 == TianGan.TIANGAN_DING && dz2 == DiZhi.DIZHI_MOU) {
                    return true
                }
            }
        }

        //6 Xin
        if(tg1 == TianGan.TIANGAN_XIN) {
            if (dz1 == DiZhi.DIZHI_WEI) {
                if (tg2 == TianGan.TIANGAN_DING && dz2 == DiZhi.DIZHI_CHOU) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_SI) {
                if (tg2 == TianGan.TIANGAN_DING && dz2 == DiZhi.DIZHI_HAI) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_MOU) {
                if (tg2 == TianGan.TIANGAN_DING && dz2 == DiZhi.DIZHI_YOU) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_CHOU) {
                if (tg2 == TianGan.TIANGAN_DING && dz2 == DiZhi.DIZHI_WEI) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_HAI) {
                if (tg2 == TianGan.TIANGAN_DING && dz2 == DiZhi.DIZHI_SI) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_YOU) {
                if (tg2 == TianGan.TIANGAN_BING && dz2 == DiZhi.DIZHI_YIN) {
                    return true
                }
            }
        }

        //6 Ren
        if(tg1 == TianGan.TIANGAN_REN) {
            if (dz1 == DiZhi.DIZHI_SHEN) {
                if (tg2 == TianGan.TIANGAN_WU && dz2 == DiZhi.DIZHI_YIN) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_WU) {
                if (tg2 == TianGan.TIANGAN_WU && dz2 == DiZhi.DIZHI_ZI) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_CHEN) {
                if (tg2 == TianGan.TIANGAN_WU && dz2 == DiZhi.DIZHI_XU) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_YIN) {
                if (tg2 == TianGan.TIANGAN_WU && dz2 == DiZhi.DIZHI_SHEN) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_ZI) {
                if (tg2 == TianGan.TIANGAN_WU && dz2 == DiZhi.DIZHI_WU) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_XU) {
                if (tg2 == TianGan.TIANGAN_WU && dz2 == DiZhi.DIZHI_CHEN) {
                    return true
                }
            }
        }

        //6 Gui
        if(tg1 == TianGan.TIANGAN_GUI) {
            if (dz1 == DiZhi.DIZHI_YOU) {
                if (tg2 == TianGan.TIANGAN_JI && dz2 == DiZhi.DIZHI_MOU) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_WEI) {
                if (tg2 == TianGan.TIANGAN_JI && dz2 == DiZhi.DIZHI_CHOU) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_SI) {
                if (tg2 == TianGan.TIANGAN_JI && dz2 == DiZhi.DIZHI_HAI) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_MOU) {
                if (tg2 == TianGan.TIANGAN_JI && dz2 == DiZhi.DIZHI_YOU) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_CHOU) {
                if (tg2 == TianGan.TIANGAN_JI && dz2 == DiZhi.DIZHI_WEI) {
                    return true
                }
            }
            if (dz1 == DiZhi.DIZHI_HAI) {
                if (tg2 == TianGan.TIANGAN_JI && dz2 == DiZhi.DIZHI_SI) {
                    return true
                }
            }
        }
        return ret
    }

    fun isTianKeDiChong(tg1 : TianGan, dz1 : DiZhi, tg2 : TianGan, dz2 : DiZhi) : Boolean {
        var ret = false
        if(isTianKeDiChongOneway(tg1, dz1, tg2, dz2) || isTianKeDiChongOneway(tg2, dz2, tg1, dz1)){
            return true
        }
        return ret
    }

    fun getTianganFromBase(baseNum : Int) : TianGan{
        var tg = TianGan.TIANGAN_JIA
        var base = baseNum
        var dayunTG = 0
        if (base < 60) {
            base = base + 60
        }
        base = base % 60

        dayunTG = base % 10
        tg = tgLookupMap.get(dayunTG)!!
        return tg
    }

    fun getDizhiFromBase(baseNum : Int) : DiZhi{
        var dz = DiZhi.DIZHI_ZI
        var base = baseNum
        var dayunDZ = 0
        if (base < 0) {
            base = base + 60
        }
        base = base % 60
        dayunDZ = base % 12
        dz = BaziUtil().dzLookupMap.get(dayunDZ)!!
        return dz
    }

}


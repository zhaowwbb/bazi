package com.rick.bazi.util

import android.os.Build
import androidx.annotation.RequiresApi
import com.rick.bazi.data.BaziData
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.TianGan
import com.rick.bazi.ui.BaziViewModel
import java.util.Calendar

class BaziPaiPanUtil {

    @RequiresApi(Build.VERSION_CODES.O)
    fun paipan(
        year: Int,
        month: Int,
        day: Int,
        hour: Int,
        gender: String,
        baziModel: BaziViewModel,
        baziInfo: BaziInfo
    )  : BaziData {
//        println("Bazi Paipan: year=$year,month=$month,day=$day,hour=$hour ")
        var yearTG = 1
        var yearDZ = 1
        var monthTG = 1
        var monthDZ = 1
        var dayTG = 1
        var dayDZ = 1
        var hourTG = 1
        var hourDZ = 1
        var tg: TianGan = TianGan.TIANGAN_JIA
        var dz: DiZhi = DiZhi.DIZHI_ZI
        var tmp = 0

        val tgLookupMap: Map<Int, TianGan> = baziInfo.tgLookupMap
        val dzLookupMap: Map<Int, DiZhi> = baziInfo.dzLookupMap

        var data = BaziData(year, month, day, hour, gender)

        //calculate Year Tiangan & Dizhi
        var yearBase = BaziUtil().getCyclicalYearBase(year, month, day, hour)
        baziModel.setYearBase(yearBase)
        data.yearBase = yearBase

        yearTG = yearBase % 10
        tg = tgLookupMap[yearTG]!!
        baziModel.setYearTiangan(tg)
        yearDZ = yearBase % 12
        dz = dzLookupMap[yearDZ]!!
        baziModel.setYearDiZhi(dz)
        data.yearTiangan = tg
        data.yearDizhi = dz

        //calculate Month Tiangan & Dizhi
        var monthBase = BaziUtil().getCyclicalMonthBase(year, month, day, hour)
        baziModel.setMonthBase(monthBase)
        data.monthBase = monthBase
        monthTG = monthBase % 10
        tg = tgLookupMap[monthTG]!!
        baziModel.setMonthTiangan(tg)
        monthDZ = monthBase % 12
        dz = dzLookupMap.get(monthDZ)!!
        baziModel.setMonthDiZhi(dz)
        data.monthTiangan = tg
        data.monthDizhi = dz

        //calculate Day Tiangan & Dizhi
        var r = 0
        //23:00 to 23:59 is belong to the next day in lunar calendar
        if (hour == 23) {
            r = DateUtils().getDayTianganBase(year, month, day + 1)
        } else {
            r = DateUtils().getDayTianganBase(year, month, day)
        }

        val dayBase = r % 60
        baziModel.setDayBase(dayBase)
        data.dayBase = dayBase

        dayTG = dayBase % 10
        dayDZ = dayBase % 12
        tg = tgLookupMap[dayTG]!!
        dz = dzLookupMap.get(dayDZ)!!

        baziModel.setDayTiangan(tg)
        baziModel.setDayDiZhi(dz)
        data.dayTiangan = tg
        data.dayDizhi = dz

        //calculate Hour Tiangan & Dizhi
        var curDayTG: TianGan = tg
        dz = BaziUtil().getHourDZ(hour)
        tg = BaziUtil().getHourTG(curDayTG, hour)
        baziModel.setHourTiangan(tg)
        baziModel.setHourDiZhi(dz)
        data.hourTiangan = tg
        data.hourDizhi = dz
        baziModel.setBaziData(data)

        println("Bazi Paipan: year=$year,month=$month,day=$day,hour=$hour, Bazi data=$data")
        return data
    }
}
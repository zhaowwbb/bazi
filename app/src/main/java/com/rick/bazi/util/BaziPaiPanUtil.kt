package com.rick.bazi.util

import android.os.Build
import androidx.annotation.RequiresApi
import com.rick.bazi.data.BaziData
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.TianGan
import com.rick.bazi.data.TianGanDiZhi
import com.rick.bazi.ui.BaziViewModel
import com.tyme.solar.SolarTerm
import com.tyme.solar.SolarTime
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
        println("Bazi Paipan: year=$year,month=$month,day=$day,hour=$hour ")
        var dayTG = 1
        var dayDZ = 1
        var tg: TianGan = TianGan.TIANGAN_JIA
        var dz: DiZhi = DiZhi.DIZHI_ZI

//        for( i in 1982..2048){
//            calculateBazi(i, month, day, hour, baziInfo.baziData)
//        }
        var data = BaziData(year, month, day, hour, gender)
        //calculate year and month
        calculateBazi(year, month, day, hour, data)

        val tgLookupMap: Map<Int, TianGan> = baziInfo.tgLookupMap
        val dzLookupMap: Map<Int, DiZhi> = baziInfo.dzLookupMap

        tg = data.yearTiangan
        dz = data.yearDizhi
        baziModel.setYearTiangan(tg)
        baziModel.setYearDiZhi(dz)

        tg = data.monthTiangan
        dz = data.monthDizhi
        baziModel.setMonthTiangan(tg)
        baziModel.setMonthDiZhi(dz)

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

        GeJuUtil().getGJ(data)

        baziModel.setBaziData(data)

        println("Bazi Paipan: year=$year,month=$month,day=$day,hour=$hour, Bazi data=$data")
        return data
    }

    fun testTyme(){
        var termName = "立春"
        val year = 2025
        val term = SolarTerm.fromName(year, termName)

        // 获取精确到秒的时刻
        val solarTime: SolarTime = term.julianDay.solarTime
        println("$year 年 $termName 节气在：$solarTime")

        // 示例2：获取某一年的全部24个节气
        val allTerms = SolarTerm.NAMES.map { name ->
            val t = SolarTerm.fromName(year, name)
            val time = t.julianDay.solarTime
            "$name: $time"
        }
        allTerms.forEach { println(it) }
    }

    fun calculateBazi(
        year: Int,
        month: Int,
        day: Int,
        hour: Int,
        data : BaziData){
        //184年 - 黄巾之乱的农民起义口号是“苍天已死，黄天当立，岁在甲子，天下大吉”。
        val startYear = 184
        var yearBase = (year - startYear) % 60
        //map start from 1
        yearBase = yearBase + 1

        var ownerSolarTime: SolarTime = SolarTime(year, month, day, hour, 30, 30)

        var termName = "立春"
        var term = SolarTerm.fromName(year, termName)

        // 获取精确到秒的时刻
        val lichunTime: SolarTime = term.julianDay.solarTime
        println("$year 年 $termName 节气在：$lichunTime, ownerSolarTime:$ownerSolarTime")
        if(ownerSolarTime.isBefore(lichunTime)){
            //belong to previous
            yearBase = yearBase - 1
            if(yearBase == 0){
                yearBase = 60
            }
        }

        val gz = BaziUtil().jiazi60Map[yearBase]!!

        val tg = gz.tg
        val dz = gz.dz

        //year
        data.yearTiangan =  tg
        data.yearDizhi = dz

        termName = "小寒"
        term = SolarTerm.fromName(year, termName)
        val xiaohanTime: SolarTime = term.julianDay.solarTime
        term = SolarTerm.fromName(year + 1, termName)
        val xiaohanNextTime: SolarTime = term.julianDay.solarTime

        termName = "惊蛰"
        term = SolarTerm.fromName(year, termName)
        val jingzheTime: SolarTime = term.julianDay.solarTime

        termName = "清明"
        term = SolarTerm.fromName(year, termName)
        val qingmingTime: SolarTime = term.julianDay.solarTime

        termName = "立夏"
        term = SolarTerm.fromName(year, termName)
        val lixiaTime: SolarTime = term.julianDay.solarTime

        termName = "芒种"
        term = SolarTerm.fromName(year, termName)
        val mangzhongTime: SolarTime = term.julianDay.solarTime

        termName = "小暑"
        term = SolarTerm.fromName(year, termName)
        val xiaoshuTime: SolarTime = term.julianDay.solarTime

        termName = "立秋"
        term = SolarTerm.fromName(year, termName)
        val liqiuTime: SolarTime = term.julianDay.solarTime

        termName = "白露"
        term = SolarTerm.fromName(year, termName)
        val bailuTime: SolarTime = term.julianDay.solarTime

        termName = "寒露"
        term = SolarTerm.fromName(year, termName)
        val hanluTime: SolarTime = term.julianDay.solarTime

        termName = "立冬"
        term = SolarTerm.fromName(year, termName)
        val lidongTime: SolarTime = term.julianDay.solarTime

        termName = "大雪"
        term = SolarTerm.fromName(year, termName)
        val daxueTime: SolarTime = term.julianDay.solarTime
        term = SolarTerm.fromName(year - 1, termName)
        val daxuePrevTime: SolarTime = term.julianDay.solarTime

        DaYunUtil().calculateDaYunStartSeconds(qingmingTime, lidongTime, ownerSolarTime, data)
//        println("daYunStartSeconds=${data.daYunStartSeconds}")

        //month
        //1 month
        if(ownerSolarTime.isAfter(lichunTime) && ownerSolarTime.isBefore(jingzheTime)){
            data.monthDizhi = DiZhi.DIZHI_YIN
            DaYunUtil().calculateDaYunStartSeconds(lichunTime, jingzheTime, ownerSolarTime, data)
        }
        //2 month
        if(ownerSolarTime.isAfter(jingzheTime) && ownerSolarTime.isBefore(qingmingTime)){
            data.monthDizhi = DiZhi.DIZHI_MOU
            DaYunUtil().calculateDaYunStartSeconds(jingzheTime, qingmingTime, ownerSolarTime, data)
        }
        //3
        if(ownerSolarTime.isAfter(qingmingTime) && ownerSolarTime.isBefore(lixiaTime)){
            data.monthDizhi = DiZhi.DIZHI_CHEN
            DaYunUtil().calculateDaYunStartSeconds(qingmingTime, lixiaTime, ownerSolarTime, data)
        }
        //4
        if(ownerSolarTime.isAfter(lixiaTime) && ownerSolarTime.isBefore(mangzhongTime)){
            data.monthDizhi = DiZhi.DIZHI_SI
            DaYunUtil().calculateDaYunStartSeconds(lixiaTime, mangzhongTime, ownerSolarTime, data)
        }
        //5
        if(ownerSolarTime.isAfter(mangzhongTime) && ownerSolarTime.isBefore(xiaoshuTime)){
            data.monthDizhi = DiZhi.DIZHI_WU
            DaYunUtil().calculateDaYunStartSeconds(mangzhongTime, xiaoshuTime, ownerSolarTime, data)
        }
        //6
        if(ownerSolarTime.isAfter(xiaoshuTime) && ownerSolarTime.isBefore(liqiuTime)){
            data.monthDizhi = DiZhi.DIZHI_WEI
            DaYunUtil().calculateDaYunStartSeconds(xiaoshuTime, liqiuTime, ownerSolarTime, data)
        }
        //7
        if(ownerSolarTime.isAfter(liqiuTime) && ownerSolarTime.isBefore(bailuTime)){
            data.monthDizhi = DiZhi.DIZHI_SHEN
            DaYunUtil().calculateDaYunStartSeconds(liqiuTime, bailuTime, ownerSolarTime, data)
        }
        //8
        if(ownerSolarTime.isAfter(bailuTime) && ownerSolarTime.isBefore(hanluTime)){
            data.monthDizhi = DiZhi.DIZHI_YOU
            DaYunUtil().calculateDaYunStartSeconds(bailuTime, hanluTime, ownerSolarTime, data)
        }
        //9
        if(ownerSolarTime.isAfter(hanluTime) && ownerSolarTime.isBefore(lidongTime)){
            data.monthDizhi = DiZhi.DIZHI_XU
            DaYunUtil().calculateDaYunStartSeconds(hanluTime, lidongTime, ownerSolarTime, data)
        }
        //10
        if(ownerSolarTime.isAfter(lidongTime) && ownerSolarTime.isBefore(daxueTime)){
            data.monthDizhi = DiZhi.DIZHI_HAI
            DaYunUtil().calculateDaYunStartSeconds(lidongTime, daxueTime, ownerSolarTime, data)
        }
        //11
        if(ownerSolarTime.isAfter(daxueTime)){
            data.monthDizhi = DiZhi.DIZHI_ZI
            DaYunUtil().calculateDaYunStartSeconds(daxueTime, xiaohanNextTime, ownerSolarTime, data)
        }
        //12
        if(ownerSolarTime.isAfter(xiaohanTime) && ownerSolarTime.isBefore(lichunTime)){
            data.monthDizhi = DiZhi.DIZHI_CHOU
            DaYunUtil().calculateDaYunStartSeconds(xiaohanTime, lichunTime, ownerSolarTime, data)
        }

        if(ownerSolarTime.isBefore(xiaohanTime)){
            data.monthDizhi = DiZhi.DIZHI_ZI
            DaYunUtil().calculateDaYunStartSeconds(daxuePrevTime, xiaohanTime, ownerSolarTime, data)
        }
        calculateMonthTianGan(data)

        println("year=$year, tg=$tg, dz=$dz, yearBase=$yearBase ")
    }

    fun calculateMonthTianGan(data : BaziData){
        var tgdz = TianGanDiZhi(TianGan.TIANGAN_BING, DiZhi.DIZHI_YIN)
        //“五虎遁”口诀
        if(data.yearTiangan == TianGan.TIANGAN_JIA || data.yearTiangan == TianGan.TIANGAN_JI){
            tgdz = BaziUtil().jiaJi5TigerMap.get(data.monthDizhi)!!
            data.monthTiangan = tgdz.tg
        }
        if(data.yearTiangan == TianGan.TIANGAN_YI || data.yearTiangan == TianGan.TIANGAN_GENG){
            tgdz = BaziUtil().yiGeng5TigerMap.get(data.monthDizhi)!!
            data.monthTiangan = tgdz.tg
        }
        if(data.yearTiangan == TianGan.TIANGAN_BING || data.yearTiangan == TianGan.TIANGAN_XIN){
            tgdz = BaziUtil().bingXin5TigerMap.get(data.monthDizhi)!!
            data.monthTiangan = tgdz.tg
        }
        if(data.yearTiangan == TianGan.TIANGAN_DING || data.yearTiangan == TianGan.TIANGAN_REN){
            tgdz = BaziUtil().dingRen5TigerMap.get(data.monthDizhi)!!
            data.monthTiangan = tgdz.tg
        }
        if(data.yearTiangan == TianGan.TIANGAN_WU || data.yearTiangan == TianGan.TIANGAN_GUI){
            tgdz = BaziUtil().wuGui5TigerMap.get(data.monthDizhi)!!
            data.monthTiangan = tgdz.tg
        }
    }

}
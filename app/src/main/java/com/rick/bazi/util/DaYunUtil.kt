package com.rick.bazi.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rick.bazi.R
import com.rick.bazi.data.BaziData
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.data.BaziYongShen
import com.rick.bazi.data.ColumnPosition
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.MALE
import com.rick.bazi.data.ShiShen
import com.rick.bazi.data.TianGan
import com.rick.bazi.data.TianGanDiZhi
import com.rick.bazi.data.WuXing
import com.rick.bazi.ui.BaziViewModel
import com.tyme.solar.SolarTime

class DaYunUtil {
    val SPACE = "    "


    data class DayunRecord(
        val dyLabel: String,
        val dyAgeLabel: String
    )


    fun isDaYunForward(data: BaziData) : Boolean{
        if (data.gender == MALE) {
            if(TianGanUtil().isTianGanYang(data.yearTiangan)){
                return true
            }else{
                return false
            }
        }else{
            if(TianGanUtil().isTianGanYang(data.yearTiangan)){
                return false
            }else{
                return true
            }
        }
    }

    //index start from 1
    fun getDaYun(index : Int, data: BaziData) : TianGanDiZhi {
        var tg = data.monthTiangan
        var dz = data.monthDizhi
        if(isDaYunForward(data)){
            for(i in 0 until index){
                tg = TianGanUtil().getNextTianGan(tg)
                dz = DiZhiUtil().getNextDiZhi(dz)
            }
        }else{
            for(i in 0 until index){
                tg = TianGanUtil().getPrevTianGan(tg)
                dz = DiZhiUtil().getPrevDiZhi(dz)
            }
        }

        return TianGanDiZhi(tg, dz)
    }

    fun calculateDaYunStartSeconds(startJieQi : SolarTime, endJieQi : SolarTime, ownerTime : SolarTime, data: BaziData){
        var seconds : Int = 0
        if(isDaYunForward(data)){
            seconds = endJieQi.subtract(ownerTime)
        }else{
            seconds = ownerTime.subtract(startJieQi)
        }
        data.daYunStartSeconds = seconds

        var days = data.daYunStartSeconds/86400
        var remainSeconds = data.daYunStartSeconds%86400
        var hours = remainSeconds/3600
        var remainTime = remainSeconds%3600
        var year = days/3
        var month = (days%3)*4
        var day = hours*5
        day = day + (remainTime/720)
        if(day > 30){
            month = month + day/30
        }
        day = day%30

        var remainHours = remainTime%720
        var hour = remainHours/30

        data.daYunStartYear = year
        data.daYunStartMonth = month
        data.daYunStartDay = day

        var startYear = data.birthDateYear + data.daYunStartYear
        var startMonth = data.birthDateMonth + data.daYunStartMonth
        if(startMonth >= 13){
            startYear+= 1
            startMonth -= 12
        }
        data.daYunFirstYear = startYear
        data.daYunFirstMonth = startMonth

        println("daYunStartSeconds=${data.daYunStartSeconds}")
    }

    @Composable
    fun getDaYunStartTimeString(data: BaziData) : String{
        val sb = StringBuilder()

        sb.append(stringResource(R.string.app_after_birth))
        sb.append(data.daYunStartYear).append(stringResource(R.string.app_label_year))
//        sb.append(" ")
        sb.append(data.daYunStartMonth).append(stringResource(R.string.app_label_month))
//        sb.append(" ")
//        sb.append(day).append(stringResource(R.string.app_label_day))
//        sb.append(" ")
//        sb.append(hour).append(stringResource(R.string.app_label_hour))

//        var year = data.birthDateYear + data.daYunStartYear
//        var month = data.birthDateMonth + data.daYunStartMonth
//        if(month >= 13){
//            year+= 1
//            month = month%13
//        }

        sb.append("(")
        sb.append(data.daYunFirstYear).append(stringResource(R.string.app_label_year))
        sb.append(data.daYunFirstMonth).append(stringResource(R.string.app_label_month))
        sb.append(")")
        sb.append(stringResource(R.string.app_start_dayun))

        return sb.toString()
    }

    @Composable
    fun getDayunList(baziInfo: BaziInfo) : MutableList<DayunRecord> {
        var records: MutableList<DayunRecord> = arrayListOf()
        var tgdz : TianGanDiZhi
        var labelstr = ""
        var builder = StringBuilder()
        var ageStr = ""
        for (i in 1..8) {
            tgdz = getDaYun(i, baziInfo.baziData)
        builder = StringBuilder()
        builder.append(BaziUtil().getTianGanLabel(tgdz.tg))
        builder.append(BaziUtil().getDizhiLabel(tgdz.dz))
        builder.append("(")
        builder.append(stringResource(R.string.bazi_tkdc_dayun))
        builder.append(")")
        builder.append(" ")

            labelstr = builder.toString()

            builder = StringBuilder()

//        builder.append((i - 1) * 10 + yearOffet + 1)
//        builder.append(stringResource(R.string.app_to))
//        builder.append((i - 1) * 10 + yearOffet + 10)
//        builder.append(stringResource(R.string.age_unit))

            labelstr = builder.toString()

                    val r = DayunRecord(labelstr, ageStr)
        records.add(r)
        }
        return records
    }

//    @Composable
//    fun getTianGanCheckString(srcTG : TianGan, dstTG : TianGan, columnPos : ColumnPosition,data: BaziData) : String{
//        val sb = StringBuilder()
//        var isJiShen = YongShenUtil().isTianGanJiShen(dstTG, data)
//        var isYongShen = YongShenUtil().isTianGanYongShen(dstTG, data)
//        var isXiShen = YongShenUtil().isTianGanXiShen(dstTG, data)
//
//        var isDyJiShen = YongShenUtil().isTianGanJiShen(srcTG, data)
//        var isDyYongShen = YongShenUtil().isTianGanYongShen(srcTG, data)
//        var isDyXiShen = YongShenUtil().isTianGanXiShen(srcTG, data)
//
//        var xjStr = ""
//        var dyXjStr = ""
//
//        if(isJiShen){
//            xjStr = "[" + stringResource(R.string.app_bazi_ji_shen) + "]"
//        }
//        if(isYongShen){
//            xjStr = "[" + stringResource(R.string.app_bazi_yong_shen) + "]"
//        }
//        if(isXiShen){
//            xjStr = "[" + stringResource(R.string.app_bazi_xi_shen) + "]"
//        }
//
//        if(isDyJiShen){
//            dyXjStr = "[" + stringResource(R.string.app_bazi_ji_shen) + "]"
//        }
//        if(isDyYongShen){
//            dyXjStr = "[" + stringResource(R.string.app_bazi_yong_shen) + "]"
//        }
//        if(isDyXiShen){
//            dyXjStr = "[" + stringResource(R.string.app_bazi_xi_shen) + "]"
//        }
//
//        var columnStr = ""
//        if(columnPos == ColumnPosition.COLUMN_YEAR){
//            columnStr = stringResource(R.string.app_bazi_tiangan_year_label)
//        }
//        if(columnPos == ColumnPosition.COLUMN_MONTH){
//            columnStr = stringResource(R.string.app_bazi_tiangan_month_label)
//        }
//        if(columnPos == ColumnPosition.COLUMN_DAY){
//            columnStr = stringResource(R.string.bazi_owner)
//        }
//        if(columnPos == ColumnPosition.COLUMN_HOUR){
//            columnStr = stringResource(R.string.app_bazi_tiangan_hour_label)
//        }
//
//        if(TianGanUtil().isTianGanSheng(srcTG, dstTG) || srcTG == dstTG){
//            sb.append(stringResource(R.string.app_bazi_dayun))
//            sb.append(WuXingUtil().getTianGanWuXingText(srcTG))
//            sb.append(dyXjStr)
//
//            sb.append(stringResource(R.string.tiangan_sheng_label))
//            sb.append(columnStr)
//            if(columnPos != ColumnPosition.COLUMN_DAY){
//                sb.append(xjStr)
//            }
//            sb.append(WuXingUtil().getTianGanWuXingText(dstTG))
//
//            if(columnPos != ColumnPosition.COLUMN_DAY){
//                if(isJiShen){
//                    data.daYunWeight-= 2
//                    sb.append("(").append("-2").append(")")
//                }
//                if(isYongShen){
//                    data.daYunWeight+= 4
//                    sb.append("(").append("+4").append(")")
//                }
//                if(isXiShen){
//                    data.daYunWeight+= 2
//                    sb.append("(").append("+2").append(")")
//                }
//            }
//
//            if(columnPos == ColumnPosition.COLUMN_DAY){
//                if(isDyJiShen){
//                    data.daYunWeight-= 2
//                    sb.append("(").append("-2").append(")")
//                }
//                if(isDyYongShen){
//                    data.daYunWeight+= 4
//                    sb.append("(").append("+4").append(")")
//                }
//                if(isDyXiShen){
//                    data.daYunWeight+= 2
//                    sb.append("(").append("+2").append(")")
//                }
//            }
//        }
//        if(TianGanUtil().isTianGanKe(srcTG, dstTG)){
//            sb.append(stringResource(R.string.app_bazi_dayun))
//            sb.append(WuXingUtil().getTianGanWuXingText(srcTG))
//            sb.append(dyXjStr)
//
//            sb.append(stringResource(R.string.tiangan_ke_label))
//            sb.append(columnStr)
//            if(columnPos != ColumnPosition.COLUMN_DAY){
//                sb.append(xjStr)
//            }
//            sb.append(WuXingUtil().getTianGanWuXingText(dstTG))
//            if(columnPos != ColumnPosition.COLUMN_DAY) {
//                if (isJiShen) {
//                    data.daYunWeight -= 2
//                    sb.append("(").append("-2").append(")")
//                }
//                if (isYongShen) {
//                    data.daYunWeight += 4
//                    sb.append("(").append("+4").append(")")
//                }
//                if (isXiShen) {
//                    data.daYunWeight += 2
//                    sb.append("(").append("+2").append(")")
//                }
//            }
//            if(columnPos == ColumnPosition.COLUMN_DAY){
//                if(isDyJiShen){
//                    data.daYunWeight-= 2
//                    sb.append("(").append("-2").append(")")
//                }
//                if(isDyYongShen){
//                    data.daYunWeight+= 4
//                    sb.append("(").append("+4").append(")")
//                }
//                if(isDyXiShen){
//                    data.daYunWeight+= 2
//                    sb.append("(").append("+2").append(")")
//                }
//            }
//        }
//        if(sb.isNotEmpty()){
//            sb.append("\n")
//        }
//        return sb.toString()
//    }

    @Composable
    fun geDaYunTianGanCheckString(dyTG : TianGan, data: BaziData): String {
        val sb = StringBuilder()
        var isJiShen = YongShenUtil().isTianGanJiShen(dyTG, data)
        var isYongShen = YongShenUtil().isTianGanYongShen(dyTG, data)
        var isXiShen = YongShenUtil().isTianGanXiShen(dyTG, data)
        var xjStr = ""
        sb.append(SPACE)

        if(isJiShen){
            xjStr = "[" + stringResource(R.string.app_bazi_ji_shen) + "]"
        }else if(isYongShen){
            xjStr = "[" + stringResource(R.string.app_bazi_yong_shen) + "]"
        }else if(isXiShen){
            xjStr = "[" + stringResource(R.string.app_bazi_xi_shen) + "]"
        }else{
            xjStr = "[" + stringResource(R.string.app_bazi_xian_shen) + "]"
        }

        sb.append(stringResource(R.string.dayun_tiangan_label))
        sb.append(WuXingUtil().getTianGanWuXingString(dyTG))
        sb.append(xjStr)
        sb.append(" ")
        sb.append(stringResource(R.string.app_bazi_weight_label))

        if(isJiShen){
            data.daYunWeight-= 2
            sb.append("(").append("-2").append(")")
        }else if(isYongShen){
            data.daYunWeight+= 4
            sb.append("(").append("+4").append(")")
        }else if(isXiShen){
            data.daYunWeight+= 2
            sb.append("(").append("+2").append(")")
        }else{
            sb.append("(").append("+0").append(")")
        }
        sb.append("\n")

        return sb.toString()
    }

    @Composable
    fun getDaYunDiZhiCheckString(dyDZ: DiZhi, data: BaziData): String {
        val sb = StringBuilder()
        var tg = DiZhiUtil().getCanggan(dyDZ).get(0)
        var isJiShen = YongShenUtil().isTianGanJiShen(tg, data)
        var isYongShen = YongShenUtil().isTianGanYongShen(tg, data)
        var isXiShen = YongShenUtil().isTianGanXiShen(tg, data)
        var xjStr = ""
        sb.append(SPACE)

        if(isJiShen){
            xjStr = "[" + stringResource(R.string.app_bazi_ji_shen) + "]"
        }else if(isYongShen){
            xjStr = "[" + stringResource(R.string.app_bazi_yong_shen) + "]"
        }else if(isXiShen){
            xjStr = "[" + stringResource(R.string.app_bazi_xi_shen) + "]"
        }else{
            xjStr = "[" + stringResource(R.string.app_bazi_xian_shen) + "]"
        }

        sb.append(stringResource(R.string.dayun_dizhi_label))
        sb.append(WuXingUtil().getDiZhiWuXingString(dyDZ))
        sb.append(xjStr)
        sb.append(" ")
        sb.append(stringResource(R.string.app_bazi_weight_label))

        if(isJiShen){
            data.daYunWeight-= 2
            sb.append("(").append("-2").append(")")
        }else if(isYongShen){
            data.daYunWeight+= 4
            sb.append("(").append("+4").append(")")
        }else if(isXiShen){
            data.daYunWeight+= 2
            sb.append("(").append("+2").append(")")
        }else{
            sb.append("(").append("+0").append(")")
        }
        sb.append("\n")

        return sb.toString()
    }

    @Composable
    fun getDaYunTianGan6HeString(dyTG : TianGan, data: BaziData): String {
        val sb = StringBuilder()
        var wx = WuXing.WUXING_MU
        //year
        if(TianGanUtil().isTianGanHe(dyTG, data.yearTiangan)){
            wx = TianGanUtil().getTianGanHeWuXing(dyTG)
            sb.append(SPACE)
            sb.append(stringResource(R.string.dayun_tiangan_label))
            sb.append(WuXingUtil().getTianGanWuXingString(dyTG))
            sb.append(" ")

            sb.append(stringResource(R.string.app_bazi_tiangan_year_label))
            sb.append(WuXingUtil().getTianGanWuXingString(data.yearTiangan))
            sb.append(" ")

            sb.append(stringResource(R.string.app_bazi_tiangan_he_label))
            sb.append("[")
            sb.append(WuXingUtil().getWuXingText(wx))
            sb.append("]")
            if(YongShenUtil().isWuXingXiYongShen(wx, data)){
                data.daYunWeight+= 1
                sb.append(stringResource(R.string.app_bazi_xiyong_shen))
                sb.append(" ")
                sb.append(stringResource(R.string.app_bazi_weight_label))
                sb.append("(").append("+1").append(")")
            }
            if(YongShenUtil().isWuXingJiShen(wx, data)){
                data.daYunWeight-= 1
                sb.append(stringResource(R.string.app_bazi_xiyong_shen))
                sb.append(" ")
                sb.append(stringResource(R.string.app_bazi_weight_label))
                sb.append("(").append("-1").append(")")
            }
            sb.append("\n")
        }
        //month
        if(TianGanUtil().isTianGanHe(dyTG, data.monthTiangan)){
            wx = TianGanUtil().getTianGanHeWuXing(dyTG)
            sb.append(SPACE)
            sb.append(stringResource(R.string.dayun_tiangan_label))
            sb.append(WuXingUtil().getTianGanWuXingString(dyTG))
            sb.append(" ")

            sb.append(stringResource(R.string.app_bazi_tiangan_month_label))
            sb.append(WuXingUtil().getTianGanWuXingString(data.monthTiangan))
            sb.append(" ")

            sb.append(stringResource(R.string.app_bazi_tiangan_he_label))
            sb.append("[")
            sb.append(WuXingUtil().getWuXingText(wx))
            sb.append("]")
            if(YongShenUtil().isWuXingXiYongShen(wx, data)){
                data.daYunWeight+= 1
                sb.append(stringResource(R.string.app_bazi_xiyong_shen))
                sb.append(" ")
                sb.append(stringResource(R.string.app_bazi_weight_label))
                sb.append("(").append("+1").append(")")
            }
            if(YongShenUtil().isWuXingJiShen(wx, data)){
                data.daYunWeight-= 1
                sb.append(stringResource(R.string.app_bazi_ji_shen))
                sb.append(" ")
                sb.append(stringResource(R.string.app_bazi_weight_label))
                sb.append("(").append("-1").append(")")
            }
            sb.append("\n")
        }
        //hour
        if(TianGanUtil().isTianGanHe(dyTG, data.hourTiangan)){
            wx = TianGanUtil().getTianGanHeWuXing(dyTG)
            sb.append(SPACE)
            sb.append(stringResource(R.string.dayun_tiangan_label))
            sb.append(WuXingUtil().getTianGanWuXingString(dyTG))
            sb.append(" ")

            sb.append(stringResource(R.string.app_bazi_tiangan_hour_label))
            sb.append(WuXingUtil().getTianGanWuXingString(data.hourTiangan))
            sb.append(" ")

            sb.append(stringResource(R.string.app_bazi_tiangan_he_label))
            sb.append("[")
            sb.append(WuXingUtil().getWuXingText(wx))
            sb.append("]")
            if(YongShenUtil().isWuXingXiYongShen(wx, data)){
                data.daYunWeight+= 1
                sb.append(stringResource(R.string.app_bazi_xiyong_shen))
                sb.append(" ")
                sb.append(stringResource(R.string.app_bazi_weight_label))
                sb.append("(").append("+1").append(")")
            }
            if(YongShenUtil().isWuXingJiShen(wx, data)){
                data.daYunWeight-= 1
                sb.append(stringResource(R.string.app_bazi_ji_shen))
                sb.append(" ")
                sb.append(stringResource(R.string.app_bazi_weight_label))
                sb.append("(").append("-1").append(")")
            }
            sb.append("\n")
        }

        return sb.toString()
    }

    @Composable
    fun getBaziColumnString(columnPosition: ColumnPosition, isTG : Boolean) : String{
        var sb = StringBuilder()
        var gzStr = ""
        if(isTG){
            gzStr = stringResource(R.string.app_label_gan)
        }else{
            gzStr = stringResource(R.string.app_label_zhi)
        }
        if(columnPosition == ColumnPosition.COLUMN_YEAR){
            sb.append(stringResource(R.string.app_label_year))
        }
        if(columnPosition == ColumnPosition.COLUMN_MONTH){
            sb.append(stringResource(R.string.app_label_month))
        }
        if(columnPosition == ColumnPosition.COLUMN_DAY){
            sb.append(stringResource(R.string.app_label_day))
        }
        if(columnPosition == ColumnPosition.COLUMN_HOUR){
            sb.append(stringResource(R.string.app_label_hour))
        }
        sb.append(gzStr)

        return sb.toString()
    }

    @Composable
    fun getDaYunTianGanTouDiZhiString(dyTG : TianGan, data: BaziData): String {
        val sb = StringBuilder()
        var ys : BaziYongShen
        var isYongShenTouChu = false
        if(data.baziYongShenList.size > 0){
            //1st yong shen
            ys = data.baziYongShenList[0]
            if(!ys.isTianGan){
                if(ys.yongshenTG == dyTG){
                    sb.append(SPACE)
                    sb.append(getBaziColumnString(ys.columnPosition, false))
                    sb.append(WuXingUtil().getDiZhiWuXingString(ys.dz))
                    sb.append("[")
                    sb.append(stringResource(R.string.app_bazi_yong_shen))
                    sb.append("]")

                    sb.append(stringResource(R.string.dizhi_touchu_label))
                    sb.append(stringResource(R.string.dayun_tiangan_label))
//                    sb.append(stringResource(R.string.tiangan))
                    sb.append("[")
                    sb.append(WuXingUtil().getTianGanWuXingString(dyTG))
                    sb.append("]")

                    data.daYunWeight+= 4
                    sb.append(" ")
                    sb.append(stringResource(R.string.app_bazi_weight_label))
                    sb.append("(").append("+4").append(")")
                    sb.append("\n")
                    isYongShenTouChu = true
                }
            }
            if(!isYongShenTouChu){
                for(ys in data.baziYongShenList){
                    if(!ys.isTianGan){
                        if(ys.yongshenTG == dyTG){
                            sb.append(SPACE)
                            sb.append(getBaziColumnString(ys.columnPosition, false))
                            sb.append(WuXingUtil().getDiZhiWuXingString(ys.dz))
                            sb.append("[")
                            sb.append(stringResource(R.string.app_bazi_xi_shen))
                            sb.append("]")
                            sb.append(stringResource(R.string.bazi_canggan))
                            sb.append(stringResource(R.string.dizhi_touchu_label))
//                            sb.append(stringResource(R.string.app_bazi_dayun))
                            sb.append(stringResource(R.string.dayun_tiangan_label))
                            sb.append("[")
                            sb.append(WuXingUtil().getTianGanWuXingString(dyTG))
                            sb.append("]")

                            data.daYunWeight+= 2
                            sb.append(" ")
                            sb.append(stringResource(R.string.app_bazi_weight_label))
                            sb.append("(").append("+2").append(")")
                            sb.append("\n")
                            break
                        }
                    }
                }
            }
        }

        return sb.toString()
    }

    @Composable
    fun getDaYunDiZhiTouchuCheckString(dyTG : TianGan, dyDZ : DiZhi, data: BaziData): String {
        val sb = StringBuilder()
        var tg = TianGan.TIANGAN_JIA
        var tgArray : Array<TianGan>
        var ys: BaziYongShen
        var ss = ShiShen.SHISHEN_SHI_SHEN
        var isYongShen = false
        var foundXiShen = false
        var xsTg = TianGan.TIANGAN_JIA
        tgArray = DiZhiUtil().getCanggan(dyDZ)
        if(tgArray.size == 2){
            tg = tgArray[1]
            ss = ShiShenUtil().getShiShen(tg, data.dayTiangan)
            for(s in data.allYongShenList){
                if(ss == s){
                    foundXiShen = true
                    xsTg = tg
                    break
                }
            }
        }
        if(tgArray.size == 3){
            if(!foundXiShen){
                tg = tgArray[2]
                ss = ShiShenUtil().getShiShen(tg, data.dayTiangan)
                for(s in data.allYongShenList){
                    if(ss == s){
                        foundXiShen = true
                        xsTg = tg
                        break
                    }
                }
            }
        }

        if(foundXiShen) {
            //only handle touchu
            var isBaziTou = DiZhiUtil().isDiZhiTouTianGan(xsTg, data)
            if (isBaziTou || xsTg == dyTG) {
                sb.append(SPACE)
                sb.append(stringResource(R.string.dayun_dizhi_label))
//                sb.append(getBaziColumnString(ys.columnPosition, ys.isCangGan))
                sb.append(WuXingUtil().getDiZhiWuXingString(dyDZ))
                sb.append(stringResource(R.string.bazi_canggan))
                sb.append("[")
                sb.append(WuXingUtil().getTianGanWuXingText(xsTg))
                sb.append("]")
                sb.append("[")
                sb.append(stringResource(R.string.app_bazi_xi_shen))
                sb.append("]")

                sb.append(stringResource(R.string.dizhi_touchu_label))
                sb.append(stringResource(R.string.tiangan))
                sb.append(" ")
                sb.append(stringResource(R.string.app_bazi_weight_label))

                if(isBaziTou){
                    data.daYunWeight += 1
                    sb.append("(").append("+1").append(")")
                }else{
                    data.daYunWeight += 2
                    sb.append("(").append("+2").append(")")
                }
                sb.append("\n")
            }
        }

        return sb.toString()
    }

    @Composable
    fun getDaYunChongString(dyDZ : DiZhi, dz : DiZhi, columnPosition: ColumnPosition, data: BaziData): String {
        val sb = StringBuilder()
        if(DiZhiUtil().isDiZhiChong(dyDZ, dz)){
            sb.append(SPACE)
            sb.append(stringResource(R.string.dayun_dizhi_label))
            sb.append(WuXingUtil().getDiZhiWuXingString(dyDZ))
            sb.append(stringResource(R.string.dizhi_chong_label))
            sb.append(getBaziColumnString(columnPosition, false))
            sb.append(WuXingUtil().getDiZhiWuXingString(dz))
//            sb.append(stringResource(R.string.app_bazi_weight_label))

            if(YongShenUtil().isDiZhiYongShen(dz, data)){
                sb.append("[")
                sb.append(stringResource(R.string.app_bazi_yong_shen))
                sb.append("]")
                sb.append(" ")
                sb.append(stringResource(R.string.app_bazi_weight_label))

                data.daYunWeight -= 4
                sb.append("(").append("+4").append(")")
            }else if(YongShenUtil().isDiZhiXiShen(dz, data)){
                sb.append("[")
                sb.append(stringResource(R.string.app_bazi_xi_shen))
                sb.append("]")
                sb.append(" ")
                sb.append(stringResource(R.string.app_bazi_weight_label))

                data.daYunWeight -= 2
                sb.append("(").append("-2").append(")")
            }else if(YongShenUtil().isDiZhiJiShen(dz, data)){
                sb.append("[")
                sb.append(stringResource(R.string.app_bazi_ji_shen))
                sb.append("]")
                sb.append(" ")
                sb.append(stringResource(R.string.app_bazi_weight_label))

                data.daYunWeight += 2
                sb.append("(").append("+2").append(")")
            }
            sb.append("\n")
        }
        return sb.toString()
    }

    @Composable
    fun getDaYunDiZhiChongString(dyDZ : DiZhi, data: BaziData): String {
        val sb = StringBuilder()
        sb.append(getDaYunChongString(dyDZ, data.yearDizhi, ColumnPosition.COLUMN_YEAR, data))
        sb.append(getDaYunChongString(dyDZ, data.monthDizhi, ColumnPosition.COLUMN_MONTH, data))
        sb.append(getDaYunChongString(dyDZ, data.dayDizhi, ColumnPosition.COLUMN_DAY, data))
        sb.append(getDaYunChongString(dyDZ, data.hourDizhi, ColumnPosition.COLUMN_HOUR, data))
        return sb.toString()
    }

    @Composable
    fun analyzeDaYunString(dyTG : TianGan, dyDZ : DiZhi, data: BaziData): String {
        val sb = StringBuilder()
        data.daYunWeight = 0

        sb.append(geDaYunTianGanCheckString(dyTG, data))
        sb.append(getDaYunDiZhiCheckString(dyDZ, data))
        sb.append(getDaYunTianGan6HeString(dyTG, data))
        sb.append(getDaYunTianGanTouDiZhiString(dyTG, data))

        sb.append(getDaYunDiZhiTouchuCheckString(dyTG, dyDZ, data))
        sb.append(getDaYunDiZhiChongString(dyDZ, data))
        sb.append(SPACE)
        sb.append(stringResource(R.string.app_bazi_weight_label))
        sb.append("(")
        sb.append(data.daYunWeight)
        sb.append(")")

        return sb.toString()
    }

    @Composable
    fun getDaYunSummaryString(data: BaziData): String {
        var str = ""
        var startIndex = 1
        var endIndex = 12
        var tg = TianGan.TIANGAN_JIA
        var dz = DiZhi.DIZHI_ZI
        var builder = StringBuilder()
        var tgdz : TianGanDiZhi

        for (i in startIndex..endIndex) {
            tgdz = DaYunUtil().getDaYun(i, data)
            tg = tgdz.tg
            dz = tgdz.dz
            builder.append(BaziUtil().getTianGanLabel(tg))
            builder.append(BaziUtil().getDizhiLabel(dz))
            builder.append("(")
            builder.append(data.daYunFirstYear + (i - 1) * 10)
            builder.append(")")
            builder.append("\n")
            builder.append(analyzeDaYunString(tg, dz, data))
            builder.append("\n")
        }
        return builder.toString()
    }
}
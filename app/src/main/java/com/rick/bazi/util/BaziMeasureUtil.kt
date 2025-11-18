package com.rick.bazi.util

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rick.bazi.R
import com.rick.bazi.data.BaziData
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.data.BaziStrength
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.ShiShen
import com.rick.bazi.data.TianGan
import com.rick.bazi.data.WuXing
import com.rick.bazi.data.BaziGeJu
import com.rick.bazi.ui.BaziViewModel

class BaziMeasureUtil {

    @Composable
    fun getHelpElementString(data : BaziData) : String{
        var sb = StringBuilder()
        var bijieStr = ""
        var yinStr = ""
        var yinCount = 0
        var biJieCount = 0

        yinCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_ZHENG_YIN) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_YIN)
        biJieCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_BI_JIAN) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_JIE_CAI)

        yinStr = ShiShenUtil().getBaziShiShenStatString(data, ShiShen.SHISHEN_ZHENG_YIN) + ShiShenUtil().getBaziShiShenStatString(data, ShiShen.SHISHEN_PIAN_YIN)
        bijieStr = ShiShenUtil().getBaziShiShenStatString(data, ShiShen.SHISHEN_BI_JIAN) + ShiShenUtil().getBaziShiShenStatString(data, ShiShen.SHISHEN_JIE_CAI)

        sb.append("\n")
        sb.append("    " + stringResource(R.string.app_bazi_have) + yinCount + stringResource(R.string.app_bazi_yin_label) + stringResource(R.string.app_bazi_shengshen))
        sb.append("  " + yinStr)
        sb.append("\n")
        sb.append("    " + stringResource(R.string.app_bazi_have) + biJieCount + stringResource(R.string.app_bazi_bj_label) + stringResource(R.string.app_bazi_bangfu))
        sb.append(" " + bijieStr)

        return sb.toString()
    }

    @Composable
    fun getKeXieHaoString(data : BaziData) : String{
        var sb = StringBuilder()
        var guanshaStr = ""
        var shishangStr = ""
        var caiStr = ""
        var shiShangCount = 0
        var guanShaCount = 0
        var caiCount = 0

        shiShangCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_SHI_SHEN) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_SHANG_GUAN)
        guanShaCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_ZHENG_GUAN) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_QI_SHA)
        caiCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_ZHENG_CAI) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_CAI)

        guanshaStr = ShiShenUtil().getBaziShiShenStatString(data, ShiShen.SHISHEN_ZHENG_GUAN) + ShiShenUtil().getBaziShiShenStatString(data, ShiShen.SHISHEN_QI_SHA)
        shishangStr = ShiShenUtil().getBaziShiShenStatString(data, ShiShen.SHISHEN_SHI_SHEN) + ShiShenUtil().getBaziShiShenStatString(data, ShiShen.SHISHEN_SHANG_GUAN)
        caiStr = ShiShenUtil().getBaziShiShenStatString(data, ShiShen.SHISHEN_ZHENG_CAI) + ShiShenUtil().getBaziShiShenStatString(data, ShiShen.SHISHEN_PIAN_CAI)

        sb.append("\n")
        sb.append("    " + stringResource(R.string.app_bazi_have) + guanShaCount + stringResource(R.string.app_bazi_gs_label) + stringResource(R.string.app_bazi_ke))
        sb.append(" ").append(guanshaStr).append("\n")
        sb.append("    " + stringResource(R.string.app_bazi_have) + shiShangCount + stringResource(R.string.app_bazi_ss_label) + stringResource(R.string.app_bazi_xie))
        sb.append(" ").append(shishangStr).append("\n")
        sb.append("    " + stringResource(R.string.app_bazi_have) + caiCount + stringResource(R.string.app_bazi_cai_label) + stringResource(R.string.app_bazi_hao))
        sb.append("  ").append(caiStr).append("")

        return sb.toString()
    }

    @Composable
    fun analyzeBaziAndSaveStat(baziInfo: BaziInfo, baziModel: BaziViewModel) {
        Log.i("[Rick]", "Aanalyze Bazi And Save Stat");

        //shishen string
        ShiShenUtil().getBaziShiShengString(baziInfo, baziModel)

//        analyzeBaziHelpStar(baziInfo, baziModel)
//        analyzeBaziKeXieHao(baziInfo, baziModel)
        getBaziStrengthSummary(baziInfo, baziModel)
//        getXiJiSummary(baziInfo, baziModel)
//        getBaziGeJuSummary(baziInfo, baziModel)
        getBaziDayunSummary(baziInfo, baziModel)
    }

    @Composable
    fun getBaziDayunSummary(baziInfo: BaziInfo, baziModel: BaziViewModel): String {
        var str = ""
        var startIndex = 1
        var endIndex = 7
        var base = baziInfo.monthBase
        var tg = TianGan.TIANGAN_JIA
        var dz = DiZhi.DIZHI_ZI
        var builder = StringBuilder()

        var dayunForward: Boolean =
            BaziUtil().isDayunDirectionForward(baziInfo.gender, baziInfo.yearDizhi)
        var days = BaziUtil().getDayunStartDays(
            baziInfo.birthDateYear,
            baziInfo.birthDateMonth,
            baziInfo.birthDateDay,
            dayunForward,
            baziInfo.birthHour,
            baziInfo.birthMinute
        )
        var dayunStartYear = BaziUtil().getDayunStartYear(baziInfo.birthDateYear, days)

        var yearOffet = BaziUtil().getDayunStartOffset(days)

        var monthOffset = 0
        var yearOffset = days / 3
        var remains = days % 3
        if (remains == 2) {
            yearOffset = yearOffset + 1
            monthOffset = 8
        } else if (remains == 1) {
            monthOffset = 4
        }

        baziModel.setDayunForward(dayunForward)
        baziModel.setDayunDays(days)

        for (i in startIndex..endIndex) {
            if (dayunForward) {
                base = baziInfo.monthBase + i
            } else {
                base = baziInfo.monthBase - i
            }
            tg = BaziUtil().getTianganFromBase(base)
            dz = BaziUtil().getDizhiFromBase(base)
            builder = StringBuilder()
            builder.append(BaziUtil().getTianGanLabel(tg))
            builder.append(BaziUtil().getDizhiLabel(dz))
            builder.append("(")
            builder.append(stringResource(R.string.bazi_tkdc_dayun))
            builder.append(")")
            builder.append(" ")
            builder.append(dayunStartYear + (i - 1) * 10)
            builder.append(" ")
            builder.append(getDayunAnalysisResult(tg, dz, baziInfo.baziData))

            builder.append("\n")
            str = str + builder.toString()

        }

        baziModel.setBaziDayunSummary(str)
        return str
    }

    @Composable
    fun getDayunAnalysisResult(dyTg: TianGan, dyDz: DiZhi, data: BaziData): String {
        var str = ""
        var sb = StringBuilder()
        var weight = 0
        var tgWX = WuXingUtil().getTgWX(dyTg)
        var dzWX = WuXingUtil().getDiZhiWuxing(dyDz)
        var isJiShenExist = false

        if(isTianGanXiShen(dyTg, data)){
//            sb.append(stringResource(R.string.app_bazi_dayun))
            sb.append(stringResource(R.string.tiangan))
            sb.append("(" + stringResource(R.string.app_bazi_ji) + ")")
            weight = weight + 1
        }else if(isTianGanJiShen(dyTg, data)){
//            sb.append(stringResource(R.string.app_bazi_dayun))
            sb.append(stringResource(R.string.tiangan))
            sb.append("(" + stringResource(R.string.app_bazi_xiong) + ")")
            weight = weight - 1
            isJiShenExist = true
        }else{
//            weight = weight + 1
        }

        if(isDiZhiXiShen(dyDz, data)){
//            sb.append(stringResource(R.string.app_bazi_dayun))
            sb.append(stringResource(R.string.dizhi))
            sb.append("(" + stringResource(R.string.app_bazi_ji) + ")")
            weight = weight + 1
        }else if(isDiZhiJiShen(dyDz, data)){
//            sb.append(stringResource(R.string.app_bazi_dayun))
            sb.append(stringResource(R.string.dizhi))
            sb.append("(" + stringResource(R.string.app_bazi_xiong) + ")")
            weight = weight - 1
            isJiShenExist = true
        }else{
//            weight = weight + 1
        }

        if (weight >= 5) {
            str = stringResource(R.string.app_bazi_jixiong_1)
        } else if (weight == 2) {
            str = stringResource(R.string.app_bazi_jixiong_1)
        } else if (weight == 1) {
            str = stringResource(R.string.app_bazi_jixiong_2)
        }
        else if (weight == 0) {
            if(isJiShenExist == true){
                str = stringResource(R.string.app_bazi_jixiong_3)
            }else{
                str = stringResource(R.string.app_bazi_jixiong_0)
            }
        } else if (weight == -1) {
            str = stringResource(R.string.app_bazi_jixiong_4)
        } else if (weight == -2) {
            str = stringResource(R.string.app_bazi_jixiong_5)
        }
        sb.append(str)

        return sb.toString()
    }

    fun isTianGanJiShen(tg: TianGan, data: BaziData) : Boolean{
        var ret = false
        var tgSS = ShiShen.SHISHEN_BI_JIAN
        tgSS = ShiShenUtil().getShiShen(tg, data.dayTiangan)
        for(ss in data.jiShenList){
            if(ss == tgSS){
                println("Found TianGan JiShen=$ss")
                ret = true
                break
            }
        }
        return ret
    }

//    @Composable
    fun isDiZhiJiShen(dz : DiZhi, data: BaziData) : Boolean{
        var ret = false
//        var tgSS = ShiShen.SHISHEN_BI_JIAN
        var dzSS = ShiShenUtil().getShiShenFromDizhi(dz, data.dayTiangan)
        for(ss in data.jiShenList){
            if(ss == dzSS){
                println("Found DiZhi XiShen=$ss")
                ret = true
                break
            }
        }
        return ret
    }

    fun isDiZhiXiShen(dz : DiZhi, data: BaziData) : Boolean{
        var ret = false
//        var tgSS = ShiShen.SHISHEN_BI_JIAN
        var dzSS = ShiShenUtil().getShiShenFromDizhi(dz, data.dayTiangan)
        for(ss in data.xiShenList){
            if(ss == dzSS){
                println("Found DiZhi XiShen=$ss")
                ret = true
                break
            }
        }
        return ret
    }

    fun isTianGanXiShen(tg: TianGan, data: BaziData) : Boolean{
        var ret = false
        var tgSS = ShiShen.SHISHEN_BI_JIAN
        tgSS = ShiShenUtil().getShiShen(tg, data.dayTiangan)
        for(ss in data.xiShenList){
            if(ss == tgSS){
                println("Found TianGan XiShen=$ss")
                ret = true
                break
            }
        }
        return ret
    }

    @Composable
    fun getDayunCheckResult(tg: TianGan, dz: DiZhi, baziInfo: BaziInfo): String {
        var str = "xxx"
        var tgWX = WuXingUtil().getTgWX(tg)
        var dzWX = WuXingUtil().getDiZhiWuxing(dz)
        var weight = 0
        val xiyongShenList = baziInfo.xiyongShenList
        val jiShenList = baziInfo.jiShenList
        val tiaohouShenList = baziInfo.tiaohouShenList

        val mergedAndDistinctList = (xiyongShenList + tiaohouShenList).distinct()
        println("mergedAndDistinctList.size=" + mergedAndDistinctList.size)
        for (s in mergedAndDistinctList) {
            println("  " + s)
        }

        if (mergedAndDistinctList.size > 0) {
            for (s in mergedAndDistinctList) {
                if (s == tgWX) weight++
                if (s == dzWX) weight++
            }
        }
        println("weight=" + weight)
        println("jiShenList.size=" + jiShenList.size)
        for (s in jiShenList) {
            println("  " + s)
        }

        if (jiShenList.size > 0) {
            for (s in jiShenList) {
                if (mergedAndDistinctList.contains(s)) {
                    //skip
                } else {
                    if (s == tgWX) weight--
                    if (s == dzWX) weight--
                }
            }
        }
        println("weight=" + weight)

        if (weight == 2) {
            str = stringResource(R.string.app_bazi_jixiong_1)
        } else if (weight == 1) {
            str = stringResource(R.string.app_bazi_jixiong_2)
        } else if (weight == 0) {
            str = stringResource(R.string.app_bazi_jixiong_3)
        } else if (weight == -1) {
            str = stringResource(R.string.app_bazi_jixiong_4)
        } else if (weight == -2) {
            str = stringResource(R.string.app_bazi_jixiong_5)
        }

        return str
    }

    @Composable
    fun getBaziStrengthText(s: BaziStrength): String {
        var str = ""
        if (s == BaziStrength.BAZI_STRONG_SUPER) {
            str = stringResource(R.string.app_bazi_gj_congqian_desc)
        }
        if (s == BaziStrength.BAZI_STRONG) {
            str = stringResource(R.string.app_bazi_strong_2_desc)
        }
        if (s == BaziStrength.BAZI_STRONG_MILD) {
            str = stringResource(R.string.app_bazi_strong_1_desc)
        }
        if (s == BaziStrength.BAZI_BALANCED) {
            str = stringResource(R.string.app_bazi_balanced_desc)
        }
        if (s == BaziStrength.BAZI_WEAK_MILD) {
            str = stringResource(R.string.app_bazi_weak_1_desc)
        }
        if (s == BaziStrength.BAZI_WEAK) {
            str = stringResource(R.string.app_bazi_weak_2_desc)
        }
        if (s == BaziStrength.BAZI_WEAK_SUPER) {
            str = stringResource(R.string.app_bazi_gj_congruo_desc)
        }
        return str
    }

    @Composable
    fun getBaziStrengthSummary(baziInfo: BaziInfo, baziModel: BaziViewModel): String {
        var sb = StringBuilder()
        var bijieStr = ""
        var yinStr = ""
        var yinCount = 0
        var biJieCount = 0
        var shiShangCount = 0
        var guanShaCount = 0
        var caiCount = 0
        var data = baziInfo.baziData

        yinCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_ZHENG_YIN) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_YIN)
        biJieCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_BI_JIAN) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_JIE_CAI)
        shiShangCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_SHI_SHEN) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_SHANG_GUAN)
        guanShaCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_ZHENG_GUAN) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_QI_SHA)
        caiCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_ZHENG_CAI) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_CAI)

        var summary = ""
        var baziStrength = BaziStrength.BAZI_BALANCED
        var goodElementCount = biJieCount + yinCount
        var badElementCount = guanShaCount + shiShangCount + caiCount

        var elementDiffWeight = goodElementCount - badElementCount
        var danglingWeight = 0
        var dediWeight = 0
        var totalWeight = 0
        var isDangLing = WuXingUtil().isDangling(data.monthDizhi, data.dayTiangan)
        var idDeDi = WuXingUtil().isBaziDedi(data)
        if (isDangLing) {
            danglingWeight = 3
        } else {
            danglingWeight = -3
        }

        if (idDeDi) {
            dediWeight = 2
        } else {
            dediWeight = -2
        }

        totalWeight = danglingWeight + dediWeight + elementDiffWeight


        if (goodElementCount == 7) {
            baziStrength = BaziStrength.BAZI_STRONG_SUPER
        } else if (badElementCount == 7) {
            baziStrength = BaziStrength.BAZI_WEAK_SUPER
        } else if (totalWeight == 0) {
            baziStrength = BaziStrength.BAZI_BALANCED
        } else if (totalWeight > 3 && totalWeight < 5) {
            baziStrength = BaziStrength.BAZI_STRONG_MILD
        } else if (totalWeight >= 5) {
            baziStrength = BaziStrength.BAZI_STRONG
        } else if (totalWeight < 0 && totalWeight > -5) {
            baziStrength = BaziStrength.BAZI_WEAK_MILD
        } else if (totalWeight <= -5) {
            baziStrength = BaziStrength.BAZI_WEAK
        }

        summary =
            summary + stringResource(R.string.bazi_owner) + WuXingUtil().getTianGanWuXingText(baziInfo.baziData.dayTiangan)
        if (baziInfo.isDangLing) {
            summary = summary + stringResource(R.string.app_bazi_dangling_yes)
        } else {
            summary = summary + stringResource(R.string.app_bazi_dangling_no)
        }
        summary = summary + ","
        if (baziInfo.isDedi) {
            summary = summary + stringResource(R.string.app_bazi_dedi_yes)
        } else {
            summary = summary + stringResource(R.string.app_bazi_dedi_no)
        }
        summary = summary + ","
        if (elementDiffWeight > 0) {
            summary = summary + stringResource(R.string.app_bazi_deshi_yes)
        } else if (elementDiffWeight < 0) {
            summary = summary + stringResource(R.string.app_bazi_deshi_no)
        }
        summary = summary + ","
        summary = summary + getBaziStrengthText(baziStrength)

        baziModel.setBaziStrength(baziStrength)
        baziModel.setBaziStrengthSummary(summary)
        return summary
    }


    @Composable
    fun getBaziYongshenSummary(data: BaziData): String {
        var str = ""
        var summary = ""
        var sb = StringBuilder()
        var guanshaStr = ""
        var shishangStr = ""
        var caiStr = ""
//        var shiShangCount = 0
//        var guanShaCount = 0
//        var caiCount = 0
//
//        shiShangCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_SHI_SHEN) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_SHANG_GUAN)
//        guanShaCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_ZHENG_GUAN) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_QI_SHA)
//        caiCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_ZHENG_CAI) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_CAI)
//
//        guanshaStr = ShiShenUtil().getBaziShiShenStatString(data, ShiShen.SHISHEN_ZHENG_GUAN) + ShiShenUtil().getBaziShiShenStatString(data, ShiShen.SHISHEN_QI_SHA)
//        shishangStr = ShiShenUtil().getBaziShiShenStatString(data, ShiShen.SHISHEN_SHI_SHEN) + ShiShenUtil().getBaziShiShenStatString(data, ShiShen.SHISHEN_SHANG_GUAN)
//        caiStr = ShiShenUtil().getBaziShiShenStatString(data, ShiShen.SHISHEN_ZHENG_CAI) + ShiShenUtil().getBaziShiShenStatString(data, ShiShen.SHISHEN_PIAN_CAI)
        //check yongshen in Bazi
        for(ss in data.yongShenList){
            str = ShiShenUtil().getBaziShiShenStatString(data, ss)
            if(str.length > 0){
                sb.append(str)
            }
        }
        if(sb.length > 0){
            summary = sb.toString()
            return summary
        }

        //check xishen in Bazi
        for(ss in data.xiShenList){
            str = ShiShenUtil().getBaziShiShenStatString(data, ss)
            if(str.length > 0){
                sb.append(str)
            }
        }
        if(sb.length > 0){
            summary = sb.toString()
            return summary
        }
        //check tiaohou
        for(wx in data.tiaohouList){
            str = WuXingUtil().getBaziWuxingStatString(data, wx)
            if(str.length > 0){
                sb.append(str)
            }
        }
        summary = sb.toString()
        return summary
    }
}


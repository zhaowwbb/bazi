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

    val cangganLookupMap: Map<DiZhi, Array<TianGan>> = mapOf(
        DiZhi.DIZHI_ZI to arrayOf(TianGan.TIANGAN_GUI),
        DiZhi.DIZHI_CHOU to arrayOf(TianGan.TIANGAN_JI, TianGan.TIANGAN_GUI, TianGan.TIANGAN_XIN),
        DiZhi.DIZHI_YIN to arrayOf(TianGan.TIANGAN_JIA, TianGan.TIANGAN_BING, TianGan.TIANGAN_WU),
        DiZhi.DIZHI_MOU to arrayOf(TianGan.TIANGAN_YI),
        DiZhi.DIZHI_CHEN to arrayOf(TianGan.TIANGAN_WU, TianGan.TIANGAN_YI, TianGan.TIANGAN_GUI),
        DiZhi.DIZHI_SI to arrayOf(TianGan.TIANGAN_BING, TianGan.TIANGAN_GENG, TianGan.TIANGAN_WU),
        DiZhi.DIZHI_WU to arrayOf(TianGan.TIANGAN_DING, TianGan.TIANGAN_JI),
        DiZhi.DIZHI_WEI to arrayOf(TianGan.TIANGAN_JI, TianGan.TIANGAN_DING, TianGan.TIANGAN_YI),
        DiZhi.DIZHI_SHEN to arrayOf(TianGan.TIANGAN_GENG, TianGan.TIANGAN_REN, TianGan.TIANGAN_WU),
        DiZhi.DIZHI_YOU to arrayOf(TianGan.TIANGAN_XIN),
        DiZhi.DIZHI_XU to arrayOf(TianGan.TIANGAN_WU, TianGan.TIANGAN_XIN, TianGan.TIANGAN_DING),
        DiZhi.DIZHI_HAI to arrayOf(TianGan.TIANGAN_REN, TianGan.TIANGAN_JIA)
    )

    @Composable
    fun analyzeBaziHelpStar(baziInfo: BaziInfo, baziModel: BaziViewModel): String {
        var str = ""
        var helpNumber = 0
        var impedeNumber = 0
        var ss = ShiShen.SHISHEN_BI_JIAN

        var goodElementStr = ""
        var badElementStr = ""
        var tg = baziInfo.yearTiangan
        var dz = baziInfo.yearDizhi
        var yinCount = 0
        var bijieCount = 0
        var bijieStr = ""
        var yinStr = ""

        //check tiangan
        ss = baziInfo.yearTgShiShen
        tg = baziInfo.yearTiangan
        if (ShiShenUtil().isYin(ss)) {
            yinCount++
            yinStr = yinStr + getTianganStr(
                baziInfo,
                tg
            ) + WuXingUtil().getTianGanWuxingText(tg) + "(" + ShiShenUtil().getShiShenText(
                tg,
                baziInfo.dayTiangan
            ) + ") 、 "
        }
        if (ShiShenUtil().isBiJie(ss)) {
            bijieCount++
            bijieStr = bijieStr + getTianganStr(
                baziInfo,
                tg
            ) + WuXingUtil().getTianGanWuxingText(tg) + "(" + ShiShenUtil().getShiShenText(
                tg,
                baziInfo.dayTiangan
            ) + ") 、 "
        }

        ss = baziInfo.monthTgShiShen
        tg = baziInfo.monthTiangan
        if (ShiShenUtil().isYin(ss)) {
            yinCount++
            yinStr = yinStr + getTianganStr(
                baziInfo,
                tg
            ) + WuXingUtil().getTianGanWuxingText(tg) + "(" + ShiShenUtil().getShiShenText(
                tg,
                baziInfo.dayTiangan
            ) + ") 、 "
        }
        if (ShiShenUtil().isBiJie(ss)) {
            bijieCount++
            bijieStr = bijieStr + getTianganStr(
                baziInfo,
                tg
            ) + WuXingUtil().getTianGanWuxingText(tg) + "(" + ShiShenUtil().getShiShenText(
                tg,
                baziInfo.dayTiangan
            ) + ") 、 "
        }

        ss = baziInfo.hourTgShiShen
        tg = baziInfo.hourTiangan
        if (ShiShenUtil().isYin(ss)) {
            yinCount++
            yinStr = yinStr + getTianganStr(
                baziInfo,
                tg
            ) + WuXingUtil().getTianGanWuxingText(tg) + "(" + ShiShenUtil().getShiShenText(
                tg,
                baziInfo.dayTiangan
            ) + ") 、 "
        }
        if (ShiShenUtil().isBiJie(ss)) {
            bijieCount++
            bijieStr = bijieStr + getTianganStr(
                baziInfo,
                tg
            ) + WuXingUtil().getTianGanWuxingText(tg) + "(" + ShiShenUtil().getShiShenText(
                tg,
                baziInfo.dayTiangan
            ) + ") 、 "
        }

        //check dizhi
        ss = baziInfo.yearDzShiShen
        dz = baziInfo.yearDizhi
        if (ShiShenUtil().isYin(ss)) {
            yinCount++
            yinStr = yinStr + getDizhiStr(
                baziInfo,
                dz
            ) + WuXingUtil().getDiZhiWuxingText(dz) + "(" + ShiShenUtil().getDiZhiShiShenText(
                dz,
                baziInfo.dayTiangan
            ) + ") 、 "
        }
        if (ShiShenUtil().isBiJie(ss)) {
            bijieCount++
            bijieStr = bijieStr + getDizhiStr(
                baziInfo,
                dz
            ) + WuXingUtil().getDiZhiWuxingText(dz) + "(" + ShiShenUtil().getDiZhiShiShenText(
                dz,
                baziInfo.dayTiangan
            ) + ") 、 "
        }

        ss = baziInfo.monthDzShiShen
        dz = baziInfo.monthDizhi
        if (ShiShenUtil().isYin(ss)) {
            yinCount++
            yinStr = yinStr + getDizhiStr(
                baziInfo,
                dz
            ) + WuXingUtil().getDiZhiWuxingText(dz) + "(" + ShiShenUtil().getDiZhiShiShenText(
                dz,
                baziInfo.dayTiangan
            ) + ") 、 "
        }
        if (ShiShenUtil().isBiJie(ss)) {
            bijieCount++
            bijieStr = bijieStr + getDizhiStr(
                baziInfo,
                dz
            ) + WuXingUtil().getDiZhiWuxingText(dz) + "(" + ShiShenUtil().getDiZhiShiShenText(
                dz,
                baziInfo.dayTiangan
            ) + ") 、 "
        }

        ss = baziInfo.dayDzShiShen
        dz = baziInfo.dayDizhi
        if (ShiShenUtil().isYin(ss)) {
            yinCount++
            yinStr = yinStr + getDizhiStr(
                baziInfo,
                dz
            ) + WuXingUtil().getDiZhiWuxingText(dz) + "(" + ShiShenUtil().getDiZhiShiShenText(
                dz,
                baziInfo.dayTiangan
            ) + ") 、 "
        }
        if (ShiShenUtil().isBiJie(ss)) {
            bijieCount++
            bijieStr = bijieStr + getDizhiStr(
                baziInfo,
                dz
            ) + WuXingUtil().getDiZhiWuxingText(dz) + "(" + ShiShenUtil().getDiZhiShiShenText(
                dz,
                baziInfo.dayTiangan
            ) + ") 、 "
        }

        ss = baziInfo.hourDzShiShen
        dz = baziInfo.hourDizhi
        if (ShiShenUtil().isYin(ss)) {
            yinCount++
            yinStr = yinStr + getDizhiStr(
                baziInfo,
                dz
            ) + WuXingUtil().getDiZhiWuxingText(dz) + "(" + ShiShenUtil().getDiZhiShiShenText(
                dz,
                baziInfo.dayTiangan
            ) + ") 、 "
        }
        if (ShiShenUtil().isBiJie(ss)) {
            bijieCount++
            bijieStr = bijieStr + getDizhiStr(
                baziInfo,
                dz
            ) + WuXingUtil().getDiZhiWuxingText(dz) + "(" + ShiShenUtil().getDiZhiShiShenText(
                dz,
                baziInfo.dayTiangan
            ) + ") 、 "
        }

        if (yinCount > 0) {
            yinStr =
                stringResource(R.string.app_bazi_have) + yinCount + stringResource(R.string.app_bazi_yin_label) + stringResource(
                    R.string.app_bazi_shengshen
                ) + " " + yinStr
        }
        if (bijieCount > 0) {
            bijieStr =
                stringResource(R.string.app_bazi_have) + bijieCount + stringResource(R.string.app_bazi_bj_label) + stringResource(
                    R.string.app_bazi_bangfu
                ) + " " + bijieStr
        }

        goodElementStr = yinStr + bijieStr
        baziModel.setDeHelpStr(goodElementStr)
        baziModel.setYinCount(yinCount)
        baziModel.setBiJieCount(bijieCount)

        return goodElementStr
    }

    @Composable
    fun analyzeBaziKeXieHao(baziInfo: BaziInfo, baziModel: BaziViewModel): String {

        var ss = ShiShen.SHISHEN_BI_JIAN

        var badElementStr = ""
        var tg = baziInfo.yearTiangan
        var dz = baziInfo.yearDizhi
        var guanshaCount = 0
        var shishangCount = 0
        var caiCount = 0
        var guanshaString = ""
        var shishangString = ""
        var caiString = ""

        //check tiangan
        ss = baziInfo.yearTgShiShen
        tg = baziInfo.yearTiangan
        if (ShiShenUtil().isGuanSha(ss)) {
            guanshaCount++
            guanshaString = guanshaString + getTianganStr(
                baziInfo,
                tg
            ) + WuXingUtil().getTianGanWuxingText(tg) + "(" + ShiShenUtil().getShiShenText(
                tg,
                baziInfo.dayTiangan
            ) + ") 、 "
        }
        if (ShiShenUtil().isShiShang(ss)) {
            shishangCount++
            shishangString = shishangString + getTianganStr(
                baziInfo,
                tg
            ) + WuXingUtil().getTianGanWuxingText(tg) + "(" + ShiShenUtil().getShiShenText(
                tg,
                baziInfo.dayTiangan
            ) + ") 、 "
        }
        if (ShiShenUtil().isCai(ss)) {
            caiCount++
            caiString = caiString + getTianganStr(
                baziInfo,
                tg
            ) + WuXingUtil().getTianGanWuxingText(tg) + "(" + ShiShenUtil().getShiShenText(
                tg,
                baziInfo.dayTiangan
            ) + ") 、 "
        }

        ss = baziInfo.monthTgShiShen
        tg = baziInfo.monthTiangan
        if (ShiShenUtil().isGuanSha(ss)) {
            guanshaCount++
            guanshaString = guanshaString + getTianganStr(
                baziInfo,
                tg
            ) + WuXingUtil().getTianGanWuxingText(tg) + "(" + ShiShenUtil().getShiShenText(
                tg,
                baziInfo.dayTiangan
            ) + ") 、 "
        }
        if (ShiShenUtil().isShiShang(ss)) {
            shishangCount++
            shishangString = shishangString + getTianganStr(
                baziInfo,
                tg
            ) + WuXingUtil().getTianGanWuxingText(tg) + "(" + ShiShenUtil().getShiShenText(
                tg,
                baziInfo.dayTiangan
            ) + ") 、 "
        }
        if (ShiShenUtil().isCai(ss)) {
            caiCount++
            caiString = caiString + getTianganStr(
                baziInfo,
                tg
            ) + WuXingUtil().getTianGanWuxingText(tg) + "(" + ShiShenUtil().getShiShenText(
                tg,
                baziInfo.dayTiangan
            ) + ") 、 "
        }

        ss = baziInfo.hourTgShiShen
        tg = baziInfo.hourTiangan
        if (ShiShenUtil().isGuanSha(ss)) {
            guanshaCount++
            guanshaString = guanshaString + getTianganStr(
                baziInfo,
                tg
            ) + WuXingUtil().getTianGanWuxingText(tg) + "(" + ShiShenUtil().getShiShenText(
                tg,
                baziInfo.dayTiangan
            ) + ") 、 "
        }
        if (ShiShenUtil().isShiShang(ss)) {
            shishangCount++
            shishangString = shishangString + getTianganStr(
                baziInfo,
                tg
            ) + WuXingUtil().getTianGanWuxingText(tg) + "(" + ShiShenUtil().getShiShenText(
                tg,
                baziInfo.dayTiangan
            ) + ") 、 "
        }
        if (ShiShenUtil().isCai(ss)) {
            caiCount++
            caiString = caiString + getTianganStr(
                baziInfo,
                tg
            ) + WuXingUtil().getTianGanWuxingText(tg) + "(" + ShiShenUtil().getShiShenText(
                tg,
                baziInfo.dayTiangan
            ) + ") 、 "
        }

        //check dizhi
        ss = baziInfo.yearDzShiShen
        dz = baziInfo.yearDizhi
        if (ShiShenUtil().isGuanSha(ss)) {
            guanshaCount++
            guanshaString = guanshaString + getDizhiStr(
                baziInfo,
                dz
            ) + WuXingUtil().getDiZhiWuxingText(dz) + "(" + ShiShenUtil().getDiZhiShiShenText(
                dz,
                baziInfo.dayTiangan
            ) + ") 、 "
        }
        if (ShiShenUtil().isShiShang(ss)) {
            shishangCount++
            shishangString = shishangString + getDizhiStr(
                baziInfo,
                dz
            ) + WuXingUtil().getDiZhiWuxingText(dz) + "(" + ShiShenUtil().getDiZhiShiShenText(
                dz,
                baziInfo.dayTiangan
            ) + ") 、 "
        }
        if (ShiShenUtil().isCai(ss)) {
            caiCount++
            caiString = caiString + getDizhiStr(
                baziInfo,
                dz
            ) + WuXingUtil().getDiZhiWuxingText(dz) + "(" + ShiShenUtil().getDiZhiShiShenText(
                dz,
                baziInfo.dayTiangan
            ) + ") 、 "
        }

        ss = baziInfo.monthDzShiShen
        dz = baziInfo.monthDizhi
        if (ShiShenUtil().isGuanSha(ss)) {
            guanshaCount++
            guanshaString = guanshaString + getDizhiStr(
                baziInfo,
                dz
            ) + WuXingUtil().getDiZhiWuxingText(dz) + "(" + ShiShenUtil().getDiZhiShiShenText(
                dz,
                baziInfo.dayTiangan
            ) + ") 、 "
        }
        if (ShiShenUtil().isShiShang(ss)) {
            shishangCount++
            shishangString = shishangString + getDizhiStr(
                baziInfo,
                dz
            ) + WuXingUtil().getDiZhiWuxingText(dz) + "(" + ShiShenUtil().getDiZhiShiShenText(
                dz,
                baziInfo.dayTiangan
            ) + ") 、 "
        }
        if (ShiShenUtil().isCai(ss)) {
            caiCount++
            caiString = caiString + getDizhiStr(
                baziInfo,
                dz
            ) + WuXingUtil().getDiZhiWuxingText(dz) + "(" + ShiShenUtil().getDiZhiShiShenText(
                dz,
                baziInfo.dayTiangan
            ) + ") 、 "
        }

        ss = baziInfo.dayDzShiShen
        dz = baziInfo.dayDizhi
        if (ShiShenUtil().isGuanSha(ss)) {
            guanshaCount++
            guanshaString = guanshaString + getDizhiStr(
                baziInfo,
                dz
            ) + WuXingUtil().getDiZhiWuxingText(dz) + "(" + ShiShenUtil().getDiZhiShiShenText(
                dz,
                baziInfo.dayTiangan
            ) + ") 、 "
        }
        if (ShiShenUtil().isShiShang(ss)) {
            shishangCount++
            shishangString = shishangString + getDizhiStr(
                baziInfo,
                dz
            ) + WuXingUtil().getDiZhiWuxingText(dz) + "(" + ShiShenUtil().getDiZhiShiShenText(
                dz,
                baziInfo.dayTiangan
            ) + ") 、 "
        }
        if (ShiShenUtil().isCai(ss)) {
            caiCount++
            caiString = caiString + getDizhiStr(
                baziInfo,
                dz
            ) + WuXingUtil().getDiZhiWuxingText(dz) + "(" + ShiShenUtil().getDiZhiShiShenText(
                dz,
                baziInfo.dayTiangan
            ) + ") 、 "
        }

        ss = baziInfo.hourDzShiShen
        dz = baziInfo.hourDizhi
        if (ShiShenUtil().isGuanSha(ss)) {
            guanshaCount++
            guanshaString = guanshaString + getDizhiStr(
                baziInfo,
                dz
            ) + WuXingUtil().getDiZhiWuxingText(dz) + "(" + ShiShenUtil().getDiZhiShiShenText(
                dz,
                baziInfo.dayTiangan
            ) + ") 、 "
        }
        if (ShiShenUtil().isShiShang(ss)) {
            shishangCount++
            shishangString = shishangString + getDizhiStr(
                baziInfo,
                dz
            ) + WuXingUtil().getDiZhiWuxingText(dz) + "(" + ShiShenUtil().getDiZhiShiShenText(
                dz,
                baziInfo.dayTiangan
            ) + ") 、 "
        }
        if (ShiShenUtil().isCai(ss)) {
            caiCount++
            caiString = caiString + getDizhiStr(
                baziInfo,
                dz
            ) + WuXingUtil().getDiZhiWuxingText(dz) + "(" + ShiShenUtil().getDiZhiShiShenText(
                dz,
                baziInfo.dayTiangan
            ) + ") 、 "
        }

        if (guanshaCount > 0) {
            guanshaString =
                stringResource(R.string.app_bazi_have) + guanshaCount + stringResource(R.string.app_bazi_gs_label) + stringResource(
                    R.string.app_bazi_ke
                ) + " " + guanshaString
        }
        if (shishangCount > 0) {
            shishangString =
                stringResource(R.string.app_bazi_have) + shishangCount + stringResource(R.string.app_bazi_ss_label) + stringResource(
                    R.string.app_bazi_xie
                ) + " " + shishangString
        }
        if (caiCount > 0) {
            caiString =
                stringResource(R.string.app_bazi_have) + caiCount + stringResource(R.string.app_bazi_cai_label) + stringResource(
                    R.string.app_bazi_hao
                ) + " " + caiString
        }


        badElementStr = guanshaString + shishangString + caiString

        baziModel.setKeXieHaoStr(badElementStr)
        baziModel.setGuanShaCount(guanshaCount)
        baziModel.setShiShangCount(shishangCount)
        baziModel.setCaiCount(caiCount)

        return badElementStr
    }

    @Composable
    fun analyzeBaziAndSaveStat(baziInfo: BaziInfo, baziModel: BaziViewModel) {
        Log.i("[Rick]", "Aanalyze Bazi And Save Stat");

//        //owner wuxing
//        WuXingUtil().getOwnerWuXingString(baziInfo, baziModel)

        //shishen string
        ShiShenUtil().getBaziShiShengString(baziInfo, baziModel)

//        //deling check
//        WuXingUtil().getDangLingStr(baziInfo, baziModel)

        //collect dedi info
//        WuXingUtil().isDeDi(baziInfo, baziModel)
//        WuXingUtil().getDeDiCheckStr(baziInfo, baziModel)

        analyzeBaziHelpStar(baziInfo, baziModel)
        analyzeBaziKeXieHao(baziInfo, baziModel)
        getBaziStrengthSummary(baziInfo, baziModel)
        getXiJiSummary(baziInfo, baziModel)
        getBaziGeJuSummary(baziInfo, baziModel)
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
            builder.append(getDayunCheckResult(tg, dz, baziInfo))

            builder.append("\n")
            str = str + builder.toString()

        }

        baziModel.setBaziDayunSummary(str)
        return str
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
            str = stringResource(R.string.app_bazi_strong_3)
        }
        if (s == BaziStrength.BAZI_STRONG) {
            str = stringResource(R.string.app_bazi_strong_2)
        }
        if (s == BaziStrength.BAZI_STRONG_MILD) {
            str = stringResource(R.string.app_bazi_strong_1)
        }
        if (s == BaziStrength.BAZI_BALANCED) {
            str = stringResource(R.string.app_bazi_balanced)
        }
        if (s == BaziStrength.BAZI_WEAK_MILD) {
            str = stringResource(R.string.app_bazi_weak_1)
        }
        if (s == BaziStrength.BAZI_WEAK) {
            str = stringResource(R.string.app_bazi_weak_2)
        }
        if (s == BaziStrength.BAZI_WEAK_SUPER) {
            str = stringResource(R.string.app_bazi_weak_3)
        }
        return str
    }

    @Composable
    fun getBaziStrengthSummary(baziInfo: BaziInfo, baziModel: BaziViewModel): String {
        var summary = ""
        var baziStrength = BaziStrength.BAZI_BALANCED
        var goodElementCount = baziInfo.bijieCount + baziInfo.yinCount
        var badElementCount = baziInfo.guanshaCount + baziInfo.shishangCount + baziInfo.caiCount
        Log.i(
            "[Rick]",
            "goodElementCount=" + goodElementCount + ",badElementCount=" + badElementCount
        );

        var elementDiffWeight = goodElementCount - badElementCount
        var danglingWeight = 0
        var dediWeight = 0
        var totalWeight = 0
        if (baziInfo.isDangLing) {
            danglingWeight = 3
        } else {
            danglingWeight = -3
        }

        if (baziInfo.isDedi) {
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
            summary + stringResource(R.string.bazi_owner) + WuXingUtil().getOwnerStr(baziInfo)
        if (baziInfo.isDangLing) {
            summary = summary + stringResource(R.string.app_bazi_dangling_yes)
        } else {
            summary = summary + stringResource(R.string.app_bazi_dangling_no)
        }
        summary = summary + " "
        if (baziInfo.isDedi) {
            summary = summary + stringResource(R.string.app_bazi_dedi_yes)
        } else {
            summary = summary + stringResource(R.string.app_bazi_dedi_no)
        }
        summary = summary + " "
        if (elementDiffWeight > 0) {
            summary = summary + stringResource(R.string.app_bazi_deshi_yes)
        } else if (elementDiffWeight < 0) {
            summary = summary + stringResource(R.string.app_bazi_deshi_no)
        }
        summary = summary + " "
        summary = summary + getBaziStrengthText(baziStrength)

        baziModel.setBaziStrength(baziStrength)
        baziModel.setBaziStrengthSummary(summary)
        return summary
    }

    @Composable
    fun isRootToTianGan(tg: TianGan, baziInfo: BaziInfo): Boolean {
        var ret = false
        var wx = WuXingUtil().getTgWX(tg)
        if (wx == WuXingUtil().getTgWX(baziInfo.yearTiangan)) return true
        if (wx == WuXingUtil().getTgWX(baziInfo.monthTiangan)) return true
        if (wx == WuXingUtil().getTgWX(baziInfo.dayTiangan)) return true
        if (wx == WuXingUtil().getTgWX(baziInfo.hourTiangan)) return true
        return ret
    }

    @Composable
    fun getBaziGeJuSummary(baziInfo: BaziInfo, baziModel: BaziViewModel){
        var string = ""
        var gjString = ""
        var gjSummary = ""
        var gj = BaziGeJu.GJ_NONE
        var gjTG = TianGan.TIANGAN_JIA

        if(GeJuUtil().isCongShaGJ(baziInfo, baziModel))return

        //normal GeJu
        var monthDz = baziInfo.monthDizhi
        var ss = baziInfo.monthDzShiShen
        var tgList = cangganLookupMap.get(monthDz)
        Log.i("[Rick]", "tgList=" + tgList)
        if (tgList != null) {
            if (tgList.size == 1) {
                gjTG = tgList.get(0)
            } else {
                for (tg in tgList) {
                    if (isRootToTianGan(tg, baziInfo)) {
                        gjTG = tg
                        Log.i("[Rick]", "GeJu tg=" + tg)
                        break
                    }
                }
            }
            ss = ShiShenUtil().getShiShen(gjTG, baziInfo.dayTiangan)
            gj = GeJuUtil().getGJByShiShen(ss)
            gjString = GeJuUtil().getGJText(gj)
            gjSummary = GeJuUtil().getGJSummary(gj)
//            Log.i("[Rick]", "GeJu tg=" + tg)
            Log.i("[Rick]", "GeJu gjSummary=" + gjSummary)

        }

        baziModel.setBaziGJ(gj)
        baziModel.setBaziGJSummary(gjSummary)
        baziModel.setBaziGJString(gjString)
    }

    @Composable
    fun getBaziStrongSummary(baziInfo: BaziInfo, baziModel: BaziViewModel): String {
        var summary = ""
        var yinCount = baziInfo.yinCount
        var bijieCount = baziInfo.bijieCount
        summary = summary + stringResource(R.string.app_bazi_xiyong_shen) + ": "

//        summary = summary + stringResource(R.string.app_bazi_strong_2_desc) + "\n "

        var guanshaString = "[" + WuXingUtil().getGuanshaWuXingText(
            baziInfo.dayTiangan
        ) + "] " + getBaziYongshenStr(
            baziInfo,
            baziModel,
            ShiShen.SHISHEN_ZHENG_GUAN
        ) + getBaziYongshenStr(baziInfo, baziModel, ShiShen.SHISHEN_QI_SHA)

        var shishangString =
            "[" + WuXingUtil().getShishangWuXingText(baziInfo.dayTiangan) + "] " + getBaziYongshenStr(
                baziInfo,
                baziModel,
                ShiShen.SHISHEN_SHI_SHEN
            ) + getBaziYongshenStr(baziInfo, baziModel, ShiShen.SHISHEN_SHANG_GUAN) + "]"

        var caiString =
            "[" + WuXingUtil().getCaiWuXingText(baziInfo.dayTiangan) + "] " + getBaziYongshenStr(
                baziInfo,
                baziModel,
                ShiShen.SHISHEN_ZHENG_CAI
            ) + getBaziYongshenStr(baziInfo, baziModel, ShiShen.SHISHEN_PIAN_CAI) + "]"

        if (bijieCount > yinCount) {
            summary = summary + "(1)" + guanshaString
            summary = summary + "(2)" + shishangString
            summary = summary + "(3)" + caiString
        } else if (yinCount > bijieCount) {
            summary = summary + "(1)" + caiString
            summary = summary + "(2)" + shishangString
            summary = summary + "(3)" + guanshaString
        } else {
            summary = summary + "(1)" + shishangString
            summary = summary + "(2)" + caiString
            summary = summary + "(3)" + guanshaString
        }

        summary = summary + "\n"

        summary =
            summary + stringResource(R.string.app_bazi_ji_shen) + ": (1)[" + WuXingUtil().getYinWuXingText(
                baziInfo.dayTiangan
            ) + "] (2)[" + WuXingUtil().getBiJieWuXingText(baziInfo.dayTiangan) + "] \n"

        return summary
    }

    @Composable
    fun getBaziYongshenStr(baziInfo: BaziInfo, baziModel: BaziViewModel, ss: ShiShen): String {
        var str = ""
        var tg = TianGan.TIANGAN_JIA
        var dz = DiZhi.DIZHI_ZI
        //tiangan
        tg = baziInfo.yearTiangan
        if (baziInfo.yearTgShiShen == ss) {
            str = str + stringResource(R.string.bazi_year) + getTianganStr(
                baziInfo,
                tg
            ) + WuXingUtil().getTianGanWuxingText(tg) + "(" + ShiShenUtil().getShiShenText(
                tg,
                baziInfo.dayTiangan
            ) + ")"
        }
        tg = baziInfo.monthTiangan
        if (baziInfo.monthTgShiShen == ss) {
            str = str + stringResource(R.string.bazi_month) + getTianganStr(
                baziInfo,
                tg
            ) + WuXingUtil().getTianGanWuxingText(tg) + "(" + ShiShenUtil().getShiShenText(
                tg,
                baziInfo.dayTiangan
            ) + ")"
        }
        tg = baziInfo.hourTiangan
        if (baziInfo.hourDzShiShen == ss) {
            str = str + stringResource(R.string.bazi_hour) + getTianganStr(
                baziInfo,
                tg
            ) + WuXingUtil().getTianGanWuxingText(tg) + "(" + ShiShenUtil().getShiShenText(
                tg,
                baziInfo.dayTiangan
            ) + ")"
        }
        //dizhi
        dz = baziInfo.yearDizhi
        if (baziInfo.yearDzShiShen == ss) {
            str = str + stringResource(R.string.bazi_year) + getDizhiStr(
                baziInfo,
                dz
            ) + WuXingUtil().getDiZhiWuxingText(dz) + "(" + ShiShenUtil().getDiZhiShiShenText(
                dz,
                baziInfo.dayTiangan
            ) + ")"
        }
        dz = baziInfo.monthDizhi
        if (baziInfo.monthDzShiShen == ss) {
            str = str + stringResource(R.string.bazi_month) + getDizhiStr(
                baziInfo,
                dz
            ) + WuXingUtil().getDiZhiWuxingText(dz) + "(" + ShiShenUtil().getDiZhiShiShenText(
                dz,
                baziInfo.dayTiangan
            ) + ")"
        }
        dz = baziInfo.dayDizhi
        if (baziInfo.dayDzShiShen == ss) {
            str = str + stringResource(R.string.bazi_day) + getDizhiStr(
                baziInfo,
                dz
            ) + WuXingUtil().getDiZhiWuxingText(dz) + "(" + ShiShenUtil().getDiZhiShiShenText(
                dz,
                baziInfo.dayTiangan
            ) + ")"
        }
        dz = baziInfo.hourDizhi
        if (baziInfo.hourDzShiShen == ss) {
            str = str + stringResource(R.string.bazi_hour) + getDizhiStr(
                baziInfo,
                dz
            ) + WuXingUtil().getDiZhiWuxingText(dz) + "(" + ShiShenUtil().getDiZhiShiShenText(
                dz,
                baziInfo.dayTiangan
            ) + ")"
        }

        return str
    }

    @Composable
    fun getBaziWeakSummary(baziInfo: BaziInfo, baziModel: BaziViewModel): String {
        var summary = ""
//        summary = summary + stringResource(R.string.app_bazi_weak_2_desc) + "\n "
        summary = summary + stringResource(R.string.app_bazi_xiyong_shen) + ": "

        var guanshaCount = baziInfo.guanshaCount
        var shishangCount = baziInfo.shishangCount
        var caiCount = baziInfo.caiCount

        var yinStr = "[" + WuXingUtil().getYinWuXingText(
            baziInfo.dayTiangan
        ) + "] " + getBaziYongshenStr(
            baziInfo,
            baziModel,
            ShiShen.SHISHEN_ZHENG_YIN
        ) + getBaziYongshenStr(baziInfo, baziModel, ShiShen.SHISHEN_PIAN_YIN)

        var bijieStr =
            "[" + WuXingUtil().getBiJieWuXingText(baziInfo.dayTiangan) + getBaziYongshenStr(
                baziInfo,
                baziModel,
                ShiShen.SHISHEN_BI_JIAN
            ) + getBaziYongshenStr(baziInfo, baziModel, ShiShen.SHISHEN_JIE_CAI) + "]"
//        summary = summary + stringResource(R.string.app_bazi_xiyong_shen) + ": "
        if (guanshaCount > shishangCount && guanshaCount > caiCount) {
            summary = summary + "(1)" + yinStr
            summary = summary + "(2)" + bijieStr

        } else if (caiCount > guanshaCount && caiCount > shishangCount) {
            summary = summary + "(1)" + bijieStr
            summary = summary + "(2)" + yinStr
        } else if (shishangCount > guanshaCount && shishangCount > caiCount) {
            summary = summary + "(1)" + yinStr
            summary = summary + "(2)" + bijieStr
        } else {
            summary = summary + "(1)" + yinStr
            summary = summary + "(2)" + bijieStr
        }
        summary = summary + "\n"
        summary =
            summary + stringResource(R.string.app_bazi_ji_shen) + ": (1)[" + WuXingUtil().getGuanshaWuXingText(
                baziInfo.dayTiangan
            ) + "] (2)[" + WuXingUtil().getShishangWuXingText(baziInfo.dayTiangan) + "] (3)[" + WuXingUtil().getCaiWuXingText(
                baziInfo.dayTiangan
            ) + "]\n"
        return summary
    }

    @Composable
    fun getXiJiSummary(baziInfo: BaziInfo, baziModel: BaziViewModel): String {
        var summary = ""
        var xiyongShenList: List<WuXing> = arrayListOf<WuXing>()
        var jiShenList: List<WuXing> = arrayListOf<WuXing>()

        var strenthLevel = baziInfo.baziStrength
        if (strenthLevel == BaziStrength.BAZI_STRONG_SUPER) {
            summary = summary + stringResource(R.string.app_bazi_gj_congqian_desc) + "\n "
            summary =
                summary + stringResource(R.string.app_bazi_xiyong_shen) + ": (1)[" + WuXingUtil().getYinWuXingText(
                    baziInfo.dayTiangan
                ) + "] (2)[" + WuXingUtil().getBiJieWuXingText(baziInfo.dayTiangan) + "] \n"
            summary =
                summary + stringResource(R.string.app_bazi_ji_shen) + ": (1)[" + WuXingUtil().getGuanshaWuXingText(
                    baziInfo.dayTiangan
                ) + "] (2)[" + WuXingUtil().getShishangWuXingText(baziInfo.dayTiangan) + "] (3)[" + WuXingUtil().getCaiWuXingText(
                    baziInfo.dayTiangan
                ) + "]\n"
            xiyongShenList = arrayListOf<WuXing>(
                WuXingUtil().getYinWuXing(baziInfo.dayTiangan),
                WuXingUtil().getBiJieWuXing(baziInfo.dayTiangan)
            )
            jiShenList = arrayListOf<WuXing>(
                WuXingUtil().getGuanshaWuXing(baziInfo.dayTiangan),
                WuXingUtil().getShishangWuXing(baziInfo.dayTiangan),
                WuXingUtil().getCaiWuXing(baziInfo.dayTiangan)
            )
        } else if (strenthLevel == BaziStrength.BAZI_STRONG) {
            summary = summary + stringResource(R.string.app_bazi_strong_2_desc) + "\n "

            summary = summary + getBaziStrongSummary(baziInfo, baziModel)
            xiyongShenList = arrayListOf<WuXing>(
                WuXingUtil().getGuanshaWuXing(baziInfo.dayTiangan),
                WuXingUtil().getShishangWuXing(baziInfo.dayTiangan),
                WuXingUtil().getCaiWuXing(baziInfo.dayTiangan)
            )
            jiShenList = arrayListOf<WuXing>(
                WuXingUtil().getYinWuXing(baziInfo.dayTiangan),
                WuXingUtil().getBiJieWuXing(baziInfo.dayTiangan)
            )
        } else if (strenthLevel == BaziStrength.BAZI_STRONG_MILD) {
            summary = summary + stringResource(R.string.app_bazi_strong_1_desc) + "\n "
            summary = summary + getBaziStrongSummary(baziInfo, baziModel)
            xiyongShenList = arrayListOf<WuXing>(
                WuXingUtil().getGuanshaWuXing(baziInfo.dayTiangan),
                WuXingUtil().getShishangWuXing(baziInfo.dayTiangan),
                WuXingUtil().getCaiWuXing(baziInfo.dayTiangan)
            )
            jiShenList = arrayListOf<WuXing>(
                WuXingUtil().getYinWuXing(baziInfo.dayTiangan),
                WuXingUtil().getBiJieWuXing(baziInfo.dayTiangan)
            )
        } else if (strenthLevel == BaziStrength.BAZI_BALANCED) {
            summary = summary + stringResource(R.string.app_bazi_balanced_desc) + "\n "
        } else if (strenthLevel == BaziStrength.BAZI_WEAK_MILD) {
            summary = summary + stringResource(R.string.app_bazi_weak_1_desc) + "\n "
            summary = summary + getBaziWeakSummary(baziInfo, baziModel)
            xiyongShenList = arrayListOf<WuXing>(
                WuXingUtil().getYinWuXing(baziInfo.dayTiangan),
                WuXingUtil().getBiJieWuXing(baziInfo.dayTiangan)
            )
            jiShenList = arrayListOf<WuXing>(
                WuXingUtil().getGuanshaWuXing(baziInfo.dayTiangan),
                WuXingUtil().getShishangWuXing(baziInfo.dayTiangan),
                WuXingUtil().getCaiWuXing(baziInfo.dayTiangan)
            )
        } else if (strenthLevel == BaziStrength.BAZI_WEAK) {
            summary = summary + stringResource(R.string.app_bazi_weak_2_desc) + "\n "
            summary = summary + getBaziWeakSummary(baziInfo, baziModel)
            xiyongShenList = arrayListOf<WuXing>(
                WuXingUtil().getYinWuXing(baziInfo.dayTiangan),
                WuXingUtil().getBiJieWuXing(baziInfo.dayTiangan)
            )
            jiShenList = arrayListOf<WuXing>(
                WuXingUtil().getGuanshaWuXing(baziInfo.dayTiangan),
                WuXingUtil().getShishangWuXing(baziInfo.dayTiangan),
                WuXingUtil().getCaiWuXing(baziInfo.dayTiangan)
            )
        } else if (strenthLevel == BaziStrength.BAZI_WEAK_SUPER) {
            summary = summary + stringResource(R.string.app_bazi_gj_congruo_desc) + "\n "
            summary =
                summary + stringResource(R.string.app_bazi_xiyong_shen) + ": (1)[" + WuXingUtil().getGuanshaWuXingText(
                    baziInfo.dayTiangan
                ) + "] (2)[" + WuXingUtil().getShishangWuXingText(baziInfo.dayTiangan) + "] (3)[" + WuXingUtil().getCaiWuXingText(
                    baziInfo.dayTiangan
                ) + "]\n"
            summary =
                summary + stringResource(R.string.app_bazi_ji_shen) + ": (1)[" + WuXingUtil().getYinWuXingText(
                    baziInfo.dayTiangan
                ) + "] (2)[" + WuXingUtil().getBiJieWuXingText(baziInfo.dayTiangan) + "] \n"

            //todo, check which one to follow
//                xiyongShenList = arrayListOf<WuXing>(WuXingUtil().getYinWuXing(baziInfo.dayTiangan),WuXingUtil().getBiJieWuXing(baziInfo.dayTiangan))
//                jiShenList = arrayListOf<WuXing>(WuXingUtil().getGuanshaWuXing(baziInfo.dayTiangan),WuXingUtil().getShishangWuXing(baziInfo.dayTiangan),WuXingUtil().getCaiWuXing(baziInfo.dayTiangan))

            jiShenList = arrayListOf<WuXing>(
                WuXingUtil().getYinWuXing(baziInfo.dayTiangan),
                WuXingUtil().getBiJieWuXing(baziInfo.dayTiangan)
            )
            xiyongShenList = arrayListOf<WuXing>(
                WuXingUtil().getGuanshaWuXing(baziInfo.dayTiangan),
                WuXingUtil().getShishangWuXing(baziInfo.dayTiangan),
                WuXingUtil().getCaiWuXing(baziInfo.dayTiangan)
            )
        }

        summary =
            summary + stringResource(R.string.app_bazi_tiaohou_shen) + ":" + WuXingUtil().getTiaohouWuXingText(
                baziInfo, baziModel
            ) + ""

        baziModel.setBaziXiyongShenList(xiyongShenList)
        baziModel.setBaziJiShenList(jiShenList)
        baziModel.setBaziXiJiSummary(summary)
        return summary
    }
}


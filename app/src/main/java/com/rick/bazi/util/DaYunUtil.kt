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
import java.time.LocalDate
import java.time.Year
import java.time.LocalDateTime
import java.time.YearMonth

class DaYunUtil {

    data class DayunRecord(
        val dyLabel: String, val dyAgeLabel: String
    )

    fun isDaYunForward(data: BaziData): Boolean {
        if (data.gender == MALE) {
            if (TianGanUtil().isTianGanYang(data.yearTiangan)) {
                return true
            } else {
                return false
            }
        } else {
            if (TianGanUtil().isTianGanYang(data.yearTiangan)) {
                return false
            } else {
                return true
            }
        }
    }

    //index start from 1
    fun getDaYun(index: Int, data: BaziData): TianGanDiZhi {
        var tg = data.monthTiangan
        var dz = data.monthDizhi
        if (isDaYunForward(data)) {
            for (i in 0 until index) {
                tg = TianGanUtil().getNextTianGan(tg)
                dz = DiZhiUtil().getNextDiZhi(dz)
            }
        } else {
            for (i in 0 until index) {
                tg = TianGanUtil().getPrevTianGan(tg)
                dz = DiZhiUtil().getPrevDiZhi(dz)
            }
        }

        return TianGanDiZhi(tg, dz)
    }



    @Composable
    fun getDaYunStartTimeString(data: BaziData): String {
        val sb = StringBuilder()
        sb.append(stringResource(R.string.app_after_birth))
        sb.append(data.daYunStartYear).append(stringResource(R.string.app_label_year))
        sb.append(data.daYunStartMonth).append(stringResource(R.string.app_label_month))
        sb.append("(")
        sb.append(data.daYunFirstYear).append(stringResource(R.string.app_label_year))
        sb.append(data.daYunFirstMonth).append(stringResource(R.string.app_label_month))
        sb.append(")")
        sb.append(stringResource(R.string.app_start_dayun))
        return sb.toString()
    }

    @Composable
    fun getTianGan5HeString(
        dyTG: TianGan, tg: TianGan, columnPosition: ColumnPosition, data: BaziData
    ): String {
        val sb = StringBuilder()
        var wx = WuXing.WUXING_MU
        if (TianGanUtil().isTianGanHe(dyTG, tg)) {
            wx = TianGanUtil().getTianGanHeWuXing(dyTG)
            sb.append(ConstUtil.SPACE)
            sb.append(stringResource(R.string.dayun_tiangan_label))
            sb.append(stringResource(R.string.app_and_label))
            sb.append(BaziColumnUtil().getBaziColumnString(columnPosition, true))
            sb.append(" ")
            sb.append(TianGanUtil().getTianGan5HeLabel(dyTG))

            if (YongShenUtil().isWuXingXiYongShen(wx, data)) {
                data.daYunWeight += ConstUtil.TIANGAN_5HE_WEIGHT
                sb.append("[")
                sb.append(stringResource(R.string.app_bazi_xiyong_shen))
                sb.append("] ")
                sb.append(stringResource(R.string.app_bazi_weight_label))
                sb.append("(").append(ConstUtil.ADD_SYMBOL)
                sb.append(ConstUtil.TIANGAN_5HE_WEIGHT).append(")")
            } else if (YongShenUtil().isWuXingJiShen(wx, data)) {
                data.daYunWeight -= ConstUtil.TIANGAN_5HE_WEIGHT
                sb.append("[")
                sb.append(stringResource(R.string.app_bazi_ji_shen))
                sb.append("] ")
                sb.append(stringResource(R.string.app_bazi_weight_label))
                sb.append("(").append(ConstUtil.SUB_SYMBOL)
                sb.append(ConstUtil.TIANGAN_5HE_WEIGHT).append(")")
            } else {
                data.daYunWeight += ConstUtil.XIAN_SHEN_WEIGHT
                sb.append("[")
                sb.append(stringResource(R.string.app_bazi_xian_shen))
                sb.append("] ")
                sb.append(stringResource(R.string.app_bazi_weight_label))
                sb.append("(").append(ConstUtil.ADD_SYMBOL)
                sb.append(ConstUtil.XIAN_SHEN_WEIGHT).append(")")
            }
            sb.append("\n")
        }
        return sb.toString()
    }

    @Composable
    fun getDaYunTianGan5HeString(dyTG: TianGan, data: BaziData): String {
        val sb = StringBuilder()
        sb.append(getTianGan5HeString(dyTG, data.yearTiangan, ColumnPosition.COLUMN_YEAR, data))
        sb.append(getTianGan5HeString(dyTG, data.monthTiangan, ColumnPosition.COLUMN_MONTH, data))
        sb.append(getTianGan5HeString(dyTG, data.hourTiangan, ColumnPosition.COLUMN_HOUR, data))
        return sb.toString()
    }

    @Composable
    fun getDaYunDiZhiTouCheckString(
        dyTG: TianGan, dz: DiZhi, position: ColumnPosition, data: BaziData
    ): String {
        val sb = StringBuilder()
        var tg = DiZhiUtil().getTianGan(dz)
        if (tg == dyTG) {
            sb.append(ConstUtil.SPACE)
            sb.append(BaziColumnUtil().getBaziColumnString(position, false))
            sb.append(WuXingUtil().getDiZhiWuXingString(dz))
            sb.append(stringResource(R.string.dizhi_touchu_label))
            sb.append(stringResource(R.string.dayun_tiangan_label))
            sb.append(" ")
            sb.append(YongShenUtil().getDiZhiTouXiJiWeightString(dz, data))
        }
        return sb.toString()
    }

    @Composable
    fun getDaYunDiZhiTouchuCheckString(dyTG: TianGan, dyDZ: DiZhi, data: BaziData): String {
        val sb = StringBuilder()
        sb.append(
            getDaYunDiZhiTouCheckString(
                dyTG, data.yearDizhi, ColumnPosition.COLUMN_YEAR, data
            )
        )
        sb.append(
            getDaYunDiZhiTouCheckString(
                dyTG, data.monthDizhi, ColumnPosition.COLUMN_MONTH, data
            )
        )
        sb.append(getDaYunDiZhiTouCheckString(dyTG, data.dayDizhi, ColumnPosition.COLUMN_DAY, data))
        sb.append(
            getDaYunDiZhiTouCheckString(
                dyTG, data.hourDizhi, ColumnPosition.COLUMN_HOUR, data
            )
        )
        sb.append(getDaYunDiZhiTouCheckString(dyTG, dyDZ, ColumnPosition.COLUMN_DA_YUN, data))
        return sb.toString()
    }

    @Composable
    fun getDaYunChongString(
        dyDZ: DiZhi, dz: DiZhi, columnPosition: ColumnPosition, data: BaziData
    ): String {
        val sb = StringBuilder()
        if (DiZhiUtil().isDiZhiChong(dyDZ, dz)) {
            sb.append(ConstUtil.SPACE)
            sb.append(stringResource(R.string.dayun_dizhi_label))
            sb.append(WuXingUtil().getDiZhiWuXingString(dyDZ))
            sb.append(stringResource(R.string.dizhi_chong_label))
            sb.append(BaziColumnUtil().getBaziColumnString(columnPosition, false))
            sb.append(WuXingUtil().getDiZhiWuXingString(dz))

            if (YongShenUtil().isDiZhiYongShen(dz, data)) {
                sb.append("[")
                sb.append(stringResource(R.string.app_bazi_yong_shen))
                sb.append("]")
                sb.append(" ")
                sb.append(stringResource(R.string.app_bazi_weight_label))

                data.daYunWeight -= ConstUtil.CHONG_YONG_SHEN_WEIGHT
                sb.append("(").append(ConstUtil.SUB_SYMBOL).append(ConstUtil.CHONG_YONG_SHEN_WEIGHT)
                    .append(")")
            } else if (YongShenUtil().isDiZhiXiShen(dz, data)) {
                sb.append("[")
                sb.append(stringResource(R.string.app_bazi_xi_shen))
                sb.append("]")
                sb.append(" ")
                sb.append(stringResource(R.string.app_bazi_weight_label))

                data.daYunWeight -= ConstUtil.CHONG_XI_SHEN_WEIGHT
                sb.append("(")
                sb.append(ConstUtil.SUB_SYMBOL)
                sb.append(ConstUtil.CHONG_XI_SHEN_WEIGHT)
                sb.append(")")
            } else if (YongShenUtil().isDiZhiJiShen(dz, data)) {
                sb.append("[")
                sb.append(stringResource(R.string.app_bazi_ji_shen))
                sb.append("]")
                sb.append(" ")
                sb.append(stringResource(R.string.app_bazi_weight_label))

                data.daYunWeight += ConstUtil.CHONG_JI_SHEN_WEIGHT
                sb.append("(")
                sb.append(ConstUtil.ADD_SYMBOL)
                sb.append(ConstUtil.CHONG_JI_SHEN_WEIGHT)
                sb.append(")")
            }
            sb.append("\n")
        }
        return sb.toString()
    }

    @Composable
    fun getDaYunDiZhiChongString(dyDZ: DiZhi, data: BaziData): String {
        val sb = StringBuilder()
        sb.append(getDaYunChongString(dyDZ, data.yearDizhi, ColumnPosition.COLUMN_YEAR, data))
        sb.append(getDaYunChongString(dyDZ, data.monthDizhi, ColumnPosition.COLUMN_MONTH, data))
        sb.append(getDaYunChongString(dyDZ, data.dayDizhi, ColumnPosition.COLUMN_DAY, data))
        sb.append(getDaYunChongString(dyDZ, data.hourDizhi, ColumnPosition.COLUMN_HOUR, data))
        return sb.toString()
    }

    @Composable
    fun get3HuiCheckString(dyDZ: DiZhi, dz1: DiZhi, dz2: DiZhi, data: BaziData): String {
        val sb = StringBuilder()
        var wx = WuXing.WUXING_MU
        if (DiZhiUtil().isDiZhiSanHui(dyDZ, dz1, dz2)) {
            wx = DiZhiUtil().get3HuiWuXingByDiZhi(dyDZ)
            sb.append(ConstUtil.SPACE)
            sb.append(stringResource(R.string.dayun_dizhi_label))
            sb.append(DiZhiUtil().getSanHuiStringByWuXing(wx))
            if (YongShenUtil().isWuXingXiYongShen(wx, data)) {
                data.daYunWeight += ConstUtil.DIZHI_SANHUI_WEIGHT
                sb.append("[")
                sb.append(stringResource(R.string.app_bazi_xiyong_shen))
                sb.append("]")
                sb.append(" ")
                sb.append(stringResource(R.string.app_bazi_weight_label))
                sb.append("(")
                sb.append(ConstUtil.ADD_SYMBOL)
                sb.append(ConstUtil.DIZHI_SANHUI_WEIGHT)
                sb.append(")")
            } else if (YongShenUtil().isWuXingJiShen(wx, data)) {
                data.daYunWeight -= ConstUtil.DIZHI_SANHUI_WEIGHT
                sb.append("[")
                sb.append(stringResource(R.string.app_bazi_ji_shen))
                sb.append("]")
                sb.append(" ")
                sb.append(stringResource(R.string.app_bazi_weight_label))
                sb.append("(")
                sb.append(ConstUtil.SUB_SYMBOL)
                sb.append(ConstUtil.DIZHI_SANHUI_WEIGHT)
                sb.append(")")
            } else {
                data.daYunWeight += ConstUtil.XIAN_SHEN_WEIGHT
                sb.append("[")
                sb.append(stringResource(R.string.app_bazi_xian_shen))
                sb.append("]")
                sb.append(" ")
                sb.append(stringResource(R.string.app_bazi_weight_label))
                sb.append("(")
                sb.append(ConstUtil.ADD_SYMBOL)
                sb.append(ConstUtil.XIAN_SHEN_WEIGHT)
                sb.append(")")
            }
            sb.append("\n")
        }
        return sb.toString()
    }

    @Composable
    fun getDaYunDiZhi3HuiString(dyDZ: DiZhi, data: BaziData): String {
        val sb = StringBuilder()
        var str = get3HuiCheckString(dyDZ, data.yearDizhi, data.monthDizhi, data)
        sb.append(str)
        if (sb.isEmpty()) {
            str = get3HuiCheckString(dyDZ, data.yearDizhi, data.dayDizhi, data)
            sb.append(str)
        }
        if (sb.isEmpty()) {
            str = get3HuiCheckString(dyDZ, data.yearDizhi, data.hourDizhi, data)
            sb.append(str)
        }
        if (sb.isEmpty()) {
            str = get3HuiCheckString(dyDZ, data.monthDizhi, data.dayDizhi, data)
            sb.append(str)
        }
        if (sb.isEmpty()) {
            str = get3HuiCheckString(dyDZ, data.monthDizhi, data.hourDizhi, data)
            sb.append(str)
        }
        if (sb.isEmpty()) {
            str = get3HuiCheckString(dyDZ, data.dayDizhi, data.hourDizhi, data)
            sb.append(str)
        }
        return sb.toString()
    }

    @Composable
    fun get3HeCheckString(dyDZ: DiZhi, dz1: DiZhi, dz2: DiZhi, data: BaziData): String {
        val sb = StringBuilder()
        var wx = WuXing.WUXING_MU
        if (DiZhiUtil().isDiZhiSanHe(dyDZ, dz1, dz2)) {
            wx = DiZhiUtil().get3HeWuXingByDiZhi(dyDZ)

            sb.append(ConstUtil.SPACE)
            sb.append(stringResource(R.string.dayun_dizhi_label))
            sb.append(DiZhiUtil().getSanHeStringByWuXing(wx))

            if (YongShenUtil().isWuXingXiYongShen(wx, data)) {
                data.daYunWeight += ConstUtil.DIZHI_SANHE_WEIGHT
                sb.append("[")
                sb.append(stringResource(R.string.app_bazi_xiyong_shen))
                sb.append("]")
                sb.append(" ")
                sb.append(stringResource(R.string.app_bazi_weight_label))
                sb.append("(")
                sb.append(ConstUtil.ADD_SYMBOL)
                sb.append(ConstUtil.DIZHI_SANHE_WEIGHT)
                sb.append(")")
            } else if (YongShenUtil().isWuXingJiShen(wx, data)) {
                data.daYunWeight -= ConstUtil.DIZHI_SANHE_WEIGHT
                sb.append("[")
                sb.append(stringResource(R.string.app_bazi_ji_shen))
                sb.append("]")
                sb.append(" ")
                sb.append(stringResource(R.string.app_bazi_weight_label))
                sb.append("(")
                sb.append(ConstUtil.SUB_SYMBOL)
                sb.append(ConstUtil.DIZHI_SANHE_WEIGHT)
                sb.append(")")
            } else {
                data.daYunWeight += ConstUtil.XIAN_SHEN_WEIGHT
                sb.append("[")
                sb.append(stringResource(R.string.app_bazi_xian_shen))
                sb.append("]")
                sb.append(" ")
                sb.append(stringResource(R.string.app_bazi_weight_label))
                sb.append("(")
                sb.append(ConstUtil.ADD_SYMBOL)
                sb.append(ConstUtil.XIAN_SHEN_WEIGHT)
                sb.append(")")
            }
            sb.append("\n")
        }
        return sb.toString()
    }

    @Composable
    fun getDaYunDiZhi3HeString(dyDZ: DiZhi, data: BaziData): String {
        val sb = StringBuilder()
        var str = get3HeCheckString(dyDZ, data.yearDizhi, data.monthDizhi, data)
        sb.append(str)
        if (sb.isEmpty()) {
            str = get3HeCheckString(dyDZ, data.yearDizhi, data.dayDizhi, data)
            sb.append(str)
        }
        if (sb.isEmpty()) {
            str = get3HeCheckString(dyDZ, data.yearDizhi, data.hourDizhi, data)
            sb.append(str)
        }
        if (sb.isEmpty()) {
            str = get3HeCheckString(dyDZ, data.monthDizhi, data.dayDizhi, data)
            sb.append(str)
        }
        if (sb.isEmpty()) {
            str = get3HeCheckString(dyDZ, data.monthDizhi, data.hourDizhi, data)
            sb.append(str)
        }
        if (sb.isEmpty()) {
            str = get3HeCheckString(dyDZ, data.dayDizhi, data.hourDizhi, data)
            sb.append(str)
        }

//        sb.append(get3HeCheckString(dyDZ, data.yearDizhi, data.monthDizhi, data))
//        sb.append(get3HeCheckString(dyDZ, data.yearDizhi, data.dayDizhi, data))
//        sb.append(get3HeCheckString(dyDZ, data.yearDizhi, data.hourDizhi, data))
//        sb.append(get3HeCheckString(dyDZ, data.monthDizhi, data.dayDizhi, data))
//        sb.append(get3HeCheckString(dyDZ, data.monthDizhi, data.hourDizhi, data))
//        sb.append(get3HeCheckString(dyDZ, data.dayDizhi, data.hourDizhi, data))
        return sb.toString()
    }

    @Composable
    fun get6HeCheckString(
        dyDZ: DiZhi, dz: DiZhi, columnPosition: ColumnPosition, data: BaziData
    ): String {
        val sb = StringBuilder()
        var wx = WuXing.WUXING_MU
        if (DiZhiUtil().isDiZhi6He(dyDZ, dz)) {
            wx = DiZhiUtil().get6HeWuXingByDiZhi(dyDZ)

            sb.append(ConstUtil.SPACE)
            sb.append(stringResource(R.string.dayun_dizhi_label))
            sb.append(
                DiZhiUtil().getDiZhi6HeText(
                    dyDZ, ColumnPosition.COLUMN_DA_YUN, dz, columnPosition
                )
            )

            if (YongShenUtil().isWuXingXiYongShen(wx, data)) {
                data.daYunWeight += ConstUtil.DIZHI_LIUHE_WEIGHT
                sb.append("[")
                sb.append(stringResource(R.string.app_bazi_xiyong_shen))
                sb.append("]")
                sb.append(" ")
                sb.append(stringResource(R.string.app_bazi_weight_label))
                sb.append("(")
                sb.append(ConstUtil.ADD_SYMBOL)
                sb.append(ConstUtil.DIZHI_LIUHE_WEIGHT)
                sb.append(")")
            } else if (YongShenUtil().isWuXingJiShen(wx, data)) {
                data.daYunWeight -= ConstUtil.DIZHI_LIUHE_WEIGHT
                sb.append("[")
                sb.append(stringResource(R.string.app_bazi_ji_shen))
                sb.append("]")
                sb.append(" ")
                sb.append(stringResource(R.string.app_bazi_weight_label))
                sb.append("(")
                sb.append(ConstUtil.SUB_SYMBOL)
                sb.append(ConstUtil.DIZHI_LIUHE_WEIGHT)
                sb.append(")")
            } else {
                data.daYunWeight -= ConstUtil.XIAN_SHEN_WEIGHT
                sb.append("[")
                sb.append(stringResource(R.string.app_bazi_xian_shen))
                sb.append("]")
                sb.append(" ")
                sb.append(stringResource(R.string.app_bazi_weight_label))
                sb.append("(")
                sb.append(ConstUtil.ADD_SYMBOL)
                sb.append(ConstUtil.XIAN_SHEN_WEIGHT)
                sb.append(")")
            }
            sb.append("\n")
        }
        return sb.toString()
    }

    @Composable
    fun getDaYunDiZhi6HeString(dyDZ: DiZhi, data: BaziData): String {
        val sb = StringBuilder()
        sb.append(get6HeCheckString(dyDZ, data.yearDizhi, ColumnPosition.COLUMN_YEAR, data))
        sb.append(get6HeCheckString(dyDZ, data.monthDizhi, ColumnPosition.COLUMN_MONTH, data))
        sb.append(get6HeCheckString(dyDZ, data.dayDizhi, ColumnPosition.COLUMN_DAY, data))
        sb.append(get6HeCheckString(dyDZ, data.hourDizhi, ColumnPosition.COLUMN_HOUR, data))
        return sb.toString()
    }

    @Composable
    fun analyzeDaYunString(dyTG: TianGan, dyDZ: DiZhi, data: BaziData): String {
        val sb = StringBuilder()
        data.daYunWeight = ConstUtil.ZERO_WEIGHT

        sb.append(YongShenUtil().getTianGanXiJiWeightString(dyTG, data))
        sb.append(YongShenUtil().getDiZhiXiJiWeightString(dyDZ, data))
        sb.append(getDaYunTianGan5HeString(dyTG, data))
//        sb.append(getDaYunTianGanTouDiZhiString(dyTG, data))

        sb.append(getDaYunDiZhiTouchuCheckString(dyTG, dyDZ, data))
        sb.append(getDaYunDiZhiChongString(dyDZ, data))
        sb.append(getDaYunDiZhi3HuiString(dyDZ, data))
        sb.append(getDaYunDiZhi3HeString(dyDZ, data))
        sb.append(getDaYunDiZhi6HeString(dyDZ, data))
        sb.append(ConstUtil.SPACE)
        sb.append(stringResource(R.string.app_bazi_weight_label))
        sb.append("(")
        sb.append(data.daYunWeight)
        sb.append(")")

        return sb.toString()
    }

    @Composable
    fun getDaYunJiXiongString(data: BaziData): String {
        val sb = StringBuilder()
        sb.append("[")
        if (data.daYunWeight > 2) {
            sb.append(stringResource(R.string.app_bazi_jixiong_1))
        } else if (data.daYunWeight > 0 && data.daYunWeight <= 2) {
            sb.append(stringResource(R.string.app_bazi_jixiong_2))
        } else if (data.daYunWeight < 0 && data.daYunWeight >= -2) {
            sb.append(stringResource(R.string.app_bazi_jixiong_4))
        } else if (data.daYunWeight < -2) {
            sb.append(stringResource(R.string.app_bazi_jixiong_5))
        } else {
            sb.append(stringResource(R.string.app_bazi_jixiong_3))
        }
        sb.append("]")
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
        var tgdz: TianGanDiZhi

        for (i in startIndex..endIndex) {
            tgdz = DaYunUtil().getDaYun(i, data)
            tg = tgdz.tg
            dz = tgdz.dz
            str = analyzeDaYunString(tg, dz, data)

            builder.append(BaziUtil().getTianGanLabel(tg))
            builder.append(BaziUtil().getDizhiLabel(dz))
            builder.append("(")
            builder.append(data.daYunFirstYear + (i - 1) * 10)
            builder.append(") ")
            builder.append(getDaYunJiXiongString(data))
            builder.append("\n")
            builder.append(str)
            builder.append("\n")
        }
        return builder.toString()
    }

    fun getDaYunFirstYear(data: BaziData): TianGanDiZhi {
        val tmpData = BaziData(
            data.daYunFirstYear,
            data.daYunStartMonth,
            data.birthDateDay,
            data.birthHour,
            data.gender
        )
        //calculate year and month
        BaziPaiPanUtil().calculateBazi(
            data.daYunFirstYear, data.daYunStartMonth, data.birthDateDay, data.birthHour, tmpData
        )
        return TianGanDiZhi(tmpData.yearTiangan, tmpData.yearDizhi)
    }

    /**
     * 根据当前时间，计算所在大运的天干地支与年龄区间
     * 返回示例：庚辰大运（28–37岁）
     */
    @Composable
    fun currentDaYunWithAge(data: BaziData): String {
        val today = LocalDate.now()
//        println("[Rick]data=$data")
//        println("data.daYunStartYear=$data.daYunStartYear")
//        println("data.daYunStartMonth=$data.daYunStartMonth")
//        println("data.daYunStartDay=$data.daYunStartDay")
        val daYunStart = LocalDate.of(
            data.daYunFirstYear, data.daYunFirstMonth, 1
        )
        println("[Rick]daYunStart=$daYunStart")
        println("[Rick]today=$today")

        val birthDate = LocalDate.of(
            data.birthDateYear, data.birthDateMonth, data.birthDateDay
        )

        // 如果还没到大运开始时间
        if (today.isBefore(daYunStart)) {
            return "尚未起运"
        }

        // 计算当前处于第几个大运（index 从 1 开始）
        val yearsSinceStart = today.year - daYunStart.year
        val daYunIndex = (yearsSinceStart / 10) + 1  // 每运10年

        // 取该大运天干地支
        val daYunGz = getDaYun(daYunIndex, data)
        val ganZhiStr =
            "${TianGanUtil().getTianGanText(daYunGz.tg)}${DiZhiUtil().getDiZhiText(daYunGz.dz)}"

        // 大运起始年龄（按出生年算虚龄差，业内常用）
        val startAge = daYunStart.year - data.birthDateYear
        val endAge = startAge + 9  // 每运10年，显示闭区间

        return "${ganZhiStr}大运（${startAge}–${endAge}岁）"
    }

    fun calculateDaYunStartSeconds(
        startJieQi: SolarTime, endJieQi: SolarTime, ownerTime: SolarTime, data: BaziData
    ) {
        var seconds: Int = 0
        if (isDaYunForward(data)) {
            seconds = endJieQi.subtract(ownerTime)
        } else {
            seconds = ownerTime.subtract(startJieQi)
        }
        data.daYunStartSeconds = seconds
        var updateData = calcDaYunStartFromSeconds(data, seconds)
        println("[Rick]calculateDaYunStartSeconds() updateData=$updateData")

//        var days = data.daYunStartSeconds / 86400
//        var remainSeconds = data.daYunStartSeconds % 86400
//        var hours = remainSeconds / 3600
//        var remainTime = remainSeconds % 3600
//        var year = days / 3
//        var month = (days % 3) * 4
//        var day = hours * 5
//        day = day + (remainTime / 720)
//        if (day > 30) {
//            month = month + day / 30
//        }
//        day = day % 30
//
//        var remainHours = remainTime % 720
//        var hour = remainHours / 30
//
//        data.daYunStartYear = year
//        data.daYunStartMonth = month
//        data.daYunStartDay = day
//
//        var startYear = data.birthDateYear + data.daYunStartYear
//        var startMonth = data.birthDateMonth + data.daYunStartMonth
//        if (startMonth >= 13) {
//            startYear += 1
//            startMonth -= 12
//        }
//        data.daYunFirstYear = startYear
//        data.daYunFirstMonth = startMonth

//        println("daYunStartSeconds=${data.daYunStartSeconds}")
    }

    /**
     * 根据 daYunStartSeconds（出生到最近节气的秒数，已含顺逆），
     * 计算并更新 BaziData 中的大运起始信息
     */
    fun calcDaYunStartFromSeconds(
        data: BaziData,
        daYunStartSeconds: Int
    ): BaziData {

        // 1. 秒 → 天（带小数）
        val totalDays = daYunStartSeconds / 86400.0      // 86400 = 24 * 60 * 60
        val totalHours = daYunStartSeconds / 3600.0

        // 2. 起运年数：3 天 = 1 岁（年）
        val startYears = (totalDays / 3).toInt()

        // 3. 剩余天数用于折算月数
        val remainDays = totalDays % 3

        // 1 天 = 4 个月 → 剩余月数（可能带小数）
        val remainMonthsExact = remainDays * 4

        // 取整月数
        val startMonths = remainMonthsExact.toInt()

        println("[Dayun] daYunStartSeconds=$daYunStartSeconds, totalDays=$totalDays, totalHours=$totalHours, startYears=$startYears, startMonths=$startMonths")

        // 若还有不足 1 天的秒数，可继续折算（保守做法：忽略 <1 天的小数，或向下取整）
        // 这里按行业常用：只取到整月，不再往下拆日（排盘够用）

        // 4. 出生时间
        val birthDateTime = LocalDateTime.of(
            data.birthDateYear,
            data.birthDateMonth,
            data.birthDateDay,
            data.birthHour,
            data.birthMinute,
            0
        )

        // 5. 起运的公历时间 = 出生时间 + startYears 年 + startMonths 月
        var firstYearMonth = YearMonth.of(birthDateTime.year, birthDateTime.month)
            .plusYears(startYears.toLong())
            .plusMonths(startMonths.toLong())

        val daYunFirstYear = firstYearMonth.year
        val daYunFirstMonth = firstYearMonth.monthValue

        // 6. 回写 BaziData（注意：data 属性目前是 var，可直接赋值）
        data.daYunStartSeconds = daYunStartSeconds.toInt()
        data.daYunStartYear = startYears
        data.daYunStartMonth = startMonths
        data.daYunFirstYear = daYunFirstYear
        data.daYunFirstMonth = daYunFirstMonth

        return data
    }

}
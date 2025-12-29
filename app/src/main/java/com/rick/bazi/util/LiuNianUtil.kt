package com.rick.bazi.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rick.bazi.R
import com.rick.bazi.data.BaziData
import com.rick.bazi.data.ColumnPosition
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.TianGan
import com.rick.bazi.data.TianGanDiZhi
import com.rick.bazi.data.WuXing

class LiuNianUtil {

    @Composable
    fun get6HeCheckString(lnDZ : DiZhi, dz : DiZhi, columnPosition: ColumnPosition, data: BaziData): String {
        val sb = StringBuilder()
        var wx = WuXing.WUXING_MU
        if (DiZhiUtil().isDiZhi6He(lnDZ, dz)) {
            wx = DiZhiUtil().get6HeWuXingByDiZhi(lnDZ)

            sb.append(ConstUtil.SPACE)
            sb.append(stringResource(R.string.liunian_dizhi_label))
            sb.append(DiZhiUtil().getDiZhi6HeText(lnDZ, ColumnPosition.COLUMN_LIU_YEAR, dz, columnPosition))

            if (YongShenUtil().isWuXingXiYongShen(wx, data)) {
                data.liuNianWeight += ConstUtil.DIZHI_LIUHE_WEIGHT
                sb.append("[")
                sb.append(stringResource(R.string.app_bazi_xiyong_shen))
                sb.append("]")
                sb.append(" ")
                sb.append(stringResource(R.string.app_bazi_weight_label))
                sb.append("(")
                sb.append(ConstUtil.ADD_SYMBOL)
                sb.append(ConstUtil.DIZHI_LIUHE_WEIGHT)
                sb.append(")")
            }else if (YongShenUtil().isWuXingJiShen(wx, data)) {
                data.liuNianWeight -= ConstUtil.DIZHI_LIUHE_WEIGHT
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
                data.liuNianWeight += ConstUtil.XIAN_SHEN_WEIGHT
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
    fun getLiuNianDiZhi6HeString(lnDZ : DiZhi, dyDZ : DiZhi, data: BaziData): String {
        val sb = StringBuilder()
        sb.append(get6HeCheckString(lnDZ, data.yearDizhi, ColumnPosition.COLUMN_YEAR, data))
        sb.append(get6HeCheckString(lnDZ, data.monthDizhi, ColumnPosition.COLUMN_MONTH, data))
        sb.append(get6HeCheckString(lnDZ, data.dayDizhi, ColumnPosition.COLUMN_DAY, data))
        sb.append(get6HeCheckString(lnDZ, data.hourDizhi, ColumnPosition.COLUMN_HOUR, data))
        sb.append(get6HeCheckString(lnDZ, dyDZ, ColumnPosition.COLUMN_DA_YUN, data))
        return sb.toString()
    }


    @Composable
    fun get3HeCheckString(lnDZ : DiZhi, dz1 : DiZhi, dz2 : DiZhi, data: BaziData): String {
        val sb = StringBuilder()
        var wx = WuXing.WUXING_MU
        if (DiZhiUtil().isDiZhiSanHe(lnDZ, dz1, dz2)) {
            wx = DiZhiUtil().get3HeWuXingByDiZhi(lnDZ)

            sb.append(ConstUtil.SPACE)
            sb.append(stringResource(R.string.liunian_dizhi_label))
            sb.append(DiZhiUtil().getSanHeStringByWuXing(wx))

            if (YongShenUtil().isWuXingXiYongShen(wx, data)) {
                data.liuNianWeight += ConstUtil.DIZHI_SANHE_WEIGHT
                sb.append("[")
                sb.append(stringResource(R.string.app_bazi_xiyong_shen))
                sb.append("]")
                sb.append(" ")
                sb.append(stringResource(R.string.app_bazi_weight_label))
                sb.append("(")
                sb.append(ConstUtil.ADD_SYMBOL)
                sb.append(ConstUtil.DIZHI_SANHE_WEIGHT)
                sb.append(")")
            }else if (YongShenUtil().isWuXingJiShen(wx, data)) {
                data.liuNianWeight -= ConstUtil.DIZHI_SANHE_WEIGHT
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
                data.liuNianWeight += ConstUtil.XIAN_SHEN_WEIGHT
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
    fun getLiuNianDiZhi3HeString(lnDZ : DiZhi, dyDZ : DiZhi, data: BaziData): String {
        val sb = StringBuilder()
        var str = get3HeCheckString(lnDZ, data.yearDizhi, data.monthDizhi, data)
        sb.append(str)
        if(sb.isEmpty()){
            str = get3HeCheckString(lnDZ, data.yearDizhi, data.dayDizhi, data)
            sb.append(str)
        }
        if(sb.isEmpty()){
            str = get3HeCheckString(lnDZ, data.yearDizhi, data.hourDizhi, data)
            sb.append(str)
        }
        if(sb.isEmpty()){
            str = get3HeCheckString(lnDZ, data.monthDizhi, data.dayDizhi, data)
            sb.append(str)
        }
        if(sb.isEmpty()){
            str = get3HeCheckString(lnDZ, data.monthDizhi, data.hourDizhi, data)
            sb.append(str)
        }
        if(sb.isEmpty()){
            str = get3HeCheckString(lnDZ, data.dayDizhi, data.hourDizhi, data)
            sb.append(str)
        }

        //add liunian combo
        if(sb.isEmpty()){
            str = get3HeCheckString(lnDZ, dyDZ, data.yearDizhi, data)
            sb.append(str)
        }
        if(sb.isEmpty()){
            str = get3HeCheckString(lnDZ, dyDZ, data.monthDizhi, data)
            sb.append(str)
        }
        if(sb.isEmpty()){
            str = get3HeCheckString(lnDZ, dyDZ, data.dayDizhi, data)
            sb.append(str)
        }
        if(sb.isEmpty()){
            str = get3HeCheckString(lnDZ, dyDZ, data.hourDizhi, data)
            sb.append(str)
        }

        return sb.toString()
    }

    @Composable
    fun get3HuiCheckString(lnDZ : DiZhi, dz1 : DiZhi, dz2 : DiZhi, data: BaziData): String {
        val sb = StringBuilder()
        var wx = WuXing.WUXING_MU
        if(DiZhiUtil().isDiZhiSanHui(lnDZ, dz1, dz2)){
            wx = DiZhiUtil().get3HuiWuXingByDiZhi(lnDZ)
            sb.append(ConstUtil.SPACE)
            sb.append(stringResource(R.string.liunian_dizhi_label))
            sb.append(DiZhiUtil().getSanHuiStringByWuXing(wx))
            if(YongShenUtil().isWuXingXiYongShen(wx, data)){
                data.liuNianWeight+= ConstUtil.DIZHI_SANHUI_WEIGHT
                sb.append("[")
                sb.append(stringResource(R.string.app_bazi_xiyong_shen))
                sb.append("]")
                sb.append(" ")
                sb.append(stringResource(R.string.app_bazi_weight_label))
                sb.append("(")
                sb.append(ConstUtil.ADD_SYMBOL)
                sb.append(ConstUtil.DIZHI_SANHUI_WEIGHT)
                sb.append(")")
            }else if(YongShenUtil().isWuXingJiShen(wx, data)){
                data.liuNianWeight-= ConstUtil.DIZHI_SANHUI_WEIGHT
                sb.append("[")
                sb.append(stringResource(R.string.app_bazi_ji_shen))
                sb.append("]")
                sb.append(" ")
                sb.append(stringResource(R.string.app_bazi_weight_label))
                sb.append("(")
                sb.append(ConstUtil.SUB_SYMBOL)
                sb.append(ConstUtil.DIZHI_SANHUI_WEIGHT)
                sb.append(")")
            }else{
                data.liuNianWeight+= ConstUtil.XIAN_SHEN_WEIGHT
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
    fun getLiuNianDiZhi3HuiString(lnDZ : DiZhi, dyDZ : DiZhi, data: BaziData): String {
        val sb = StringBuilder()
        var str = get3HuiCheckString(lnDZ, data.yearDizhi, data.monthDizhi, data)
        sb.append(str)
        if(sb.isEmpty()){
            str = get3HuiCheckString(lnDZ, data.yearDizhi, data.dayDizhi, data)
            sb.append(str)
        }
        if(sb.isEmpty()){
            str = get3HuiCheckString(lnDZ, data.yearDizhi, data.hourDizhi, data)
            sb.append(str)
        }
        if(sb.isEmpty()){
            str = get3HuiCheckString(lnDZ, data.monthDizhi, data.dayDizhi, data)
            sb.append(str)
        }
        if(sb.isEmpty()){
            str = get3HuiCheckString(lnDZ, data.monthDizhi, data.hourDizhi, data)
            sb.append(str)
        }
        if(sb.isEmpty()){
            str = get3HuiCheckString(lnDZ, data.dayDizhi, data.hourDizhi, data)
            sb.append(str)
        }

        //add liunian + dayun combo
        if(sb.isEmpty()){
            str = get3HuiCheckString(lnDZ, dyDZ, data.yearDizhi, data)
            sb.append(str)
        }
        if(sb.isEmpty()){
            str = get3HuiCheckString(lnDZ, dyDZ, data.monthDizhi, data)
            sb.append(str)
        }
        if(sb.isEmpty()){
            str = get3HuiCheckString(lnDZ, dyDZ, data.dayDizhi, data)
            sb.append(str)
        }
        if(sb.isEmpty()){
            str = get3HuiCheckString(lnDZ, dyDZ, data.hourDizhi, data)
            sb.append(str)
        }
        return sb.toString()
    }

    @Composable
    fun getLiuNianChongString(dyDZ : DiZhi, dz : DiZhi, columnPosition: ColumnPosition, data: BaziData): String {
        val sb = StringBuilder()
        if(DiZhiUtil().isDiZhiChong(dyDZ, dz)){
            sb.append(ConstUtil.SPACE)
            sb.append(stringResource(R.string.liunian_dizhi_label))
            sb.append(WuXingUtil().getDiZhiWuXingString(dyDZ))
            sb.append(stringResource(R.string.dizhi_chong_label))
            sb.append(BaziColumnUtil().getBaziColumnString(columnPosition, false))
            sb.append(WuXingUtil().getDiZhiWuXingString(dz))

            if(YongShenUtil().isDiZhiYongShen(dz, data)){
                sb.append("[")
                sb.append(stringResource(R.string.app_bazi_yong_shen))
                sb.append("]")
                sb.append(" ")
                sb.append(stringResource(R.string.app_bazi_weight_label))

                data.liuNianWeight -= ConstUtil.CHONG_YONG_SHEN_WEIGHT
                sb.append("(").append(ConstUtil.SUB_SYMBOL).append(ConstUtil.CHONG_YONG_SHEN_WEIGHT).append(")")
            }else if(YongShenUtil().isDiZhiXiShen(dz, data)){
                sb.append("[")
                sb.append(stringResource(R.string.app_bazi_xi_shen))
                sb.append("]")
                sb.append(" ")
                sb.append(stringResource(R.string.app_bazi_weight_label))

                data.liuNianWeight -= ConstUtil.CHONG_XI_SHEN_WEIGHT
                sb.append("(")
                sb.append(ConstUtil.SUB_SYMBOL)
                sb.append(ConstUtil.CHONG_XI_SHEN_WEIGHT)
                sb.append(")")
            }else if(YongShenUtil().isDiZhiJiShen(dz, data)){
                sb.append("[")
                sb.append(stringResource(R.string.app_bazi_ji_shen))
                sb.append("]")
                sb.append(" ")
                sb.append(stringResource(R.string.app_bazi_weight_label))

                data.liuNianWeight += ConstUtil.CHONG_JI_SHEN_WEIGHT
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
    fun getLiuNianDiZhiChongString(lnDZ : DiZhi, dyDZ : DiZhi, data: BaziData): String {
        val sb = StringBuilder()
        sb.append(getLiuNianChongString(lnDZ, data.yearDizhi, ColumnPosition.COLUMN_YEAR, data))
        sb.append(getLiuNianChongString(lnDZ, data.monthDizhi, ColumnPosition.COLUMN_MONTH, data))
        sb.append(getLiuNianChongString(lnDZ, data.dayDizhi, ColumnPosition.COLUMN_DAY, data))
        sb.append(getLiuNianChongString(lnDZ, data.hourDizhi, ColumnPosition.COLUMN_HOUR, data))
        sb.append(getLiuNianChongString(lnDZ, dyDZ, ColumnPosition.COLUMN_DA_YUN, data))
        return sb.toString()
    }

    @Composable
    fun getLiuNianDiZhiTouXiJiWeightString(dyDZ: DiZhi, data: BaziData): String {
        val sb = StringBuilder()
        var isYongShen = YongShenUtil().isDiZhiYongShen(dyDZ, data)
        var isXiShen = YongShenUtil().isDiZhiXiShen(dyDZ, data)
        var isJiShen = YongShenUtil().isDiZhiJiShen(dyDZ, data)
        var xjStr = ""

        if (isYongShen) {
            xjStr = "[" + stringResource(R.string.app_bazi_yong_shen) + "]"
        } else if (isXiShen) {
            xjStr = "[" + stringResource(R.string.app_bazi_xi_shen) + "]"
        } else if (isJiShen) {
            xjStr = "[" + stringResource(R.string.app_bazi_ji_shen) + "]"
        } else {
            xjStr = "[" + stringResource(R.string.app_bazi_xian_shen) + "]"
        }

        sb.append(xjStr)
        sb.append(" ")
        sb.append(stringResource(R.string.app_bazi_weight_label))

        if (isJiShen) {
            data.liuNianWeight -= ConstUtil.JI_SHEN_WEIGHT
            sb.append("(").append(ConstUtil.SUB_SYMBOL).append(ConstUtil.JI_SHEN_WEIGHT).append(")")
        } else if (isYongShen) {
            data.liuNianWeight += ConstUtil.YONG_SHEN_WEIGHT
            sb.append("(").append(ConstUtil.ADD_SYMBOL).append(ConstUtil.YONG_SHEN_WEIGHT)
                .append(")")
        } else if (isXiShen) {
            data.liuNianWeight += ConstUtil.XI_SHEN_WEIGHT
            sb.append("(").append(ConstUtil.ADD_SYMBOL).append(ConstUtil.XI_SHEN_WEIGHT).append(")")
        } else {
            data.liuNianWeight += ConstUtil.XIAN_SHEN_WEIGHT
            sb.append("(").append(ConstUtil.ADD_SYMBOL).append(ConstUtil.XIAN_SHEN_WEIGHT)
                .append(")")
        }
        sb.append("\n")

        return sb.toString()
    }

    @Composable
    fun getLiuNianDiZhiTouCheckString(dyTG : TianGan, dz : DiZhi, position: ColumnPosition, data: BaziData): String {
        val sb = StringBuilder()
        var tg = DiZhiUtil().getTianGan(dz)
        if(tg == dyTG){
            sb.append(ConstUtil.SPACE)
            sb.append(BaziColumnUtil().getBaziColumnString(position, false))
            sb.append(WuXingUtil().getDiZhiWuXingString(dz))
            sb.append(stringResource(R.string.dizhi_touchu_label))
            sb.append(stringResource(R.string.liunian_tiangan_label))
            sb.append(" ")
            sb.append(getLiuNianDiZhiTouXiJiWeightString(dz, data))
        }
        return sb.toString()
    }

    @Composable
    fun getLiuNianDiZhiTouchuCheckString(lnTG : TianGan, dyDZ : DiZhi, lnDZ : DiZhi, data: BaziData): String {
        val sb = StringBuilder()
        sb.append(getLiuNianDiZhiTouCheckString(lnTG, data.yearDizhi, ColumnPosition.COLUMN_YEAR, data))
        sb.append(getLiuNianDiZhiTouCheckString(lnTG, data.monthDizhi, ColumnPosition.COLUMN_MONTH, data))
        sb.append(getLiuNianDiZhiTouCheckString(lnTG, data.dayDizhi, ColumnPosition.COLUMN_DAY, data))
        sb.append(getLiuNianDiZhiTouCheckString(lnTG, data.hourDizhi, ColumnPosition.COLUMN_HOUR, data))
        sb.append(getLiuNianDiZhiTouCheckString(lnTG, dyDZ, ColumnPosition.COLUMN_DA_YUN, data))
        sb.append(getLiuNianDiZhiTouCheckString(lnTG, lnDZ, ColumnPosition.COLUMN_LIU_YEAR, data))
        return sb.toString()
    }

    @Composable
    fun getTianGan5HeString(dyTG : TianGan, tg : TianGan, columnPosition: ColumnPosition, data: BaziData): String {
        val sb = StringBuilder()
        var wx = WuXing.WUXING_MU
        if(TianGanUtil().isTianGanHe(dyTG, tg)){
            wx = TianGanUtil().getTianGanHeWuXing(dyTG)
            sb.append(ConstUtil.SPACE)
            sb.append(stringResource(R.string.liunian_tiangan_label))
            sb.append(stringResource(R.string.app_and_label))
            sb.append(BaziColumnUtil().getBaziColumnString(columnPosition, true))
            sb.append(" ")
            sb.append(TianGanUtil().getTianGan5HeLabel(dyTG))

            if(YongShenUtil().isWuXingXiYongShen(wx, data)){
                data.liuNianWeight+= ConstUtil.TIANGAN_5HE_WEIGHT
                sb.append("[")
                sb.append(stringResource(R.string.app_bazi_xiyong_shen))
                sb.append("] ")
                sb.append(stringResource(R.string.app_bazi_weight_label))
                sb.append("(").append(ConstUtil.ADD_SYMBOL)
                sb.append(ConstUtil.TIANGAN_5HE_WEIGHT).append(")")
            }else if(YongShenUtil().isWuXingJiShen(wx, data)){
                data.liuNianWeight-= ConstUtil.TIANGAN_5HE_WEIGHT
                sb.append("[")
                sb.append(stringResource(R.string.app_bazi_ji_shen))
                sb.append("] ")
                sb.append(stringResource(R.string.app_bazi_weight_label))
                sb.append("(").append(ConstUtil.SUB_SYMBOL)
                sb.append(ConstUtil.TIANGAN_5HE_WEIGHT).append(")")
            }else{
                data.liuNianWeight+= ConstUtil.XIAN_SHEN_WEIGHT
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
    fun getTianGan5HeString(dyTG : TianGan, lnTG : TianGan, data: BaziData): String {
        val sb = StringBuilder()
        sb.append(getTianGan5HeString(lnTG, data.yearTiangan, ColumnPosition.COLUMN_YEAR, data))
        sb.append(getTianGan5HeString(lnTG, data.monthTiangan, ColumnPosition.COLUMN_MONTH, data))
        sb.append(getTianGan5HeString(lnTG, data.hourTiangan, ColumnPosition.COLUMN_HOUR, data))
        sb.append(getTianGan5HeString(lnTG, dyTG, ColumnPosition.COLUMN_DA_YUN, data))
        return sb.toString()
    }

    @Composable
    fun getDaYunLiuNianJiXiongString(data: BaziData): String {
        val sb = StringBuilder()
        val weight = data.liuNianWeight
        sb.append("[")
        if(weight >=8){
            sb.append(stringResource(R.string.app_bazi_jixiong_1))
        }else if(weight < 8 && weight >= 4){
            sb.append(stringResource(R.string.app_bazi_jixiong_2))
        }else if(weight <= -2 && weight > -8){
            sb.append(stringResource(R.string.app_bazi_jixiong_4))
        }else if(weight <= -8){
            sb.append(stringResource(R.string.app_bazi_jixiong_5))
        }else{
            sb.append(stringResource(R.string.app_bazi_jixiong_3))
        }
        sb.append("]")
        return sb.toString()
    }

    @Composable
    fun getLiuNianWeightString(dyTG : TianGan, dyDZ : DiZhi, lnTG : TianGan, lnDZ : DiZhi, data: BaziData): String {
        val sb = StringBuilder()
        sb.append(getLiuNianTianGanXiJiWeightString(lnTG, data))
        sb.append(getLiuNianDiZhiXiJiWeightString(lnDZ, data))
        sb.append(getTianGan5HeString(dyTG, lnTG, data))
        sb.append(getLiuNianDiZhiTouchuCheckString(lnTG, dyDZ, lnDZ, data))
        sb.append(getLiuNianDiZhiChongString(lnDZ, dyDZ, data))
        sb.append(getLiuNianDiZhi3HuiString(lnDZ, dyDZ, data))
        sb.append(getLiuNianDiZhi3HeString(lnDZ, dyDZ, data))
        sb.append(getLiuNianDiZhi6HeString(lnDZ, dyDZ, data))
        return sb.toString()
    }

    @Composable
    fun analyzeDaYunLiuNianString(dyTG : TianGan, dyDZ : DiZhi, lnTG : TianGan, lnDZ : DiZhi, data: BaziData, lnYear : Int): String {
        val sb = StringBuilder()
        var str = ""
        data.liuNianWeight = ConstUtil.ZERO_WEIGHT

        str = getLiuNianWeightString(dyTG, dyDZ, lnTG, lnDZ, data)

        sb.append(ConstUtil.SPACE)
        sb.append(TianGanUtil().getTianGanText(lnTG))
        sb.append(DiZhiUtil().getDiZhiText(lnDZ))
        sb.append(stringResource(R.string.bazi_liunian))
        sb.append("(").append(lnYear).append(")")
        sb.append(" ")
//        getDaYunLiuNianWeight(dyTG, dyDZ, lnTG, lnDZ, data)
        sb.append(stringResource(R.string.app_bazi_weight_label))
        sb.append("(")
        sb.append(data.liuNianWeight)
        sb.append(")")
        sb.append(" ")
        sb.append(getDaYunLiuNianJiXiongString(data))
        sb.append("\n")
        sb.append(str)
        sb.append("\n")

        return sb.toString()
    }

    @Composable
    fun getLiuNianTianGanXiJiWeightString(lnTG: TianGan, data: BaziData): String {
        val sb = StringBuilder()
        var isYongShen = YongShenUtil().isTianGanYongShen(lnTG, data)
        var isXiShen = YongShenUtil().isTianGanXiShen(lnTG, data)
        var isJiShen = YongShenUtil().isTianGanJiShen(lnTG, data)
        var xjStr = ""
        sb.append(ConstUtil.SPACE)

        if (isYongShen) {
            xjStr = "[" + stringResource(R.string.app_bazi_yong_shen) + "]"
        } else if (isXiShen) {
            xjStr = "[" + stringResource(R.string.app_bazi_xi_shen) + "]"
        } else if (isJiShen) {
            xjStr = "[" + stringResource(R.string.app_bazi_ji_shen) + "]"
        } else {
            xjStr = "[" + stringResource(R.string.app_bazi_xian_shen) + "]"
        }

        sb.append(stringResource(R.string.liunian_tiangan_label))
        sb.append(WuXingUtil().getTianGanWuXingString(lnTG))
        sb.append(xjStr)
        sb.append(" ")
        sb.append(stringResource(R.string.app_bazi_weight_label))

        if (isJiShen) {
            data.liuNianWeight -= ConstUtil.JI_SHEN_WEIGHT
            sb.append("(").append(ConstUtil.SUB_SYMBOL).append(ConstUtil.JI_SHEN_WEIGHT).append(")")
        } else if (isYongShen) {
            data.liuNianWeight += ConstUtil.YONG_SHEN_WEIGHT
            sb.append("(").append(ConstUtil.ADD_SYMBOL).append(ConstUtil.YONG_SHEN_WEIGHT)
                .append(")")
        } else if (isXiShen) {
            data.liuNianWeight += ConstUtil.XI_SHEN_WEIGHT
            sb.append("(").append(ConstUtil.ADD_SYMBOL).append(ConstUtil.XI_SHEN_WEIGHT).append(")")
        } else {
            data.liuNianWeight += ConstUtil.XIAN_SHEN_WEIGHT
            sb.append("(").append(ConstUtil.ADD_SYMBOL).append(ConstUtil.XIAN_SHEN_WEIGHT)
                .append(")")
        }
        sb.append("\n")

        return sb.toString()
    }

    @Composable
    fun getLiuNianDiZhiXiJiWeightString(lnDZ: DiZhi, data: BaziData): String {
        val sb = StringBuilder()
        var isYongShen = YongShenUtil().isDiZhiYongShen(lnDZ, data)
        var isXiShen = YongShenUtil().isDiZhiXiShen(lnDZ, data)
        var isJiShen = YongShenUtil().isDiZhiJiShen(lnDZ, data)
        var xjStr = ""
        sb.append(ConstUtil.SPACE)

        if (isYongShen) {
            xjStr = "[" + stringResource(R.string.app_bazi_yong_shen) + "]"
        } else if (isXiShen) {
            xjStr = "[" + stringResource(R.string.app_bazi_xi_shen) + "]"
        } else if (isJiShen) {
            xjStr = "[" + stringResource(R.string.app_bazi_ji_shen) + "]"
        } else {
            xjStr = "[" + stringResource(R.string.app_bazi_xian_shen) + "]"
        }

        sb.append(stringResource(R.string.liunian_dizhi_label))
        sb.append(WuXingUtil().getDiZhiWuXingString(lnDZ))
        sb.append(xjStr)
        sb.append(" ")
        sb.append(stringResource(R.string.app_bazi_weight_label))

        if (isJiShen) {
            data.liuNianWeight -= ConstUtil.JI_SHEN_WEIGHT
            sb.append("(").append(ConstUtil.SUB_SYMBOL).append(ConstUtil.JI_SHEN_WEIGHT).append(")")
        } else if (isYongShen) {
            data.liuNianWeight += ConstUtil.YONG_SHEN_WEIGHT
            sb.append("(").append(ConstUtil.ADD_SYMBOL).append(ConstUtil.YONG_SHEN_WEIGHT)
                .append(")")
        } else if (isXiShen) {
            data.liuNianWeight += ConstUtil.XI_SHEN_WEIGHT
            sb.append("(").append(ConstUtil.ADD_SYMBOL).append(ConstUtil.XI_SHEN_WEIGHT).append(")")
        } else {
            data.liuNianWeight += ConstUtil.XIAN_SHEN_WEIGHT
            sb.append("(").append(ConstUtil.ADD_SYMBOL).append(ConstUtil.XIAN_SHEN_WEIGHT)
                .append(")")
        }
        sb.append("\n")

        return sb.toString()
    }

    @Composable
    fun getDaYunLiuNianSummaryString(data: BaziData): String {
        val startIndex = 1
        val endIndex = 12
        var tg : TianGan
        var dz : DiZhi
        val sb = StringBuilder()
        var tgdz : TianGanDiZhi
        var dyYear : Int
        var liuNianTG : TianGan
        var liuNianDZ : DiZhi
        var str = ""

        val dytgdz = DaYunUtil().getDaYunFirstYear(data)
        liuNianTG = dytgdz.tg
        liuNianDZ = dytgdz.dz

        for (i in startIndex..endIndex) {
            tgdz = DaYunUtil().getDaYun(i, data)
            tg = tgdz.tg
            dz = tgdz.dz
            sb.append(ConstUtil.SPACE)
            sb.append("########################")
            sb.append("\n")
            sb.append(ConstUtil.SPACE)
            sb.append(BaziUtil().getTianGanLabel(tg))
            sb.append(BaziUtil().getDizhiLabel(dz))
            sb.append(stringResource(R.string.app_bazi_dayun))
            sb.append("(")
            dyYear = data.daYunFirstYear + (i - 1) * 10
            sb.append(dyYear)
            sb.append(") ")
            sb.append("\n")
            sb.append(ConstUtil.SPACE)
            sb.append("########################")
            sb.append("\n")
            for(j in 0 .. 9){
                str = analyzeDaYunLiuNianString(tg, dz,liuNianTG, liuNianDZ, data, dyYear + j)
                sb.append(str)
                liuNianTG = TianGanUtil().getNextTianGan(liuNianTG)
                liuNianDZ = DiZhiUtil().getNextDiZhi(liuNianDZ)
            }

        }
        return sb.toString()
    }
}
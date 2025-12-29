package com.rick.bazi.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rick.bazi.R
import com.rick.bazi.data.BaziData
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.TianGan
import com.rick.bazi.data.TianGanDiZhi

class LiuNianUtil {

    fun getDaYunLiuNianWeight(dyTG : TianGan, dyDZ : DiZhi, lnTG : TianGan, lnDZ : DiZhi, data: BaziData) : String{
        val sb = StringBuilder()
        var isYongShen = YongShenUtil().isTianGanYongShen(dyTG, data)
        var isXiShen = YongShenUtil().isTianGanXiShen(dyTG, data)
        var isJiShen = YongShenUtil().isTianGanJiShen(dyTG, data)

        if (isYongShen) {
            data.liuNianWeight+=ConstUtil.YONG_SHEN_WEIGHT
        } else if (isXiShen) {
            data.liuNianWeight+=ConstUtil.XI_SHEN_WEIGHT
        } else if (isJiShen) {
            data.liuNianWeight-=ConstUtil.JI_SHEN_WEIGHT
        }
        isYongShen = YongShenUtil().isTianGanYongShen(lnTG, data)
        isXiShen = YongShenUtil().isTianGanXiShen(lnTG, data)
        isJiShen = YongShenUtil().isTianGanJiShen(lnTG, data)
        if (isYongShen) {
            data.liuNianWeight+=ConstUtil.YONG_SHEN_WEIGHT
        } else if (isXiShen) {
            data.liuNianWeight+=ConstUtil.XI_SHEN_WEIGHT
        } else if (isJiShen) {
            data.liuNianWeight-=ConstUtil.JI_SHEN_WEIGHT
        }
        isYongShen = YongShenUtil().isDiZhiYongShen(dyDZ, data)
        isXiShen = YongShenUtil().isDiZhiXiShen(dyDZ, data)
        isJiShen = YongShenUtil().isDiZhiJiShen(dyDZ, data)
        if (isYongShen) {
            data.liuNianWeight+=ConstUtil.YONG_SHEN_WEIGHT
        } else if (isXiShen) {
            data.liuNianWeight+=ConstUtil.XI_SHEN_WEIGHT
        } else if (isJiShen) {
            data.liuNianWeight-=ConstUtil.JI_SHEN_WEIGHT
        }
        isYongShen = YongShenUtil().isDiZhiYongShen(lnDZ, data)
        isXiShen = YongShenUtil().isDiZhiXiShen(lnDZ, data)
        isJiShen = YongShenUtil().isDiZhiJiShen(lnDZ, data)
        if (isYongShen) {
            data.liuNianWeight+=ConstUtil.YONG_SHEN_WEIGHT
        } else if (isXiShen) {
            data.liuNianWeight+=ConstUtil.XI_SHEN_WEIGHT
        } else if (isJiShen) {
            data.liuNianWeight-=ConstUtil.JI_SHEN_WEIGHT
        }
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
    fun analyzeDaYunLiuNianString(dyTG : TianGan, dyDZ : DiZhi, lnTG : TianGan, lnDZ : DiZhi, data: BaziData, lnYear : Int): String {
        val sb = StringBuilder()
        data.liuNianWeight = ConstUtil.ZERO_WEIGHT
        sb.append(ConstUtil.SPACE)
        sb.append(TianGanUtil().getTianGanText(lnTG))
        sb.append(DiZhiUtil().getDiZhiText(lnDZ))
        sb.append(stringResource(R.string.bazi_liunian))
        sb.append("(").append(lnYear).append(")")
        sb.append(" ")
        getDaYunLiuNianWeight(dyTG, dyDZ, lnTG, lnDZ, data)
        sb.append(stringResource(R.string.app_bazi_weight_label))
        sb.append("(")
        sb.append(data.liuNianWeight)
        sb.append(")")
        sb.append(" ")
        sb.append(getDaYunLiuNianJiXiongString(data))
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

        val dytgdz = DaYunUtil().getDaYunFirstYear(data)
        liuNianTG = dytgdz.tg
        liuNianDZ = dytgdz.dz

        for (i in startIndex..endIndex) {
            tgdz = DaYunUtil().getDaYun(i, data)
            tg = tgdz.tg
            dz = tgdz.dz
            sb.append(ConstUtil.SPACE)
            sb.append(BaziUtil().getTianGanLabel(tg))
            sb.append(BaziUtil().getDizhiLabel(dz))
            sb.append(stringResource(R.string.app_bazi_dayun))
            sb.append("(")
            dyYear = data.daYunFirstYear + (i - 1) * 10
            sb.append(dyYear)
            sb.append(") ")
            sb.append("\n")
            for(j in 0 .. 9){
                sb.append(analyzeDaYunLiuNianString(tg, dz,liuNianTG, liuNianDZ, data, dyYear + j))
                liuNianTG = TianGanUtil().getNextTianGan(liuNianTG)
                liuNianDZ = DiZhiUtil().getNextDiZhi(liuNianDZ)
            }
            sb.append("\n")
        }
        return sb.toString()
    }
}
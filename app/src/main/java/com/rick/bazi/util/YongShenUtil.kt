package com.rick.bazi.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rick.bazi.R
import com.rick.bazi.data.BaziData
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.DiZhiSanHeInfo
import com.rick.bazi.data.DiZhiSanHuiInfo
import com.rick.bazi.data.ShiShen
import com.rick.bazi.data.TianGan
import com.rick.bazi.data.TiaoHouYongShen
import com.rick.bazi.data.TongGuanYongShen
import com.rick.bazi.data.WuXing

class YongShenUtil {
    val TIANGAN_HEHUA_WEIGHT: Float = 2.5f
    val TIANGAN_HEBAN_WEIGHT: Float = 0.5f
    val TIANGAN_WEIGHT: Float = 1.0f
    val DIZHI_YUQI_WEIGHT: Float = 1.0f
    val DIZHI_BENQI_WEIGHT: Float = 2.0f
    val DIZHI_SANHUI_WEIGHT: Float = 3.0f
    val DIZHI_SANHE_WEIGHT: Float = 2.0f
    val DIZHI_LIUHE_WEIGHT: Float = 1.0f
    val WUXING_STRONG_WEIGHT: Float = 3.5f

    fun resetBaziWuXingWeight(data: BaziData) {
        data.jinWeight = 0f
        data.muWeight = 0f
        data.shuiWeight = 0f
        data.huoWeight = 0f
        data.tuWeight = 0f
    }

    fun calculateBaziWuXingWeight(data: BaziData, wx: WuXing) {
        var weight = 0f
        //tiangan
        if (wx == WuXingUtil().getTianGanWuxing(data.yearTiangan)) {
            weight += TIANGAN_WEIGHT
        }
        if (wx == WuXingUtil().getTianGanWuxing(data.monthTiangan)) {
            weight += TIANGAN_WEIGHT
        }
        if (wx == WuXingUtil().getTianGanWuxing(data.dayTiangan)) {
            weight += TIANGAN_WEIGHT
        }
        if (wx == WuXingUtil().getTianGanWuxing(data.hourTiangan)) {
            weight += TIANGAN_WEIGHT
        }

        //dizhi
        if (wx == WuXingUtil().getDiZhiWuxing(data.yearDizhi)) {
            weight += DIZHI_BENQI_WEIGHT
        }
        if (wx == WuXingUtil().getDiZhiWuxing(data.monthDizhi)) {
            weight += DIZHI_BENQI_WEIGHT
        }
        if (wx == WuXingUtil().getDiZhiWuxing(data.dayDizhi)) {
            weight += DIZHI_BENQI_WEIGHT
        }
        if (wx == WuXingUtil().getDiZhiWuxing(data.hourDizhi)) {
            weight += DIZHI_BENQI_WEIGHT
        }

        //dizhi cang gan
        var cangganArray = DiZhiUtil().getCanggan(data.yearDizhi)
        if (cangganArray.size == 2) {
            if (wx == WuXingUtil().getTianGanWuxing(cangganArray[1])) {
                weight += DIZHI_YUQI_WEIGHT
            }
        }
        if (cangganArray.size == 3) {
            if (wx == WuXingUtil().getTianGanWuxing(cangganArray[1])) {
                weight += DIZHI_YUQI_WEIGHT
            }
            if (wx == WuXingUtil().getTianGanWuxing(cangganArray[2])) {
                weight += DIZHI_YUQI_WEIGHT
            }
        }
        cangganArray = DiZhiUtil().getCanggan(data.monthDizhi)
        if (cangganArray.size == 2) {
            if (wx == WuXingUtil().getTianGanWuxing(cangganArray[1])) {
                weight += DIZHI_YUQI_WEIGHT
            }
        }
        if (cangganArray.size == 3) {
            if (wx == WuXingUtil().getTianGanWuxing(cangganArray[1])) {
                weight += DIZHI_YUQI_WEIGHT
            }
            if (wx == WuXingUtil().getTianGanWuxing(cangganArray[2])) {
                weight += DIZHI_YUQI_WEIGHT
            }
        }
        cangganArray = DiZhiUtil().getCanggan(data.dayDizhi)
        if (cangganArray.size == 2) {
            if (wx == WuXingUtil().getTianGanWuxing(cangganArray[1])) {
                weight += DIZHI_YUQI_WEIGHT
            }
        }
        if (cangganArray.size == 3) {
            if (wx == WuXingUtil().getTianGanWuxing(cangganArray[1])) {
                weight += DIZHI_YUQI_WEIGHT
            }
            if (wx == WuXingUtil().getTianGanWuxing(cangganArray[2])) {
                weight += DIZHI_YUQI_WEIGHT
            }
        }
        cangganArray = DiZhiUtil().getCanggan(data.hourDizhi)
        if (cangganArray.size == 2) {
            if (wx == WuXingUtil().getTianGanWuxing(cangganArray[1])) {
                weight += DIZHI_YUQI_WEIGHT
            }
        }
        if (cangganArray.size == 3) {
            if (wx == WuXingUtil().getTianGanWuxing(cangganArray[1])) {
                weight += DIZHI_YUQI_WEIGHT
            }
            if (wx == WuXingUtil().getTianGanWuxing(cangganArray[2])) {
                weight += DIZHI_YUQI_WEIGHT
            }
        }
        if (wx == WuXing.WUXING_JIN) {
            data.jinWeight = weight
        } else if (wx == WuXing.WUXING_MU) {
            data.muWeight = weight
        } else if (wx == WuXing.WUXING_SHUI) {
            data.shuiWeight = weight
        } else if (wx == WuXing.WUXING_HUO) {
            data.huoWeight = weight
        } else if (wx == WuXing.WUXING_TU) {
            data.tuWeight = weight
        }

        //tian gan 6 he
        val heList = TianGanUtil().getTianGanHeList(data)
        var tgSet: MutableSet<TianGanUtil.TianGanPos> = mutableSetOf()

        var isHeHua = false
        for (info in heList) {
            //remove duplicate position
            if (!tgSet.contains(info.srcTgPos) && !tgSet.contains(info.dstTgPos)) {
                isHeHua = TianGanUtil().isTianGanHeHua(data, info)
                if (isHeHua) {
//                    increaseTianGanHeHuaWeight(data, info)
                } else {
//                    reduceTianGanHeWeight(data, info, TIANGAN_HEBAN_WEIGHT, TIANGAN_HEBAN_WEIGHT)
                }
                tgSet.add(info.srcTgPos)
                tgSet.add(info.dstTgPos)
            }
        }

    }

    fun increaseTianGanHeHuaWeight(data: BaziData, info: TianGanUtil.TianGanaHeInfo) {
        var wx = TianGanUtil().tianGanHeWuXingMap.get(info.srcTg)!!
        if (wx == WuXing.WUXING_JIN) {
            data.jinWeight += TIANGAN_HEHUA_WEIGHT
        } else if (wx == WuXing.WUXING_MU) {
            data.muWeight += TIANGAN_HEHUA_WEIGHT
        } else if (wx == WuXing.WUXING_SHUI) {
            data.shuiWeight += TIANGAN_HEHUA_WEIGHT
        } else if (wx == WuXing.WUXING_HUO) {
            data.huoWeight += TIANGAN_HEHUA_WEIGHT
        } else if (wx == WuXing.WUXING_TU) {
            data.tuWeight += TIANGAN_HEHUA_WEIGHT
        }

        reduceTianGanHeWeight(data, info, TIANGAN_WEIGHT, TIANGAN_WEIGHT)
    }

    fun reduceTianGanHeWeight(
        data: BaziData,
        info: TianGanUtil.TianGanaHeInfo,
        srcTgReduceWeight: Float,
        dstTgReduceWeight: Float
    ) {
        var tg = info.srcTg
        if (WuXingUtil().isTianGanJin(tg)) {
            data.jinWeight -= srcTgReduceWeight
        }
        if (WuXingUtil().isTianGanMu(tg)) {
            data.muWeight -= srcTgReduceWeight
        }
        if (WuXingUtil().isTianGanShui(tg)) {
            data.shuiWeight -= srcTgReduceWeight
        }
        if (WuXingUtil().isTianGanHuo(tg)) {
            data.huoWeight -= srcTgReduceWeight
        }
        if (WuXingUtil().isTianGanTu(tg)) {
            data.tuWeight -= srcTgReduceWeight
        }
        tg = info.dstTg
        if (WuXingUtil().isTianGanJin(tg)) {
            data.jinWeight -= dstTgReduceWeight
        }
        if (WuXingUtil().isTianGanMu(tg)) {
            data.muWeight -= dstTgReduceWeight
        }
        if (WuXingUtil().isTianGanShui(tg)) {
            data.shuiWeight -= dstTgReduceWeight
        }
        if (WuXingUtil().isTianGanHuo(tg)) {
            data.huoWeight -= dstTgReduceWeight
        }
        if (WuXingUtil().isTianGanTu(tg)) {
            data.tuWeight -= dstTgReduceWeight
        }
    }

    fun calculateWuXingWeight(data: BaziData) {
        var tg = data.yearTiangan
        var dz = data.yearDizhi
        var wx = WuXing.WUXING_MU
        resetBaziWuXingWeight(data)
        calculateBaziWuXingWeight(data, WuXing.WUXING_JIN)
        calculateBaziWuXingWeight(data, WuXing.WUXING_MU)
        calculateBaziWuXingWeight(data, WuXing.WUXING_SHUI)
        calculateBaziWuXingWeight(data, WuXing.WUXING_HUO)
        calculateBaziWuXingWeight(data, WuXing.WUXING_TU)

        //san hui
        val huiList: MutableList<DiZhiSanHuiInfo> = DiZhiUtil().getDiZhi3Hui(data)
        if (huiList.size > 0) {
            wx = huiList[0].wx
            if (wx == WuXing.WUXING_JIN) {
                data.jinWeight += DIZHI_SANHUI_WEIGHT
            } else if (wx == WuXing.WUXING_MU) {
                data.muWeight += DIZHI_SANHUI_WEIGHT
            } else if (wx == WuXing.WUXING_SHUI) {
                data.shuiWeight += DIZHI_SANHUI_WEIGHT
            } else if (wx == WuXing.WUXING_HUO) {
                data.huoWeight += DIZHI_SANHUI_WEIGHT
            } else if (wx == WuXing.WUXING_TU) {
                data.tuWeight += DIZHI_SANHUI_WEIGHT
            }
        }

        //san he
        val heList: MutableList<DiZhiSanHeInfo> = DiZhiUtil().getDiZhi3He(data)
        if (heList.size > 0) {
            wx = heList[0].wx
            if (wx == WuXing.WUXING_JIN) {
                data.jinWeight += DIZHI_SANHE_WEIGHT
            } else if (wx == WuXing.WUXING_MU) {
                data.muWeight += DIZHI_SANHE_WEIGHT
            } else if (wx == WuXing.WUXING_SHUI) {
                data.shuiWeight += DIZHI_SANHE_WEIGHT
            } else if (wx == WuXing.WUXING_HUO) {
                data.huoWeight += DIZHI_SANHE_WEIGHT
            } else if (wx == WuXing.WUXING_TU) {
                data.tuWeight += DIZHI_SANHE_WEIGHT
            }
        }
        //liu he (todo)

        data.tongguanYongShen = TongGuanYongShen.TONG_GUAN_NONE
        if (data.jinWeight >= WUXING_STRONG_WEIGHT && data.muWeight >= WUXING_STRONG_WEIGHT) {
            data.tongguanYongShen = TongGuanYongShen.TONG_GUAN_JIN_MU
        }
        if (data.huoWeight >= WUXING_STRONG_WEIGHT && data.jinWeight >= WUXING_STRONG_WEIGHT) {
            data.tongguanYongShen = TongGuanYongShen.TONG_GUAN_HUO_JIN
        }
        if (data.shuiWeight >= WUXING_STRONG_WEIGHT && data.huoWeight >= WUXING_STRONG_WEIGHT) {
            data.tongguanYongShen = TongGuanYongShen.TONG_GUAN_SHUI_HUO
        }
        if (data.tuWeight >= WUXING_STRONG_WEIGHT && data.shuiWeight >= WUXING_STRONG_WEIGHT) {
            data.tongguanYongShen = TongGuanYongShen.TONG_GUAN_TU_SHUI
        }
        if (data.muWeight >= WUXING_STRONG_WEIGHT && data.tuWeight >= WUXING_STRONG_WEIGHT) {
            data.tongguanYongShen = TongGuanYongShen.TONG_GUAN_MU_TU
        }

        //tiaohou
        calculateTiaoHouYongShen(data)
        //tongguan
        calculateTongGuanShen(data)
    }

    fun calculateTongGuanShen(data: BaziData){
        if (data.tongguanYongShen == TongGuanYongShen.TONG_GUAN_JIN_MU) {
            data.tongguanShenList = getShiShenByWuXing(data, WuXing.WUXING_SHUI)
        } else if (data.tongguanYongShen == TongGuanYongShen.TONG_GUAN_HUO_JIN) {
            data.tongguanShenList = getShiShenByWuXing(data, WuXing.WUXING_TU)
        } else if (data.tongguanYongShen == TongGuanYongShen.TONG_GUAN_SHUI_HUO) {
            data.tongguanShenList = getShiShenByWuXing(data, WuXing.WUXING_MU)
        } else if (data.tongguanYongShen == TongGuanYongShen.TONG_GUAN_TU_SHUI) {
            data.tongguanShenList = getShiShenByWuXing(data, WuXing.WUXING_JIN)
        } else if (data.tongguanYongShen == TongGuanYongShen.TONG_GUAN_MU_TU) {
            data.tongguanShenList = getShiShenByWuXing(data, WuXing.WUXING_HUO)
        } else {
            data.tongguanShenList = listOf()
        }
    }

    @Composable
    fun getTongGuanText(data: BaziData): String {
        var sb = StringBuilder()
        if (data.tongguanYongShen == TongGuanYongShen.TONG_GUAN_JIN_MU) {
            sb.append(stringResource(R.string.app_bazi_tongguan_jinmu_desc))
        } else if (data.tongguanYongShen == TongGuanYongShen.TONG_GUAN_HUO_JIN) {
            sb.append(stringResource(R.string.app_bazi_tongguan_huojin_desc))
        } else if (data.tongguanYongShen == TongGuanYongShen.TONG_GUAN_SHUI_HUO) {
            sb.append(stringResource(R.string.app_bazi_tongguan_shuihuo_desc))
        } else if (data.tongguanYongShen == TongGuanYongShen.TONG_GUAN_TU_SHUI) {
            sb.append(stringResource(R.string.app_bazi_tongguan_tushui_desc))
        } else if (data.tongguanYongShen == TongGuanYongShen.TONG_GUAN_MU_TU) {
            sb.append(stringResource(R.string.app_bazi_tongguan_mutu_desc))
        } else {
            sb.append(stringResource(R.string.app_bazi_yongshen_tongguan_no_desc))
        }

        return sb.toString()
    }


    @Composable
    fun getWuXingWeightString(data: BaziData): String {
        var sb = StringBuilder()
        calculateWuXingWeight(data)
        sb.append(stringResource(R.string.app_bazi_metal))
        sb.append("(")
        sb.append(data.jinWeight)
        sb.append(") ")

        sb.append(stringResource(R.string.app_bazi_wood))
        sb.append("(")
        sb.append(data.muWeight)
        sb.append(") ")

        sb.append(stringResource(R.string.app_bazi_water))
        sb.append("(")
        sb.append(data.shuiWeight)
        sb.append(") ")

        sb.append(stringResource(R.string.app_bazi_fire))
        sb.append("(")
        sb.append(data.huoWeight)
        sb.append(") ")

        sb.append(stringResource(R.string.app_bazi_soil))
        sb.append("(")
        sb.append(data.tuWeight)
        sb.append(")")
        return sb.toString()

    }

    @Composable
    fun getTongGuanYongshenString(data: BaziData): String {
        var sb = StringBuilder()
        calculateWuXingWeight(data)
        sb.append(getTongGuanText(data))

        return sb.toString()
    }

    fun isBaziCold(data: BaziData): Boolean {
        var ret = false
        var dz = data.monthDizhi
        var count = 0
//        calculateWuXingWeight(data)
        if (dz == DiZhi.DIZHI_HAI || dz == DiZhi.DIZHI_ZI || dz == DiZhi.DIZHI_CHOU) {
            //shui wang
            if (data.shuiWeight >= 3f && (WuXingUtil().getHuoNumber(data) == 0)) {
                ret = true
            } else {
                if (data.yearDizhi == DiZhi.DIZHI_CHOU || data.yearDizhi == DiZhi.DIZHI_CHEN) count += 1
                if (data.monthDizhi == DiZhi.DIZHI_CHOU || data.monthDizhi == DiZhi.DIZHI_CHEN) count += 1
                if (data.dayDizhi == DiZhi.DIZHI_CHOU || data.dayDizhi == DiZhi.DIZHI_CHEN) count += 1
                if (data.hourDizhi == DiZhi.DIZHI_CHOU || data.hourDizhi == DiZhi.DIZHI_CHEN) count += 1
                if (count >= 2 && (WuXingUtil().getHuoNumber(data) == 0)) {
                    ret = true
                }
            }
        }
        //yongshen si,wu
        if (ret) {
            data.tiaohouYongShen = TiaoHouYongShen.TIAO_HOU_COLD
        }
        return ret
    }

    fun isBaziHot(data: BaziData): Boolean {
        var ret = false
        var dz = data.monthDizhi
        var count = 0
        //hot
        if (dz == DiZhi.DIZHI_SI || dz == DiZhi.DIZHI_WU || dz == DiZhi.DIZHI_WEI) {
            if (data.huoWeight >= 3f && data.shuiWeight < 2f) {
                ret = true
            } else {
                if (data.yearDizhi == DiZhi.DIZHI_WEI || data.yearDizhi == DiZhi.DIZHI_XU) count += 1
                if (data.monthDizhi == DiZhi.DIZHI_WEI || data.monthDizhi == DiZhi.DIZHI_XU) count += 1
                if (data.dayDizhi == DiZhi.DIZHI_WEI || data.dayDizhi == DiZhi.DIZHI_XU) count += 1
                if (data.hourDizhi == DiZhi.DIZHI_WEI || data.hourDizhi == DiZhi.DIZHI_XU) count += 1
                if (count >= 2 && data.shuiWeight < 2.0f) {
                    ret = true
                }
            }
        }
        if (ret) {
            data.tiaohouYongShen = TiaoHouYongShen.TIAO_HOU_HOT
        }
        return ret
    }

    fun isBaziDry(data: BaziData): Boolean {
        var ret = false
        var dz = data.monthDizhi
        var count = 0

        if (dz == DiZhi.DIZHI_XU || dz == DiZhi.DIZHI_WEI) {
            //check wei,xu
            if (data.yearDizhi == DiZhi.DIZHI_WEI || data.yearDizhi == DiZhi.DIZHI_XU) count += 1
            if (data.monthDizhi == DiZhi.DIZHI_WEI || data.monthDizhi == DiZhi.DIZHI_XU) count += 1
            if (data.dayDizhi == DiZhi.DIZHI_WEI || data.dayDizhi == DiZhi.DIZHI_XU) count += 1
            if (data.hourDizhi == DiZhi.DIZHI_WEI || data.hourDizhi == DiZhi.DIZHI_XU) count += 1
            if (count >= 2 && (data.shuiWeight < 2f)) {
                ret = true
            }
            if (!ret) {
                //check si,wu
                count = 0
                if (data.yearDizhi == DiZhi.DIZHI_SI || data.yearDizhi == DiZhi.DIZHI_WU) count += 1
                if (data.monthDizhi == DiZhi.DIZHI_SI || data.monthDizhi == DiZhi.DIZHI_WU) count += 1
                if (data.dayDizhi == DiZhi.DIZHI_SI || data.dayDizhi == DiZhi.DIZHI_WU) count += 1
                if (data.hourDizhi == DiZhi.DIZHI_SI || data.hourDizhi == DiZhi.DIZHI_WU) count += 1
                if (count >= 2 && (data.shuiWeight < 2f)) {
                    ret = true
                }
            }
        }
        if (ret) {
            data.tiaohouYongShen = TiaoHouYongShen.TIAO_HOU_DRY
        }
        return ret
    }

    fun isBaziWet(data: BaziData): Boolean {
        var ret = false
        var dz = data.monthDizhi
        var count = 0

        if (dz == DiZhi.DIZHI_HAI || dz == DiZhi.DIZHI_ZI) {
            if (data.yearDizhi == DiZhi.DIZHI_HAI || data.yearDizhi == DiZhi.DIZHI_ZI) count += 1
            if (data.monthDizhi == DiZhi.DIZHI_HAI || data.monthDizhi == DiZhi.DIZHI_ZI) count += 1
            if (data.dayDizhi == DiZhi.DIZHI_HAI || data.dayDizhi == DiZhi.DIZHI_ZI) count += 1
            if (data.hourDizhi == DiZhi.DIZHI_HAI || data.hourDizhi == DiZhi.DIZHI_ZI) count += 1
            if (count >= 2 && (data.huoWeight < 2f)) {
                ret = true
            }
            if (!ret) {
                count = 0
                if (data.yearDizhi == DiZhi.DIZHI_CHOU || data.yearDizhi == DiZhi.DIZHI_CHEN) count += 1
                if (data.monthDizhi == DiZhi.DIZHI_CHOU || data.monthDizhi == DiZhi.DIZHI_CHEN) count += 1
                if (data.dayDizhi == DiZhi.DIZHI_CHOU || data.dayDizhi == DiZhi.DIZHI_CHEN) count += 1
                if (data.hourDizhi == DiZhi.DIZHI_CHOU || data.hourDizhi == DiZhi.DIZHI_CHEN) count += 1
                if (count >= 2 && (data.huoWeight < 2f)) {
                    ret = true
                }
            }
        }
        if (ret) {
            //wei,xu
            data.tiaohouYongShen = TiaoHouYongShen.TIAO_HOU_WET
        }
        return ret
    }

    fun getShiShenByWuXing(data: BaziData, wx : WuXing) : List<ShiShen>{
        var wx = WuXingUtil().getTgWX(data.dayTiangan)
        var list : List<ShiShen> = listOf()
        if(WuXingUtil().getYinWuXing(data.dayTiangan) == wx){
            list = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
        }else if(WuXingUtil().getShishangWuXing(data.dayTiangan) == wx){
            list = listOf(ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
        }else if(WuXingUtil().getBiJieWuXing(data.dayTiangan) == wx){
            list = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
        }else if(WuXingUtil().getGuanshaWuXing(data.dayTiangan) == wx){
            list = listOf(ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
        }else if(WuXingUtil().getCaiWuXing(data.dayTiangan) == wx){
            list = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
        }
        return list
    }

    fun calculateTiaoHouYongShen(data: BaziData){
//        YongShenUtil().calculateWuXingWeight(data)
        if(isBaziCold(data)){
            data.tiaohouShenList = getShiShenByWuXing(data, WuXing.WUXING_HUO)
        }else if(isBaziHot(data)){
            data.tiaohouShenList = getShiShenByWuXing(data, WuXing.WUXING_SHUI)
        }else if(isBaziDry(data)){
            data.tiaohouShenList = getShiShenByWuXing(data, WuXing.WUXING_SHUI)
        }else if(isBaziWet(data)){
            data.tiaohouShenList = getShiShenByWuXing(data, WuXing.WUXING_HUO)
        }else{
            data.tiaohouShenList = listOf()
        }
    }

    @Composable
    fun getTiaohouString(data: BaziData): String {
        var summary = ""
        var sb = StringBuilder()
        YongShenUtil().calculateWuXingWeight(data)

        if(isBaziCold(data)){
            summary =
                stringResource(R.string.app_bazi_tiaohou_cold) + "[" + WuXingUtil().getWuXingText(WuXing.WUXING_HUO) + "]"
        }else if(isBaziHot(data)){
            summary =
                stringResource(R.string.app_bazi_tiaohou_hot) + "[" + WuXingUtil().getWuXingText(WuXing.WUXING_SHUI) + "]"
        }else if(isBaziDry(data)){
            summary =
                stringResource(R.string.app_bazi_tiaohou_dry) + "[" + WuXingUtil().getWuXingText(WuXing.WUXING_SHUI) + "]"
        }else if(isBaziWet(data)){
            summary =
                stringResource(R.string.app_bazi_tiaohou_wet) + "[" + WuXingUtil().getWuXingText(WuXing.WUXING_HUO) + "]"
        }else{
            summary = stringResource(R.string.app_bazi_tiaohou_mild)
        }

        sb.append(summary)

        return sb.toString()
    }
}
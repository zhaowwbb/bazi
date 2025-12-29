package com.rick.bazi.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rick.bazi.R
import com.rick.bazi.data.BaziData
import com.rick.bazi.data.BaziYongShen
import com.rick.bazi.data.ColumnPosition
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.DiZhiSanHeInfo
import com.rick.bazi.data.DiZhiSanHuiInfo
import com.rick.bazi.data.RootLevel
import com.rick.bazi.data.ShiShen
import com.rick.bazi.data.TianGan
import com.rick.bazi.data.TiaoHouYongShen
import com.rick.bazi.data.TongGuanYongShen
import com.rick.bazi.data.WuXing
import com.rick.bazi.util.ConstUtil.Companion.DIZHI_BENQI_WEIGHT
import com.rick.bazi.util.ConstUtil.Companion.DIZHI_SANHE_WEIGHT
import com.rick.bazi.util.ConstUtil.Companion.DIZHI_SANHUI_WEIGHT
import com.rick.bazi.util.ConstUtil.Companion.DIZHI_YUQI_WEIGHT
import com.rick.bazi.util.ConstUtil.Companion.TIANGAN_HEHUA_WEIGHT
import com.rick.bazi.util.ConstUtil.Companion.TIANGAN_WEIGHT
import com.rick.bazi.util.ConstUtil.Companion.WUXING_STRONG_WEIGHT

class YongShenUtil {

    fun resetBaziWuXingWeight(data: BaziData) {
        data.jinWeight = 0f
        data.muWeight = 0f
        data.shuiWeight = 0f
        data.huoWeight = 0f
        data.tuWeight = 0f
    }

    fun getDiZhiCangGanWeight(dz: DiZhi, data: BaziData, wx: WuXing): Float {
        val cangganArray = DiZhiUtil().getCanggan(dz)
        var tg = cangganArray[0]
        var isWXInCangGan = false
        if (wx == WuXingUtil().getTianGanWuxing(tg)) {
            if (DiZhiUtil().isDiZhiTouTianGan(tg, data)) {
                return ConstUtil.DIZHI_BENQI_WEIGHT
            }
        }
        if (cangganArray.size == 2) {
            tg = cangganArray[1]
            if (wx == WuXingUtil().getTianGanWuxing(tg)) {
                if (DiZhiUtil().isDiZhiTouTianGan(tg, data)) {
                    return ConstUtil.DIZHI_ZHONGQI_WEIGHT
                }
                isWXInCangGan = true
            }
        }
        if (cangganArray.size == 3) {
            tg = cangganArray[2]
            if (wx == WuXingUtil().getTianGanWuxing(tg)) {
                if (DiZhiUtil().isDiZhiTouTianGan(tg, data)) {
                    return ConstUtil.DIZHI_YUQI_WEIGHT
                }
                isWXInCangGan = true
            }
        }
        if (isWXInCangGan) {
            return ConstUtil.DIZHI_CANGGAN_WEIGHT
        } else {
            return ConstUtil.ZERO_WEIGHT
        }
    }

    fun calculateBaziWuXingWeight(data: BaziData, wx: WuXing) {
        var weight = 0f
        var isTouChu = false
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
            weight += ConstUtil.DIZHI_WEIGHT
        }
        weight += getDiZhiCangGanWeight(data.yearDizhi, data, wx)

        if (wx == WuXingUtil().getDiZhiWuxing(data.monthDizhi)) {
            weight += ConstUtil.DIZHI_MONTH_WEIGHT
        }
        weight += getDiZhiCangGanWeight(data.monthDizhi, data, wx)

        if (wx == WuXingUtil().getDiZhiWuxing(data.dayDizhi)) {
            weight += ConstUtil.DIZHI_WEIGHT
        }
        weight += getDiZhiCangGanWeight(data.dayDizhi, data, wx)

        if (wx == WuXingUtil().getDiZhiWuxing(data.hourDizhi)) {
            weight += ConstUtil.DIZHI_WEIGHT
        }
        weight += getDiZhiCangGanWeight(data.hourDizhi, data, wx)

        //tian gan 6 he
//        val heList = TianGanUtil().getTianGanHeList(data)
        for (he in TianGanUtil().getTianGanHeList(data)) {
            if (wx == TianGanUtil().getTianGanHeWuXing(he.srcTg)) {
                weight += ConstUtil.TIANGAN_5HE_WEIGHT
            }
        }

        //Dizhi 3hui
        for (h in DiZhiUtil().getDiZhi3Hui(data)) {
            if (wx == DiZhiUtil().get3HuiWuXingByDiZhi(h.dz1)) {
                weight += ConstUtil.DIZHI_SANHUI_WEIGHT
            }
        }

        //DiZhi 3he
        for (h in DiZhiUtil().getDiZhi3He(data)) {
            if (wx == DiZhiUtil().get3HeWuXingByDiZhi(h.dz1)) {
                weight += ConstUtil.DIZHI_SANHE_WEIGHT
            }
        }

//        var tgSet: MutableSet<TianGanUtil.TianGanPos> = mutableSetOf()
//
//        var isHeHua = false
//        for (info in heList) {
//            //remove duplicate position
//            if (!tgSet.contains(info.srcTgPos) && !tgSet.contains(info.dstTgPos)) {
//                isHeHua = TianGanUtil().isTianGanHeHua(data, info)
//                if (isHeHua) {
////                    increaseTianGanHeHuaWeight(data, info)
//                } else {
////                    reduceTianGanHeWeight(data, info, TIANGAN_HEBAN_WEIGHT, TIANGAN_HEBAN_WEIGHT)
//                }
//                tgSet.add(info.srcTgPos)
//                tgSet.add(info.dstTgPos)
//            }
//        }


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
        //shishen weight
        calculateShiShenWeight(data)
    }

    fun calculateShiShenWeight(data: BaziData) {
        var wx = WuXingUtil().getTgWX(data.dayTiangan)
        if (wx == WuXing.WUXING_MU) {
            data.yinWeight = data.shuiWeight
            data.bijieWeight = data.muWeight
            data.guanshaWeight = data.jinWeight
            data.shishangWeight = data.huoWeight
            data.caiWeight = data.tuWeight
        } else if (wx == WuXing.WUXING_HUO) {
            data.yinWeight = data.muWeight
            data.bijieWeight = data.huoWeight
            data.guanshaWeight = data.shuiWeight
            data.shishangWeight = data.tuWeight
            data.caiWeight = data.jinWeight
        } else if (wx == WuXing.WUXING_SHUI) {
            data.yinWeight = data.jinWeight
            data.bijieWeight = data.shuiWeight
            data.guanshaWeight = data.tuWeight
            data.shishangWeight = data.muWeight
            data.caiWeight = data.huoWeight
        } else if (wx == WuXing.WUXING_JIN) {
            data.yinWeight = data.tuWeight
            data.bijieWeight = data.jinWeight
            data.guanshaWeight = data.huoWeight
            data.shishangWeight = data.shuiWeight
            data.caiWeight = data.muWeight
        } else if (wx == WuXing.WUXING_TU) {
            data.yinWeight = data.huoWeight
            data.bijieWeight = data.tuWeight
            data.guanshaWeight = data.muWeight
            data.shishangWeight = data.jinWeight
            data.caiWeight = data.shuiWeight
        }
    }

    fun calculateTongGuanShen(data: BaziData) {
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

    fun getShiShenByWuXing(data: BaziData, wx: WuXing): List<ShiShen> {
//        var wx = WuXingUtil().getTgWX(data.dayTiangan)
        var list: List<ShiShen> = listOf()
        if (WuXingUtil().getYinWuXing(data.dayTiangan) == wx) {
            list = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
        } else if (WuXingUtil().getShishangWuXing(data.dayTiangan) == wx) {
            list = listOf(ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
        } else if (WuXingUtil().getBiJieWuXing(data.dayTiangan) == wx) {
            list = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
        } else if (WuXingUtil().getGuanshaWuXing(data.dayTiangan) == wx) {
            list = listOf(ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
        } else if (WuXingUtil().getCaiWuXing(data.dayTiangan) == wx) {
            list = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
        }
        return list
    }

    fun calculateTiaoHouYongShen(data: BaziData) {
//        YongShenUtil().calculateWuXingWeight(data)
        if (isBaziCold(data)) {
            data.tiaohouShenList = getShiShenByWuXing(data, WuXing.WUXING_HUO)
        } else if (isBaziHot(data)) {
            data.tiaohouShenList = getShiShenByWuXing(data, WuXing.WUXING_SHUI)
        } else if (isBaziDry(data)) {
            data.tiaohouShenList = getShiShenByWuXing(data, WuXing.WUXING_SHUI)
        } else if (isBaziWet(data)) {
            data.tiaohouShenList = getShiShenByWuXing(data, WuXing.WUXING_HUO)
        } else {
            data.tiaohouShenList = listOf()
        }
    }

    @Composable
    fun getTiaohouString(data: BaziData): String {
        var summary = ""
        var sb = StringBuilder()
        YongShenUtil().calculateWuXingWeight(data)

        if (isBaziCold(data)) {
            summary =
                stringResource(R.string.app_bazi_tiaohou_cold) + "[" + WuXingUtil().getWuXingText(
                    WuXing.WUXING_HUO
                ) + "]"
        } else if (isBaziHot(data)) {
            summary =
                stringResource(R.string.app_bazi_tiaohou_hot) + "[" + WuXingUtil().getWuXingText(
                    WuXing.WUXING_SHUI
                ) + "]"
        } else if (isBaziDry(data)) {
            summary =
                stringResource(R.string.app_bazi_tiaohou_dry) + "[" + WuXingUtil().getWuXingText(
                    WuXing.WUXING_SHUI
                ) + "]"
        } else if (isBaziWet(data)) {
            summary =
                stringResource(R.string.app_bazi_tiaohou_wet) + "[" + WuXingUtil().getWuXingText(
                    WuXing.WUXING_HUO
                ) + "]"
        } else {
            summary = stringResource(R.string.app_bazi_tiaohou_mild)
        }

        sb.append(summary)

        return sb.toString()
    }

    fun getRootWeight(rootLevel: RootLevel): Float {
        if (rootLevel == RootLevel.STRONG_ROOT) return ConstUtil.STRONG_ROOT_WEIGHT
        if (rootLevel == RootLevel.MEDIUM_ROOT) return ConstUtil.MEDIUM_ROOT_WEIGHT
        if (rootLevel == RootLevel.WEAK_ROOT) return ConstUtil.WEAK_ROOT_WEIGHT
        return ConstUtil.ZERO_WEIGHT
    }

    fun addTianGanYongShen(
        data: BaziData,
        tg: TianGan,
        dz: DiZhi,
        columnPosition: ColumnPosition,
        yongshenList: MutableList<BaziYongShen>
    ) {
        var hasRoot = false
        var weight = ConstUtil.ZERO_WEIGHT
        var xiYongWeight = ConstUtil.ZERO_WEIGHT
        var yongshen: BaziYongShen
        val ss = ShiShenUtil().getShiShen(tg, data.dayTiangan)
        val dzTg = DiZhiUtil().getTianGan(dz)

        if (data.xiyongShenSet.contains(ss)) {
            weight += ConstUtil.TIANGAN_WEIGHT
            hasRoot = WuXingUtil().checkRootToTianGan(data, tg)
            if (hasRoot) {
                weight += getRootWeight(data.yearDzRootLevel)
                weight += getRootWeight(data.monthDzRootLevel)
                weight += getRootWeight(data.dayDzRootLevel)
                weight += getRootWeight(data.hourDzRootLevel)
            }
            //check sitting Dizhi
            if (ShiShenUtil().isHelpShiShen(tg, dzTg)) {
                weight += ConstUtil.TIANGAN_WEIGHT
            }
            xiYongWeight = getShenWeight(ss, data)

            yongshen = BaziYongShen(
                columnPosition = columnPosition,
                tg = tg,
                dz = dz,
                isTianGan = true,
                weight = weight,
                isTongGen = hasRoot,
                isTouchu = false,
                xiYongWeight = xiYongWeight
            )
            yongshenList.add(yongshen)
        }
    }

    fun addDiZhiYongShen(
        data: BaziData,
        dz: DiZhi,
        columnPosition: ColumnPosition,
        yongshenList: MutableList<BaziYongShen>
    ) {
        var isYongShen = false
        var tg = data.yearTiangan
        var yongShenTG = TianGan.TIANGAN_JIA
        var isTouGan = false
        var isCangGan = false
        var weight = ConstUtil.ZERO_WEIGHT
        var xiYongWeight = ConstUtil.ZERO_WEIGHT
        var yongshen: BaziYongShen
        val tgArray = DiZhiUtil().getCanggan(dz)

        tg = tgArray[0]
        var ss = ShiShenUtil().getShiShen(tg, data.dayTiangan)
        //benqi
        if (data.xiyongShenSet.contains(ss)) {
            isYongShen = true
//            isCangGan = true
            yongShenTG = tg
            if (columnPosition == ColumnPosition.COLUMN_MONTH) {
                weight += ConstUtil.DIZHI_MONTH_WEIGHT
            } else {
                weight += ConstUtil.DIZHI_WEIGHT
            }

            isTouGan = DiZhiUtil().isDiZhiTouTianGan(tg, data)
            if (isTouGan) weight += ConstUtil.DIZHI_BENQI_WEIGHT

            xiYongWeight = getShenWeight(ss, data)
        } else if (tgArray.size == 2) {
            //zhong qi
            tg = tgArray[1]
            ss = ShiShenUtil().getShiShen(tg, data.dayTiangan)
            if (data.xiyongShenSet.contains(ss)) {
                isYongShen = true
                weight += ConstUtil.DIZHI_CANGGAN_WEIGHT
                isCangGan = true
                yongShenTG = tg
                isTouGan = DiZhiUtil().isDiZhiTouTianGan(tg, data)
                if (isTouGan) weight += ConstUtil.DIZHI_ZHONGQI_WEIGHT
                xiYongWeight = getShenWeight(ss, data)
            }
        } else if (tgArray.size == 3) {
            //yuqi
            tg = tgArray[2]
            ss = ShiShenUtil().getShiShen(tg, data.dayTiangan)
            if (data.xiyongShenSet.contains(ss)) {
                isYongShen = true
                weight += ConstUtil.DIZHI_CANGGAN_WEIGHT
                isCangGan = true
                yongShenTG = tg
                isTouGan = DiZhiUtil().isDiZhiTouTianGan(tg, data)
                if (isTouGan) weight += ConstUtil.DIZHI_YUQI_WEIGHT
                xiYongWeight = getShenWeight(ss, data)
            }
        }

        if (isYongShen) {
            yongshen = BaziYongShen(
                columnPosition = columnPosition,
                tg = tg,
                dz = dz,
                isTianGan = false,
                weight = weight,
                isTongGen = false,
                isTouchu = isTouGan,
                isCangGan = isCangGan,
                yongshenTG = yongShenTG,
                xiYongWeight = xiYongWeight
            )
            yongshenList.add(yongshen)
        }
    }

    fun isYongShen(ss : ShiShen, data: BaziData) : Boolean{
        for(s in data.yongShenList){
            if(ss == s)return true
        }
        return false
    }

    fun isXiShen(ss : ShiShen, data: BaziData) : Boolean{
        for(s in data.xiShenList){
            if(ss == s)return true
        }
        return false
    }

    fun isTiaoHouShen(ss : ShiShen, data: BaziData) : Boolean{
        for(s in data.tiaohouShenList){
            if(ss == s)return true
        }
        return false
    }

    fun isJiShen(ss : ShiShen, data: BaziData) : Boolean{
        for(s in data.jiShenList){
            if(ss == s)return true
        }
        return false
    }

    fun getShenWeight(ss : ShiShen, data: BaziData) : Float{
        if(isYongShen(ss, data)){
            return ConstUtil.YONG_SHEN_WEIGHT
        }
        if(isTiaoHouShen(ss, data)){
            return ConstUtil.TIAO_HOU_SHEN_WEIGHT
        }
        if(isXiShen(ss, data)){
            return ConstUtil.XI_SHEN_WEIGHT
        }
        if(isJiShen(ss, data)){
            return ConstUtil.JI_SHEN_WEIGHT
        }
        return ConstUtil.XIAN_SHEN_WEIGHT
    }

    fun checkBaziYongShen(data: BaziData) {
        val yongshenList: MutableList<BaziYongShen> = mutableListOf()
//        var isYongShen = false
//        var tg = data.yearTiangan
//        var dz = data.yearDizhi

        data.allYongShenList =
            (data.yongShenList + data.xiShenList + data.tiaohouShenList).distinct()

        var xiyongShenSet: MutableSet<ShiShen> = mutableSetOf()
        var jiShenSet: MutableSet<ShiShen> = mutableSetOf()

        xiyongShenSet.addAll(data.yongShenList)
        xiyongShenSet.addAll(data.xiShenList)
        xiyongShenSet.addAll(data.tiaohouShenList)
//        xiyongShenSet.addAll(data.tongguanShenList)

        data.xiyongShenSet = xiyongShenSet

        for (ss in data.jiShenList) {
            if (!data.xiyongShenSet.contains(ss)) {
                jiShenSet.add(ss)
            }
        }
        data.jiShenSet = jiShenSet

        //check year tiangan
        addTianGanYongShen(
            data,
            data.yearTiangan,
            data.yearDizhi,
            ColumnPosition.COLUMN_YEAR,
            yongshenList
        )
        addTianGanYongShen(
            data,
            data.monthTiangan,
            data.monthDizhi,
            ColumnPosition.COLUMN_MONTH,
            yongshenList
        )
        addTianGanYongShen(
            data,
            data.hourTiangan,
            data.hourDizhi,
            ColumnPosition.COLUMN_HOUR,
            yongshenList
        )

        addDiZhiYongShen(data, data.yearDizhi, ColumnPosition.COLUMN_YEAR, yongshenList)
        addDiZhiYongShen(data, data.monthDizhi, ColumnPosition.COLUMN_MONTH, yongshenList)
        addDiZhiYongShen(data, data.dayDizhi, ColumnPosition.COLUMN_DAY, yongshenList)
        addDiZhiYongShen(data, data.hourDizhi, ColumnPosition.COLUMN_HOUR, yongshenList)

        //sort yongshenList by weight
//        val sortedYongshenList = yongshenList.sortedByDescending { it.weight }

        val sortedYongshenList =
            yongshenList.sortedWith(
            compareByDescending<BaziYongShen> { it.weight }
                .thenByDescending { it.xiYongWeight }
            )

        data.baziYongShenList = sortedYongshenList
    }

    @Composable
    fun getYongshenString(data: BaziData): String {
        checkBaziYongShen(data)
        val sb = StringBuilder()
        var isYongShen = false
        var index = 1
        for (ys in data.baziYongShenList) {
            sb.append(ConstUtil.SPACE)
            sb.append(index).append(".")
            index += 1
            if (!isYongShen) {
                sb.append("[")
                sb.append(stringResource(R.string.app_bazi_yong_shen))
                sb.append("]")
                isYongShen = true
            } else {
                sb.append("[")
                sb.append(stringResource(R.string.app_bazi_xi_shen))
                sb.append("]")
            }
            sb.append(BaziColumnUtil().getBaziColumnString(ys.columnPosition, ys.isTianGan))

            sb.append(" ")
            if (ys.isTianGan) {
                sb.append(WuXingUtil().getTianGanWuXingText(ys.tg))
                sb.append("(")
                sb.append(ShiShenUtil().getShiShenText(ys.tg, data.dayTiangan))
                sb.append(") ")
            } else {
                if (ys.isCangGan) {
                    sb.append(WuXingUtil().getDiZhiWuXingString(ys.dz))
                    sb.append("(")
                    sb.append(stringResource(R.string.bazi_canggan))
                    sb.append(WuXingUtil().getTianGanWuXingText(ys.yongshenTG))
                    sb.append(" ")
                    sb.append(ShiShenUtil().getShiShenText(ys.yongshenTG, data.dayTiangan))
                    sb.append(") ")
                } else {
                    sb.append(WuXingUtil().getDiZhiWuXingString(ys.dz))
                    sb.append("(")
                    sb.append(ShiShenUtil().getDiZhiShiShenText(ys.dz, data.dayTiangan))
                    sb.append(") ")
                }
            }
            sb.append(" ")
            sb.append(stringResource(R.string.app_bazi_weight_label))
            sb.append("(")
            sb.append(ys.weight)
            sb.append(") ")

            sb.append("\n")
        }
        return sb.toString()
    }

    fun isWuXingXiYongShen(wx: WuXing, data: BaziData): Boolean {
        var tg1 = TianGan.TIANGAN_JIA
        var tg2 = TianGan.TIANGAN_JIA
        if (wx == WuXing.WUXING_MU) {
            tg1 = TianGan.TIANGAN_JIA
            tg2 = TianGan.TIANGAN_YI
        }
        if (wx == WuXing.WUXING_JIN) {
            tg1 = TianGan.TIANGAN_GENG
            tg2 = TianGan.TIANGAN_XIN
        }
        if (wx == WuXing.WUXING_HUO) {
            tg1 = TianGan.TIANGAN_BING
            tg2 = TianGan.TIANGAN_DING
        }
        if (wx == WuXing.WUXING_TU) {
            tg1 = TianGan.TIANGAN_WU
            tg2 = TianGan.TIANGAN_JI
        }
        if (wx == WuXing.WUXING_SHUI) {
            tg1 = TianGan.TIANGAN_REN
            tg2 = TianGan.TIANGAN_GUI
        }

        var ss = ShiShenUtil().getShiShen(tg1, data.dayTiangan)
        if(data.xiyongShenSet.contains(ss)){
            return true
        }

        ss = ShiShenUtil().getShiShen(tg2, data.dayTiangan)
        if(data.xiyongShenSet.contains(ss)){
            return true
        }
        return false
    }

    fun isWuXingJiShen(wx: WuXing, data: BaziData): Boolean {
        var tg1 = TianGan.TIANGAN_JIA
        var tg2 = TianGan.TIANGAN_JIA
        if (wx == WuXing.WUXING_MU) {
            tg1 = TianGan.TIANGAN_JIA
            tg2 = TianGan.TIANGAN_YI
        }
        if (wx == WuXing.WUXING_JIN) {
            tg1 = TianGan.TIANGAN_GENG
            tg2 = TianGan.TIANGAN_XIN
        }
        if (wx == WuXing.WUXING_HUO) {
            tg1 = TianGan.TIANGAN_BING
            tg2 = TianGan.TIANGAN_DING
        }
        if (wx == WuXing.WUXING_TU) {
            tg1 = TianGan.TIANGAN_WU
            tg2 = TianGan.TIANGAN_JI
        }
        if (wx == WuXing.WUXING_SHUI) {
            tg1 = TianGan.TIANGAN_REN
            tg2 = TianGan.TIANGAN_GUI
        }

        if (!isWuXingXiYongShen(wx, data)) {
            var ss = ShiShenUtil().getShiShen(tg1, data.dayTiangan)
            for (ys in data.jiShenList) {
                if (ys == ss) {
                    return true
                }
            }
            ss = ShiShenUtil().getShiShen(tg2, data.dayTiangan)
            for (ys in data.jiShenList) {
                if (ys == ss) {
                    return true
                }
            }
        }
        return false
    }

    fun isTianGanYongShen(tg: TianGan, data: BaziData): Boolean {
        if (data.baziYongShenList.size > 0) {
            val ys = data.baziYongShenList[0]
            if (ys.isTianGan && ys.tg == tg) {
                return true
            }
        }
        return false
    }

    fun isTianGanXiShen(tg: TianGan, data: BaziData): Boolean {
//        var ret = false
        val ss = ShiShenUtil().getShiShen(tg, data.dayTiangan)
        //exclude yong shen
        if (isTianGanYongShen(tg, data)) {
            return false
        }
        if (data.xiyongShenSet.contains(ss)) {
            return true
        }
        return false
    }

    fun isTianGanJiShen(tg: TianGan, data: BaziData): Boolean {
        val ss = ShiShenUtil().getShiShen(tg, data.dayTiangan)
        if (data.jiShenSet.contains(ss)) {
            return true
        }
        return false
    }

    fun isDiZhiJiShen(dz: DiZhi, data: BaziData): Boolean {
        val ss = ShiShenUtil().getShiShenFromDizhi(dz, data.dayTiangan)
        if (data.jiShenSet.contains(ss)) {
            return true
        }
        return false
    }

    fun isDiZhiXiShen(dz: DiZhi, data: BaziData): Boolean {
        if (isDiZhiYongShen(dz, data)) {
            return false
        }

        val ss = ShiShenUtil().getShiShenFromDizhi(dz, data.dayTiangan)
        if (data.xiyongShenSet.contains(ss)) {
            return true
        }
        return false
    }

    fun isDiZhiYongShen(dz: DiZhi, data: BaziData): Boolean {
        if (data.baziYongShenList.size > 0) {
            val ys = data.baziYongShenList.get(0)
            if (!ys.isTianGan && dz == ys.dz) {
                return true
            }
        }
        return false
    }

    @Composable
    fun getTianGanXiJiWeightString(dyTG: TianGan, data: BaziData): String {
        val sb = StringBuilder()
        var isYongShen = isTianGanYongShen(dyTG, data)
        var isXiShen = isTianGanXiShen(dyTG, data)
        var isJiShen = isTianGanJiShen(dyTG, data)
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

        sb.append(stringResource(R.string.dayun_tiangan_label))
        sb.append(WuXingUtil().getTianGanWuXingString(dyTG))
        sb.append(xjStr)
        sb.append(" ")
        sb.append(stringResource(R.string.app_bazi_weight_label))

        if (isJiShen) {
            data.daYunWeight -= ConstUtil.JI_SHEN_WEIGHT
            sb.append("(").append(ConstUtil.SUB_SYMBOL).append(ConstUtil.JI_SHEN_WEIGHT).append(")")
        } else if (isYongShen) {
            data.daYunWeight += ConstUtil.YONG_SHEN_WEIGHT
            sb.append("(").append(ConstUtil.ADD_SYMBOL).append(ConstUtil.YONG_SHEN_WEIGHT)
                .append(")")
        } else if (isXiShen) {
            data.daYunWeight += ConstUtil.XI_SHEN_WEIGHT
            sb.append("(").append(ConstUtil.ADD_SYMBOL).append(ConstUtil.XI_SHEN_WEIGHT).append(")")
        } else {
            data.daYunWeight += ConstUtil.XIAN_SHEN_WEIGHT
            sb.append("(").append(ConstUtil.ADD_SYMBOL).append(ConstUtil.XIAN_SHEN_WEIGHT)
                .append(")")
        }
        sb.append("\n")

        return sb.toString()
    }

    @Composable
    fun getDiZhiTouXiJiWeightString(dyDZ: DiZhi, data: BaziData): String {
        val sb = StringBuilder()
        var isYongShen = isDiZhiYongShen(dyDZ, data)
        var isXiShen = isDiZhiXiShen(dyDZ, data)
        var isJiShen = isDiZhiJiShen(dyDZ, data)
        var xjStr = ""
//        sb.append(ConstUtil.SPACE)

        if (isYongShen) {
            xjStr = "[" + stringResource(R.string.app_bazi_yong_shen) + "]"
        } else if (isXiShen) {
            xjStr = "[" + stringResource(R.string.app_bazi_xi_shen) + "]"
        } else if (isJiShen) {
            xjStr = "[" + stringResource(R.string.app_bazi_ji_shen) + "]"
        } else {
            xjStr = "[" + stringResource(R.string.app_bazi_xian_shen) + "]"
        }

//        sb.append(stringResource(R.string.dayun_dizhi_label))
//        sb.append(WuXingUtil().getDiZhiWuXingString(dyDZ))
        sb.append(xjStr)
        sb.append(" ")
        sb.append(stringResource(R.string.app_bazi_weight_label))

        if (isJiShen) {
            data.daYunWeight -= ConstUtil.JI_SHEN_WEIGHT
            sb.append("(").append(ConstUtil.SUB_SYMBOL).append(ConstUtil.JI_SHEN_WEIGHT).append(")")
        } else if (isYongShen) {
            data.daYunWeight += ConstUtil.YONG_SHEN_WEIGHT
            sb.append("(").append(ConstUtil.ADD_SYMBOL).append(ConstUtil.YONG_SHEN_WEIGHT)
                .append(")")
        } else if (isXiShen) {
            data.daYunWeight += ConstUtil.XI_SHEN_WEIGHT
            sb.append("(").append(ConstUtil.ADD_SYMBOL).append(ConstUtil.XI_SHEN_WEIGHT).append(")")
        } else {
            data.daYunWeight += ConstUtil.XIAN_SHEN_WEIGHT
            sb.append("(").append(ConstUtil.ADD_SYMBOL).append(ConstUtil.XIAN_SHEN_WEIGHT)
                .append(")")
        }
        sb.append("\n")

        return sb.toString()
    }

    @Composable
    fun getDiZhiXiJiWeightString(dyDZ: DiZhi, data: BaziData): String {
        val sb = StringBuilder()
        var isYongShen = isDiZhiYongShen(dyDZ, data)
        var isXiShen = isDiZhiXiShen(dyDZ, data)
        var isJiShen = isDiZhiJiShen(dyDZ, data)
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

        sb.append(stringResource(R.string.dayun_dizhi_label))
        sb.append(WuXingUtil().getDiZhiWuXingString(dyDZ))
        sb.append(xjStr)
        sb.append(" ")
        sb.append(stringResource(R.string.app_bazi_weight_label))

        if (isJiShen) {
            data.daYunWeight -= ConstUtil.JI_SHEN_WEIGHT
            sb.append("(").append(ConstUtil.SUB_SYMBOL).append(ConstUtil.JI_SHEN_WEIGHT).append(")")
        } else if (isYongShen) {
            data.daYunWeight += ConstUtil.YONG_SHEN_WEIGHT
            sb.append("(").append(ConstUtil.ADD_SYMBOL).append(ConstUtil.YONG_SHEN_WEIGHT)
                .append(")")
        } else if (isXiShen) {
            data.daYunWeight += ConstUtil.XI_SHEN_WEIGHT
            sb.append("(").append(ConstUtil.ADD_SYMBOL).append(ConstUtil.XI_SHEN_WEIGHT).append(")")
        } else {
            data.daYunWeight += ConstUtil.XIAN_SHEN_WEIGHT
            sb.append("(").append(ConstUtil.ADD_SYMBOL).append(ConstUtil.XIAN_SHEN_WEIGHT)
                .append(")")
        }
        sb.append("\n")

        return sb.toString()
    }
}